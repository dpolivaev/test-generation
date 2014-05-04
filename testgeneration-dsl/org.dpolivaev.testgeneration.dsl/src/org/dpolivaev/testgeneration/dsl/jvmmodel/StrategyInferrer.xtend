package org.dpolivaev.testgeneration.dsl.jvmmodel

import com.google.inject.Injector
import java.util.ArrayList
import java.util.Collection
import javax.inject.Inject
import org.dpolivaev.testgeneration.dsl.testspec.Condition
import org.dpolivaev.testgeneration.dsl.testspec.DisabledRule
import org.dpolivaev.testgeneration.dsl.testspec.Generation
import org.dpolivaev.testgeneration.dsl.testspec.Rule
import org.dpolivaev.testgeneration.dsl.testspec.RuleGroup
import org.dpolivaev.testgeneration.dsl.testspec.StrategyReference
import org.dpolivaev.testgeneration.dsl.testspec.ValueAction
import org.dpolivaev.testgeneration.dsl.testspec.ValueProvider
import org.dpolivaev.testgeneration.dsl.testspec.Values
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry
import org.dpolivaev.testgeneration.engine.coverage.CoverageTrackerEnabler
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy
import org.dpolivaev.testgeneration.engine.coverage.StrategyConverter
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder.Factory
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProviderHelper
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.xbase.XBooleanLiteral
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XNullLiteral
import org.eclipse.xtext.xbase.XNumberLiteral
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.xbase.compiler.Later
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

import static extension org.dpolivaev.testgeneration.dsl.jvmmodel.StrategyCompiler.*

class StrategyInferrer{
	static val OPTIMIZE_FOR_DEBUG = true
	@Inject Injector injector
	@Inject extension JvmTypesBuilder jvmTypesBuilder
	@Inject ClassInferrer classInferrer
	@Inject XbaseCompiler xbaseCompiler
	val Methods methods
	var JvmGenericType jvmType
	var org.dpolivaev.testgeneration.dsl.testspec.Strategy strategy

	new(){
		this.methods = new Methods
	}


	def void inferStrategy(JvmGenericType jvmType, org.dpolivaev.testgeneration.dsl.testspec.Strategy strategy){
		this.strategy = strategy
		this.jvmType = jvmType
		if(! (strategy.parameters.empty && strategy.vars.empty) )
			classInferrer.inferConstructor(jvmType, strategy, strategy.parameters, strategy.vars)
		classInferrer.inferMemberVariables(jvmType, strategy, strategy.vars)
		classInferrer.inferMemberMethods(jvmType, strategy, strategy.subs)

		inferExpressions()
		inferStrategyMethods()
	}

	private def inferExpressions(){
		val contents = EcoreUtil2.eAllContents(strategy)
		for(obj : contents){
			if (obj instanceof ValueAction)
				appendMethodsForValueProviders(obj)
			if (obj instanceof RuleGroup)
				appendRuleNameProviders(obj)
			if (obj instanceof Rule)
				appendRulePropertyNameProviders(obj)
			else if (obj instanceof Condition)
				appendConditions(obj)
			if (obj instanceof StrategyReference)
				appendStrategyReferences(obj)
		}
	}

	final static val STRATEGY = "_strategy"
	private def appendStrategyReferences(StrategyReference ref){
		if(ref.expr != null) {
			createMethod(ref.expr, STRATEGY, ref.expr.inferredType, false)
		}
	}

    final static val VALUE = "_value"
	private def appendMethodsForValueProviders(ValueAction action){
		for(valueProvider:action.valueProviders)
			for(expr:valueProvider.expressions)
				if(shouldCreateMethodFor(expr))
					createMethod(expr, VALUE, expr.inferredType, true)

	}

	final static val NAME = "_name"
	private def appendRulePropertyNameProviders(Rule rule){
			for(expr:rule.nameExpressions)
				if(shouldCreateMethodFor(expr))
					createMethod(expr, NAME, expr.inferredType, false)
	}

	private def appendRuleNameProviders(RuleGroup rule){
			for(expr:rule.ruleNameExpressions)
				if(shouldCreateMethodFor(expr))
					createMethod(expr, NAME, expr.inferredType, false)
	}

	private def createMethod(XExpression expr, String prefix, JvmTypeReference resultTypeRef, boolean useParameters){
				if(methods.contains(prefix, expr))
					return
				val valueProviderCounter = methods.size + 1
				val name = prefix + valueProviderCounter
				methods.put(prefix, expr, name)
				jvmType.members += expr.toMethod(name, resultTypeRef)[
					if(useParameters)
						parameters += expr.toParameter("propertyContainer", expr.newTypeRef(PropertyContainer))
					body = expr
					visibility = JvmVisibility::PRIVATE
				]
	}

	private def shouldCreateMethodFor(XExpression expr){
		return !isLiteral(expr);
	}

	private def isLiteral(XExpression expr) {
		val isLiteral = expr instanceof XStringLiteral || expr instanceof XNumberLiteral || expr instanceof XBooleanLiteral || expr instanceof XNullLiteral
		isLiteral
	}

   final static val CONDITION = "condition"

	private def appendConditions(Condition condition){
		if(condition != null){
			val expr = condition.expr
			if(expr != null){
				createMethod(expr, CONDITION, expr.newTypeRef(Boolean), true)
			}
		}
	}

	private def inferStrategyMethods(){
		val methodName = strategy.name
		jvmType.members += strategy.toMethod(methodName, strategy.newTypeRef(RequirementBasedStrategy)) [
			body = [
				append(strategy.newTypeRef(CoverageEntry).type)
				append('[] _requiredItems = ') injector.getInstance(CoverageEntriesInferrer).appendArrayInitializer(it, strategy) append(';')
				newLine
				append(strategy.newTypeRef(Strategy).type)
				append(' _strategy = new ') append(strategy.newTypeRef(Strategy).type) append('();')
				newLine
				for(ruleGroup:strategy.ruleGroups){
					appendRuleGroup(it, ruleGroup)
				}
				append('return new ') append(strategy.newTypeRef(RequirementBasedStrategy).type) append('(_requiredItems)')
				append('.with(_strategy)')
				addAppliedStrategyItems(it, strategy)
				append(';')
			]
			visibility = JvmVisibility::DEFAULT
		]
	}

	def private void addAppliedStrategyItems(ITreeAppendable it, org.dpolivaev.testgeneration.dsl.testspec.Strategy strategy){
		val contents = EcoreUtil2.eAllContents(strategy)
		for(obj : contents){
			if (obj instanceof StrategyReference){
				append('.addRequiredItemsFrom(')
				append(strategy.newTypeRef(StrategyConverter).type) append('.toRequirementBasedStrategy(')
				appendReference(it, STRATEGY, obj.expr)
				append('))')
			}
		}
	}

	def appendConditionDefaultRule(ITreeAppendable it, RuleGroup ruleGroup){
		val expr = ruleGroup.condition.expr
		append(strategy.newTypeRef(Factory).type)
		append('.iterate("')
		append(propertyName(expr))
		append('")')
		append('.over(')
			appendImplementationObject(it, expr.newTypeRef(org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider).type, "Object value",
				[appendConditionExpression(it, ruleGroup.condition)])
		append(')')
		append('.asDefaultRule()')
	}

	def private propertyName(EObject expr) {
		val generation = expr.eResource.contents.get(0) as Generation
		val packageId = generation.package?:""
		' ' + packageId + EcoreUtil.getURI(expr).toString
	}

	def private appendConditionExpression(ITreeAppendable it, Condition condition) {
		newLine
		val trace = condition.trace
		if(trace)
			appendTraceStart(it, condition)
		append('Boolean _condition = ')
		appendAncestorCondition(it, condition.eContainer.eContainer)
		val methodName = methods.get(CONDITION, condition.expr)
		append('''«methodName»(propertyContainer)''')
		append(';')
		newLine
		append('return _condition;')
		if(trace)
			appendTraceEnd(it, condition)
	}

	def void appendAncestorCondition(ITreeAppendable it, EObject ruleGroup){
		if(ruleGroup instanceof RuleGroup){
			val expr = ruleGroup?.condition?.expr
			if(expr != null)
				append('''propertyContainer.<Boolean>get("«propertyName(expr)»") && ''')
			else
				appendAncestorCondition(it, ruleGroup.eContainer)
		}
	}

	def private void appendRule(ITreeAppendable it, RuleGroup ruleGroup, boolean temporaryRule) {
		append(strategy.newTypeRef(Factory).type)
		appendRuleName(it, ruleGroup)
		appendTriggers(it, ruleGroup)
		appendCondition(it, ruleGroup)
		val rule = ruleGroup.rule
		if(rule !=null){
			val specialValue = rule.specialValue
			if(specialValue  == "skip"){
				append('''.«specialValue»()''')
			}
			else{
				if(specialValue == "disable"){
					appendRuleName(it, rule.disabledRule)
					append('''.«specialValue»()''')
				}
				else{	
					appendRulePropertyName(it, rule)
					appendRuleValues(it, rule.values, true)
					appendRuleOrder(it, rule)
				}
			}
			if(rule.isDefault)
				append('.asDefaultRule()')
		}
		else if(ruleGroup.strategy !=null){
			appendStrategyRule(it, ruleGroup.strategy)
			append('.asRules()')
		}
	}

	def private void appendCondition(ITreeAppendable it, EObject ruleGroup){
		if(ruleGroup instanceof RuleGroup){
			val expr = ruleGroup?.condition?.expr
			if(expr != null){
				append('._if(')
					appendImplementationObject(it, ruleGroup.newTypeRef(org.dpolivaev.testgeneration.engine.ruleengine.Condition).type, "boolean isSatisfied",
						[
							newLine
							append('''return propertyContainer.<Boolean>get("«propertyName(expr)»");''')
						])
				append(')')
			}
			else
				appendCondition(it, ruleGroup.eContainer)
		}
	}

	private def appendStrategyRule(ITreeAppendable it, StrategyReference strategyReference){
		append('.with(')
		append(strategyReference.newTypeRef(StrategyConverter).type)
		append('.toStrategy(')
		appendReference(it, STRATEGY, strategyReference.expr)
		append('))')
	}

	private def appendTriggers(ITreeAppendable it, RuleGroup ruleGroup) {
		val triggers = triggers(ruleGroup)
		if(! triggers.empty){
			append('.when(')
			var firstValue = true
			for(name : triggers){
				if(firstValue){
					firstValue = false
				}
				else
					append(', ')
				append('''"«name.escapeQuotes»"''')
			}
			append(')')
		}
	}

	private  def Collection<String> triggers(RuleGroup ruleGroup){
		val triggers =  new ArrayList<String>()
		var group = ruleGroup
		val container = group.eContainer
		if(container instanceof RuleGroup)
			triggers.addAll(triggers(container as RuleGroup))
		val trigger = group.trigger
		if(trigger != null){
			triggers.addAll(trigger.properties)
		}
		return triggers
	}

	def private appendRulePropertyName(ITreeAppendable it, Rule rule) {
		appendRuleBuilderMethodCall(it, 'iterate', rule.name, rule.nameExpressions)
	}
	
	def private appendRuleName(ITreeAppendable it, RuleGroup rule) {
		if(rule.ruleName != null ||  ! rule.ruleNameExpressions.empty)
			appendRuleBuilderMethodCall(it, 'rule', rule.ruleName, rule.ruleNameExpressions)
	}
	
	def private appendRuleName(ITreeAppendable it, DisabledRule rule) {
		if(rule.ruleName != null ||  ! rule.ruleNameExpressions.empty)
			appendRuleBuilderMethodCall(it, 'rule', rule.ruleName, rule.ruleNameExpressions)
	}
	
	private def appendRuleBuilderMethodCall(ITreeAppendable it, String method, String name, EList<XExpression> ruleNameExpressions) {
		append('''.«method»(''')
		if(name != null) {
			val effectiveName = name.escapeQuotes;
			append('"') append(effectiveName) append('"')
		}
		else{
			appendNameExpressions(it, ruleNameExpressions)
		}
		append(')')
	}

	private def appendNameExpressions(ITreeAppendable it, EList<XExpression> expressions) {
			append("new StringBuilder()");

		for (expr : expressions){
			append(".append(");
			val methodName = methods.get(NAME, expr)
			if(methodName == null){
				xbaseCompiler.compileAsJavaExpression(expr, it, strategy.newTypeRef(Object))
			}
			else{
				append('''«methodName»()''')
			}
			append(")");
		}
			append(".toString()")
	}

	def  private void appendRuleValues(ITreeAppendable it, Values values, boolean appendActionRuleGroups) {
		for(action:values.actions){
			val valueAction = action as ValueAction
			apppendValueAction(it, valueAction)
			if(appendActionRuleGroups)
				appendActionRuleGroups(it, valueAction)
		}
	}

	private def appendRuleOrder(ITreeAppendable it, Rule rule) {
		if(rule.ordered)
				append('.ordered()')
		else if(rule.shuffled)
				append('.shuffled()')
	}

	 def private appendActionRuleGroups(ITreeAppendable it, ValueAction valueAction){
			val innerGroups =  valueAction.ruleGroups
			appendInnerGroups(it, innerGroups, true)
	}

	def private boolean appendInnerGroups(ITreeAppendable it, Collection<RuleGroup> innerGroups, boolean first) {
		var shouldStartMethodCall = first
		for(group:innerGroups){
			if(! shouldStartMethodCall && group.strategy != null){
				decreaseIndentation
				newLine
				append(')')
				shouldStartMethodCall = true
			}
			if(group.rule != null || group.strategy != null|| group?.condition?.expr != null){
				if(shouldStartMethodCall){
					shouldStartMethodCall = false
					append('.with(')
					increaseIndentation
					newLine
				}
				else{
					append(',')
					newLine
				}
				if(group?.condition?.expr != null){
					appendConditionDefaultRule(it, group)
					if(group.rule != null){
						append(', ')
						newLine
					}
					else if (group.strategy != null){
						append(')')
						newLine
						append('.with(')
					}
				}
				if(group.rule != null || group.strategy != null)
					appendRule(it, group, true)
			}
			if(group.strategy != null){
				decreaseIndentation
				newLine
				append(')')
				shouldStartMethodCall = true
			}
			else
				shouldStartMethodCall = appendInnerGroups(it, group.ruleGroups, shouldStartMethodCall)
		}
		if(first && ! shouldStartMethodCall){
			decreaseIndentation
			newLine
			append(')')
			shouldStartMethodCall = true
		}
		return shouldStartMethodCall
	}

	def private void appendRuleGroup(ITreeAppendable it, RuleGroup ruleGroup) {
		if(ruleGroup?.condition?.expr != null){
			append('_strategy.addRule(')
			appendConditionDefaultRule(it, ruleGroup)
			append(');')
			newLine
		}
		val rule = ruleGroup.rule
		if(rule != null || ruleGroup.strategy != null){
			if(rule != null)
				append('_strategy.addRule(')
			else
				append('_strategy.addRules(')
			appendRule(it, ruleGroup, false)
			append(');')
			newLine
		}
		else{
			for(innerRuleGroup : ruleGroup.ruleGroups)
				appendRuleGroup(it, innerRuleGroup)
		}
	}




	def private apppendValueAction(ITreeAppendable it, ValueAction valueAction) {
			appendValueAction(it, valueAction)
	}

	def private appendValueAction(ITreeAppendable it, ValueAction valueAction) {
		append('.over(')
		var firstValue = true
		appendValueProviders(it, valueAction, firstValue)
		append(')')
	}

	private def boolean  appendValueProviders(ITreeAppendable it, ValueAction valueAction, boolean started) {
		var firstValue = started
		for (valueProvider: valueAction.valueProviders){
			if(valueProvider.valueReference != null){
				for(referencedValueAction : valueProvider.valueReference.actions){
					firstValue = appendValueProviders(it, referencedValueAction, firstValue)
				}
			}
			else {
				if(firstValue)
					firstValue = false
				else
					append(', ')
				val expressions = valueProvider.expressions
				if(valueProvider.condition == null && expressions.size == 1 && methods.get(VALUE, expressions.get(0)) == null){
					if(! OPTIMIZE_FOR_DEBUG)
						xbaseCompiler.compileAsJavaExpression(expressions.get(0), it, valueProvider.newTypeRef(Object))
					else{
					appendImplementationObject(it, valueAction.newTypeRef(org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider).type, "Object value",
						[
							append('return ') xbaseCompiler.compileAsJavaExpression(expressions.get(0), it, valueProvider.newTypeRef(Object)) append(';')
						])
					}
				}
				else
					appendImplementationObject(it, valueAction.newTypeRef(org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider).type, "Object value",
						[appendValueExpression(it, valueProvider)])

			}
		}
		return firstValue
	}

	def private appendValueExpression(ITreeAppendable it, ValueProvider valueProvider) {
		newLine
		appendValueCondition(it, valueProvider)
		val trace = valueProvider.trace
		if(trace)
			appendTraceStart(it, valueProvider)
		append('Object _value = ')
		val expressions = valueProvider.expressions
		appendValueExpressions(it, expressions);
		append(';')
		newLine
		append('return _value;')
		if(trace)
			appendTraceEnd(it, valueProvider)
	}

	private def appendValueExpressions(ITreeAppendable it, EList<XExpression> expressions) {
		val concatenation = expressions.size() > 1
		if(concatenation)
			append("new StringBuilder()");

		for (expr : expressions){
			if(concatenation)
				append(".append(");
			val methodName = methods.get(VALUE, expr)
			if(methodName == null){
				xbaseCompiler.compileAsJavaExpression(expr, it, strategy.newTypeRef(Object))
			}
			else{
				append(strategy.newTypeRef(ValueProviderHelper).type) append('''.toValue(«methodName»(propertyContainer), propertyContainer)''')
			}
			if(concatenation)
				append(")");
		}
		if(concatenation)
			append(".toString()")
	}

	private def appendTraceEnd(ITreeAppendable it, EObject object) {
		newLine
		append('} finally{ ((')
		append(object.newTypeRef(CoverageTrackerEnabler).type)
		append(')propertyContainer).stopTrace();}')
	}

	private def appendTraceStart(ITreeAppendable it, EObject object) {
		append('((')
		append(object.newTypeRef(CoverageTrackerEnabler).type)
		append(')propertyContainer).startTrace(); try{')
		newLine
	}

	private def appendValueCondition(ITreeAppendable it, ValueProvider valueProvider) {
		val conditional = valueProvider.condition != null
		if(conditional){
			val methodName = methods.get(CONDITION, valueProvider.condition.expr)
			if(valueProvider.condition.trace)
				appendTraceStart(it, valueProvider)

			append('''if(!«methodName»(propertyContainer)) return ''')
			append(valueProvider.newTypeRef(SpecialValue).type) append('.SKIP;')
			if(valueProvider.condition.trace)
				appendTraceEnd(it, valueProvider)
			newLine
		}
	}

	private def appendImplementationObject(ITreeAppendable it, JvmType interfaceName, String interfaceMethodName, Later expression) {
		append('new ')
		append(interfaceName)
		append('(){')
		increaseIndentation
		newLine
		append('''@Override public «interfaceMethodName»(''')
		.append(strategy.newTypeRef(PropertyContainer).type)
		.append(''' propertyContainer) {''')
		increaseIndentation
		expression.exec(it)
		decreaseIndentation
		decreaseIndentation
		newLine
		append('}')
		append('}')
	}


	def private appendReference(ITreeAppendable it, String prefix, XExpression expr) {
		if(expr != null){
			val methodName = methods.get(prefix, expr)
			if(methodName != null){
				append(methodName)
				append('()')
			}
			else
				append(expr.toString)
		}
	}

	static def strategyClassName(String strategyName) {
		"_" + strategyName + "_StrategyFactory"
	}

}