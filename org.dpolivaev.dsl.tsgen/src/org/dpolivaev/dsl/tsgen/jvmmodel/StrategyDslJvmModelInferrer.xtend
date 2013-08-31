package org.dpolivaev.dsl.tsgen.jvmmodel

import com.google.inject.Inject
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.dpolivaev.dsl.tsgen.strategydsl.Model
import ruleengine.Strategy
import org.eclipse.xtext.common.types.JvmVisibility
import ruleengine.StatefulRuleBuilder.Factory
import org.eclipse.xtext.common.types.JvmGenericType
import org.dpolivaev.dsl.tsgen.strategydsl.ValueAction
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.dpolivaev.dsl.tsgen.strategydsl.Rule
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import org.eclipse.xtext.EcoreUtil2
import org.dpolivaev.dsl.tsgen.strategydsl.Condition
import org.eclipse.xtext.xbase.XExpression
import ruleengine.PropertyContainer
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.xbase.XNumberLiteral
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.xbase.XBooleanLiteral
import org.eclipse.xtext.xbase.XNullLiteral
import ruleengine.ValueProvider
import org.dpolivaev.dsl.tsgen.strategydsl.RuleGroup
import java.util.ArrayList
import java.util.Collection
import org.dpolivaev.dsl.tsgen.strategydsl.SkipAction

/**
 * <p>Infers a JVM model from the source model.</p> 
 *
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>     
 */
class StrategyDslJvmModelInferrer extends AbstractModelInferrer {
	/**
     * convenience API to build and initialize JVM types and their members.
     */
	@Inject extension JvmTypesBuilder
	@Inject XbaseCompiler xbaseCompiler
	JvmType ruleFactoryType
	val methods = new HashMap<EObject, String>

	/**
	 * The dispatch method {@code infer} is called for each instance of the
	 * given element's type that is contained in a resource.
	 * 
	 * @param element
	 *            the model to create one or more
	 *            {@link org.eclipse.xtext.common.types.JvmDeclaredType declared
	 *            types} from.
	 * @param acceptor
	 *            each created
	 *            {@link org.eclipse.xtext.common.types.JvmDeclaredType type}
	 *            without a container should be passed to the acceptor in order
	 *            get attached to the current resource. The acceptor's
	 *            {@link IJvmDeclaredTypeAcceptor#accept(org.eclipse.xtext.common.types.JvmDeclaredType)
	 *            accept(..)} method takes the constructed empty type for the
	 *            pre-indexing phase. This one is further initialized in the
	 *            indexing phase using the closure you pass to the returned
	 *            {@link org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor.IPostIndexingInitializing#initializeLater(org.eclipse.xtext.xbase.lib.Procedures.Procedure1)
	 *            initializeLater(..)}.
	 * @param isPreIndexingPhase
	 *            whether the method is called in a pre-indexing phase, i.e.
	 *            when the global index is not yet fully updated. You must not
	 *            rely on linking using the index if isPreIndexingPhase is
	 *            <code>true</code>.
	 */
	def dispatch void infer(Model script, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
		val className = script.eResource.URI.trimFileExtension.lastSegment.toFirstUpper
		val qualifiedClassName = if(script.package != null) script.package + '.' + className else className
		ruleFactoryType = script.newTypeRef(Factory).type 
		acceptor.accept(script.toClass(qualifiedClassName)).initializeLater(
			[
				methods.clear
				inferExpressions(it, script)
				inferStrategyFields(it, script)
				inferStrategyMethods(it, script)
			])
	}
	
	private def inferExpressions(JvmGenericType it, Model script){
		val contents = EcoreUtil2.eAllContents(script)
		for(obj : contents){
			if (obj instanceof ValueAction) 
				appendValueProviders(it, obj as ValueAction)
			else if (obj instanceof Condition) 
				appendConditions(it, obj as Condition)
		}
	}
	
	private def appendValueProviders(JvmGenericType it, ValueAction action){
		for(expr:action.valueProvider){
			if(shouldCreateMethodFor(expr)){
				createMethod(it, expr, "valueProvider", expr.newTypeRef(Object))
			}
		}
	}
	
	private def createMethod(JvmGenericType it, XExpression expr, String prefix, JvmTypeReference typeRef){
				val valueProviderCounter = methods.size + 1
				val name = prefix + valueProviderCounter
				methods.put(expr, name)
				members += expr.toMethod(name, typeRef)[
					parameters += expr.toParameter("propertyContainer", expr.newTypeRef(PropertyContainer))
					body = expr
					static = true
					visibility = JvmVisibility::PRIVATE
				]
	}
	
	private def shouldCreateMethodFor(XExpression expr){
		return !(expr instanceof XStringLiteral || expr instanceof XNumberLiteral || expr instanceof XBooleanLiteral || expr instanceof XNullLiteral)
	}
	
	private def appendConditions(JvmGenericType it, Condition condition){
		if(condition != null){
			val expr = condition.expr
			if(expr != null){
				createMethod(it, expr, "condition", expr.newTypeRef(Boolean))
			}
		}
	}
	
	private def inferStrategyMethods(JvmGenericType it, Model script){
		for (strategy : script.strategy) {
			val methodName = "defineStrategy" + strategy.name.toFirstUpper
			members += strategy.toMethod(methodName, strategy.newTypeRef(Strategy)) [
				body = [
					
					append('''Strategy strategy = new Strategy();''')
					newLine
					for(ruleGroup:strategy.ruleGroups){
						appendRuleGroup(it, ruleGroup)
					}
					append('''return strategy;''')
				]
				visibility = JvmVisibility::PRIVATE
			]
		}
	}

	def private void appendRuleGroup(ITreeAppendable it, RuleGroup ruleGroup) {
		val rule = ruleGroup.rule
		if(rule != null){
			append('strategy.addRule(')
			appendRule(it, ruleGroup)
			append(');')
			newLine
		}
		else{
			for(innerRuleGroup : ruleGroup.ruleGroups)
				appendRuleGroup(it, innerRuleGroup)
		}
	}

	def private void appendRule(ITreeAppendable it, RuleGroup ruleGroup) {
		val rule = ruleGroup.rule
		append(ruleFactoryType)
		appendTriggers(it, ruleGroup)
		appendConditions(it, ruleGroup)
		appendRuleValues(it, rule)
		appendRuleOrder(it, rule)
		if(rule.isDefault)
			append('.asDefaultRule()')
		else
			append('.asRule()')
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
				append('''"«name»"''')	
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
			appendImplementationObject(it, ruleGroup.newTypeRef(ruleengine.Condition).type, "boolean isSatisfied", conditions)
			append(')')
		}
	}

	def private conditions(RuleGroup ruleGroup) {
		val methodNames = new ArrayList<String>()
		var group = ruleGroup
		do {
			val condition = group.condition
			if(condition != null && condition.expr != null){
				val methodName = methods.get(condition.expr)
				methodNames.add(methodName) 
			}
			val container = group.eContainer
			if(container instanceof RuleGroup){
				group = container as RuleGroup
			}
			else
				return methodNames
		} while(true)
	}
	
	private def appendRuleValues(ITreeAppendable it, Rule rule) {
		append('''.iterate("«rule.name»")''')
		for(action:rule.actions){
			if(action instanceof ValueAction){
				val valueAction = action as ValueAction
				apppendValueAction(it, valueAction)
				appendActionRuleGroups(it, valueAction)
			}
			else if(action instanceof SkipAction){
				apppendSkip(it)
			}
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
			if(! innerGroups.empty){
				append('.with(')
				var first = true
				increaseIndentation
				newLine
				appendInnerGroups(it, innerGroups, first)
				decreaseIndentation
				newLine
				append(')')
				
			}
	}

		def private void appendInnerGroups(ITreeAppendable it, Collection<RuleGroup> innerGroups, boolean first) {
			var firstLine = first
			for(group:innerGroups){
				if(group.rule != null){	
					if(firstLine)
						firstLine = false
					else{
						append(',')
						newLine
					}
					appendRule(it, group)
				}
				appendInnerGroups(it, group.ruleGroups, firstLine)
			}
		}

	def private apppendValueAction(ITreeAppendable it, ValueAction valueAction) {
		append('.over(')
		var firstValue = true
		for (expr: valueAction.valueProvider){
			if(firstValue)
				firstValue = false
			else
				append(', ')
			val methodName = methods.get(expr)
			if(methodName == null)
				xbaseCompiler.compileAsJavaExpression(expr, it, valueAction.newTypeRef(Object))
			else{
				appendImplementationObject(it, valueAction.newTypeRef(ValueProvider).type, "Object value", methodName)
			}
		}
		append(')')
	}
	
	def private apppendSkip(ITreeAppendable it){
		append('.skip()')
	}

	private def appendImplementationObject(ITreeAppendable it, JvmType interfaceName, String interfaceMethodName, String... calledMethodNames) {
		append('new ')
		append(interfaceName)
		append('(){')
		increaseIndentation
		newLine
		append('''@Override public «interfaceMethodName»(PropertyContainer propertyContainer) { return ''')
		var first = true
		for(calledMethodName:calledMethodNames){
			if(first)
				first = false
			else
				append(' && ')
			append('''«calledMethodName»(propertyContainer)''')
		}
		append('; }')
		decreaseIndentation
		newLine
		append('}')
	}
	
	private def inferStrategyFields(JvmGenericType it, Model script){
		for (strategy : script.strategy) {
			val methodName = "defineStrategy" + strategy.name.toFirstUpper
			members += strategy.toField(strategy.name.toFirstLower, strategy.newTypeRef(Strategy)) [
				setInitializer [
					append('''«methodName»()''')
				]
				visibility = JvmVisibility::DEFAULT
			]
		}
	}
}
