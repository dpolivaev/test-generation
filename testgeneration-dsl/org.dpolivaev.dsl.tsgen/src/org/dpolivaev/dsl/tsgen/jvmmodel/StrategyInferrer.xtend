package org.dpolivaev.dsl.tsgen.jvmmodel

import com.google.inject.Injector
import java.util.ArrayList
import java.util.Collection
import javax.inject.Inject
import org.dpolivaev.dsl.tsgen.strategydsl.Condition
import org.dpolivaev.dsl.tsgen.strategydsl.Rule
import org.dpolivaev.dsl.tsgen.strategydsl.RuleGroup
import org.dpolivaev.dsl.tsgen.strategydsl.StrategyReference
import org.dpolivaev.dsl.tsgen.strategydsl.ValueAction
import org.dpolivaev.dsl.tsgen.strategydsl.ValueProvider
import org.dpolivaev.dsl.tsgen.strategydsl.Values
import org.dpolivaev.tsgen.coverage.CoverageEntry
import org.dpolivaev.tsgen.coverage.CoverageTrackerEnabler
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy
import org.dpolivaev.tsgen.coverage.StrategyConverter
import org.dpolivaev.tsgen.ruleengine.PropertyContainer
import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory
import org.dpolivaev.tsgen.ruleengine.SpecialValue
import org.dpolivaev.tsgen.ruleengine.Strategy
import org.dpolivaev.tsgen.ruleengine.ValueProviderHelper
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
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

import static extension org.dpolivaev.dsl.tsgen.jvmmodel.StrategyCompiler.*
import org.eclipse.emf.ecore.util.EcoreUtil

class StrategyInferrer{
	@Inject Injector injector
	@Inject extension JvmTypesBuilder jvmTypesBuilder
	@Inject XbaseCompiler xbaseCompiler
	val Methods methods
	var JvmGenericType jvmType
	var org.dpolivaev.dsl.tsgen.strategydsl.Strategy strategy

	new(){
		this.methods = new Methods
	}


	def void inferStrategy(JvmGenericType jvmType, org.dpolivaev.dsl.tsgen.strategydsl.Strategy strategy){
		this.strategy = strategy
		this.jvmType = jvmType
		if(!strategy.parameters.empty)
			inferConstructor()
		inferExpressions()
		inferStrategyMethods()
	}

	def inferConstructor() {
			for(parameter:strategy.parameters)
				jvmType.members += parameter.toField(parameter.name, parameter.parameterType)[
					visibility = JvmVisibility::PRIVATE
					final = true
				]

			jvmType.members += strategy.toConstructor[
				for(parameter:strategy.parameters)
					parameters += parameter.toParameter(parameter.name, parameter.parameterType)
				body = [
					for(parameter:strategy.parameters){
						append('''this.«parameter.name» = «parameter.name»;''')
						newLine
					}
				]
				visibility = JvmVisibility::PUBLIC
			]
	}


	private def inferExpressions(){
		val contents = EcoreUtil2.eAllContents(strategy)
		for(obj : contents){
			if (obj instanceof ValueAction)
				appendValueProviders(obj)
			if (obj instanceof Rule)
				appendRuleNameProviders(obj)
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
	private def appendValueProviders(ValueAction action){
		for(valueProvider:action.valueProviders)
			for(expr:valueProvider.expressions)
				if(shouldCreateMethodFor(expr)) {
					createMethod(expr, VALUE, expr.inferredType, true)
				}

	}

	final static val NAME = "_name"
	private def appendRuleNameProviders(Rule rule){
			for(expr:rule.nameExpressions)
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

	def private void addAppliedStrategyItems(ITreeAppendable it, org.dpolivaev.dsl.tsgen.strategydsl.Strategy strategy){
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

	def private void appendRuleGroup(ITreeAppendable it, RuleGroup ruleGroup) {
		val rule = ruleGroup.rule
		if(rule != null || ruleGroup.strategy != null){
			append('_strategy.addRule(')
			appendRule(it, ruleGroup, false)
			append(');')
			newLine
		}
		else{
			for(innerRuleGroup : ruleGroup.ruleGroups)
				appendRuleGroup(it, innerRuleGroup)
		}
	}

	def private void appendRule(ITreeAppendable it, RuleGroup ruleGroup, boolean temporaryRule) {
		append(strategy.newTypeRef(Factory).type)
		appendTriggers(it, ruleGroup)
		appendConditions(it, ruleGroup)
		val rule = ruleGroup.rule
		if(rule !=null){
			if(rule.skip){
				apppendSkip(it)
			}
			else{
				appendRuleName(it, rule)
				appendRuleValues(it, rule.values, true)
				appendRuleOrder(it, rule)
			}
		}
		else if(ruleGroup.strategy !=null){
			appendStrategyRule(it, ruleGroup.strategy)
		}
		if(rule != null && rule.isDefault)
			append('.asDefaultRule()')
		else if(temporaryRule)
			append('.asTriggeredRule()')
		else
			append('.asRule()')
	}

	private def appendStrategyRule(ITreeAppendable it, StrategyReference strategyReference){
		append('.iterate(" ')
		append(EcoreUtil.getURI(strategyReference).toString)
		append('")')
		append('.over(')
		append(strategyReference.newTypeRef(SpecialValue).type)
		append('.UNDEFINED)')
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

	private def appendConditions(ITreeAppendable it, RuleGroup ruleGroup) {
		val conditions = conditions(ruleGroup)
		if(! conditions.isEmpty){
			conditions.reverse
			append('._if(')
			appendImplementationObject(it, ruleGroup.newTypeRef(org.dpolivaev.tsgen.ruleengine.Condition).type, "boolean isSatisfied",
				[appendCalledMethods(conditions, it)]
			)
			append(')')
		}
	}

	def private appendCalledMethods(Condition[] conditions, ITreeAppendable it) {
		for(condition:conditions){
			val calledMethodName = methods.get(CONDITION, condition.expr)
			val trace = condition.trace
			newLine
			if(trace)
				appendTraceStart(it, condition)
			append('''if (!«calledMethodName»(propertyContainer)) return false;''')
			if(trace)
				appendTraceEnd(it, condition)
		}
		newLine
		append('return true;')
	}

	def private conditions(RuleGroup ruleGroup) {
		val methodNames = new ArrayList<Condition>()
		var group = ruleGroup
		do {
			val condition = group.condition
			if(condition != null && condition.expr != null){
				methodNames.add(condition)
			}
			val container = group.eContainer
			if(container instanceof RuleGroup){
				group = container as RuleGroup
			}
			else
				return methodNames
		} while(true)
	}


	def private appendRuleName(ITreeAppendable it, Rule rule) {
		append('.iterate(')
		if(rule.name != null) {
			val name = rule.name.escapeQuotes;
			append('"') append(name) append('"')
		}
		else{
			appendNameExpressions(it, rule.nameExpressions)
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
		if(values.valueReference != null){
			appendRuleValues(it, values.valueReference, false)
			return
		}
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

		def private void appendInnerGroups(ITreeAppendable it, Collection<RuleGroup> innerGroups, boolean first) {
			var firstLine = first
			for(group:innerGroups){
				if(group.rule != null || group.strategy != null){
					if(firstLine){
						firstLine = false
						append('.with(')
						increaseIndentation
						newLine
					}
					else{
						append(',')
						newLine
					}
					appendRule(it, group, true)
				}
				appendInnerGroups(it, group.ruleGroups, firstLine)
			}
			if(first && ! firstLine){
				decreaseIndentation
				newLine
				append(')')
			}
		}
	def private apppendValueAction(ITreeAppendable it, ValueAction valueAction) {
			appendValueAction(it, valueAction)
	}

	def private appendValueAction(ITreeAppendable it, ValueAction valueAction) {
		append('.over(')
		var firstValue = true
		for (valueProvider: valueAction.valueProviders){
			if(firstValue)
				firstValue = false
			else
				append(', ')
			val expressions = valueProvider.expressions
			if(valueProvider.condition == null && expressions.size == 1 && methods.get(VALUE, expressions.get(0)) == null)
				xbaseCompiler.compileAsJavaExpression(expressions.get(0), it, valueProvider.newTypeRef(Object))
			else
				appendImplementationObject(it, valueAction.newTypeRef(org.dpolivaev.tsgen.ruleengine.ValueProvider).type, "Object value",
					[appendValueExpression(it, valueProvider)])
		}
		append(')')
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

	def private apppendSkip(ITreeAppendable it){
		append('.skip()')
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
