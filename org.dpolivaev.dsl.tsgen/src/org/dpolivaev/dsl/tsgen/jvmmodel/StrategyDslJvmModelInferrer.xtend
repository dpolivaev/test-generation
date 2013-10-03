package org.dpolivaev.dsl.tsgen.jvmmodel

import com.google.inject.Inject
import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import org.dpolivaev.dsl.tsgen.strategydsl.Condition
import org.dpolivaev.dsl.tsgen.strategydsl.Generation
import org.dpolivaev.dsl.tsgen.strategydsl.Rule
import org.dpolivaev.dsl.tsgen.strategydsl.RuleGroup
import org.dpolivaev.dsl.tsgen.strategydsl.SkipAction
import org.dpolivaev.dsl.tsgen.strategydsl.ValueAction
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
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.dpolivaev.tsgen.ruleengine.PropertyContainer
import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory
import org.dpolivaev.tsgen.ruleengine.ValueProvider
import org.dpolivaev.dsl.tsgen.strategydsl.StrategyReference

import static extension org.dpolivaev.dsl.tsgen.jvmmodel.StrategyCompiler.*

import java.util.Set
import java.util.HashSet
import java.util.Arrays
import org.eclipse.xtext.xbase.compiler.Later
import org.dpolivaev.tsgen.scriptwriter.StrategyRunner
import org.dpolivaev.dsl.tsgen.strategydsl.OutputConfiguration

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
	@Inject extension JvmTypesBuilder jvmTypesBuilder
	@Inject XbaseCompiler xbaseCompiler

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
	def dispatch void infer(Generation script, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
		val className = script.eResource.URI.trimFileExtension.lastSegment.toFirstUpper
		val qualifiedClassName = if(script.package != null) script.package + '.' + className else className
		acceptor.accept(script.toClass(qualifiedClassName)).initializeLater([
			new ScriptInitializer(jvmTypesBuilder, xbaseCompiler, it, script).initializeClass()
		])
	}
}

class ScriptInitializer{
	val extension JvmTypesBuilder jvmTypesBuilder
	val XbaseCompiler xbaseCompiler
	val HashMap<EObject, String> methods
	val Set<String> declaredStrategies
	val JvmGenericType jvmType
	val Generation script
	val JvmType ruleFactoryType

	new(extension JvmTypesBuilder jvmTypesBuilder, XbaseCompiler xbaseCompiler, JvmGenericType jvmType, Generation script){
		this.jvmTypesBuilder = jvmTypesBuilder
		this.xbaseCompiler = xbaseCompiler
		this.methods = new HashMap<EObject, String>
		this.declaredStrategies = new HashSet<String>
		this.script = script
		this.jvmType = jvmType
		ruleFactoryType = script.newTypeRef(Factory).type 
	}
	
	
	def initializeClass(){
		inferExpressions()
		inferStrategyFields()
		inferStrategyMethods()
		inferRunMethods()
	}
	
	private def inferExpressions(){
		for(strategy : script.strategies)
			declaredStrategies += strategy.name
		val contents = EcoreUtil2.eAllContents(script)
		for(obj : contents){
			if (obj instanceof ValueAction) 
				appendValueProviders(obj as ValueAction)
			else if (obj instanceof Condition) 
				appendConditions(obj as Condition)
			else if (obj instanceof StrategyReference) 
				appendStrategyReferences(obj as StrategyReference)
		}
	}
	
	private def appendStrategyReferences(StrategyReference ref){
		if(ref.expr != null && ! declaredStrategies.contains(ref.expr.toString))
			createMethod(ref.expr, "externalStrategy", ref.expr.newTypeRef(org.dpolivaev.tsgen.ruleengine.Strategy), false)
	}
	
	private def appendValueProviders(ValueAction action){
		for(expr:action.valueProviders){
			if(shouldCreateMethodFor(expr)){
				createMethod(expr, "valueProvider", expr.newTypeRef(Object), true)
			}
		}
	}
	
	private def createMethod(XExpression expr, String prefix, JvmTypeReference resultTypeRef, boolean useParameters){
				val valueProviderCounter = methods.size + 1
				val name = prefix + valueProviderCounter
				methods.put(expr, name)
				jvmType.members += expr.toMethod(name, resultTypeRef)[
					if(useParameters)
						parameters += expr.toParameter("propertyContainer", expr.newTypeRef(PropertyContainer))
					body = expr
					visibility = JvmVisibility::PRIVATE
				]
	}
	
	private def shouldCreateMethodFor(XExpression expr){
		return !(expr instanceof XStringLiteral || expr instanceof XNumberLiteral || expr instanceof XBooleanLiteral || expr instanceof XNullLiteral)
	}
	
	private def appendConditions(Condition condition){
		if(condition != null){
			val expr = condition.expr
			if(expr != null){
				createMethod(expr, "condition", expr.newTypeRef(Boolean), true)
			}
		}
	}
	
	private def inferStrategyMethods(){
		for (strategy : script.strategies) {
			val methodName = "defineStrategy" + strategy.name.toFirstUpper
			jvmType.members += strategy.toMethod(methodName, strategy.newTypeRef(org.dpolivaev.tsgen.ruleengine.Strategy)) [
				body = [
					
					append('Strategy strategy = new Strategy()')
					combinedStrategy(it, strategy.baseStrategies, false)
					append(';')
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
		val rule = ruleGroup.rule
		append(ruleFactoryType)
		appendTriggers(it, ruleGroup)
		appendConditions(it, ruleGroup)
		appendRuleValues(it, rule)
		appendRuleOrder(it, rule)
		if(rule.isDefault)
			append('.asDefaultRule()')
		else if(temporaryRule)
			append('.asTriggeredRule()')
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
			appendImplementationObject(it, ruleGroup.newTypeRef(org.dpolivaev.tsgen.ruleengine.Condition).type, "boolean isSatisfied", 
				[appendCalledMethods(conditions, it)]
			)
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
		val name = rule.name.escapeQuotes;
		append('.iterate("')
		if(rule.requirement)
			append('requirement.')
		append(name)
		append('")')
		for(action:rule.actions){
			if(action instanceof ValueAction){
				val valueAction = action as ValueAction
				apppendValueAction(it, valueAction, rule.requirement)
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
					appendRule(it, group, true)
				}
				appendInnerGroups(it, group.ruleGroups, firstLine)
			}
		}
	def private apppendValueAction(ITreeAppendable it, ValueAction valueAction, boolean requirement) {
		if(requirement)
			apppendRequirementValueAction(it, valueAction)
		else
			appendValueAction(it, valueAction)	
	}
	
	def private appendValueAction(ITreeAppendable it, ValueAction valueAction) {	
		append('.over(')
		var firstValue = true
		for (expr: valueAction.valueProviders){
			if(firstValue)
				firstValue = false
			else
				append(', ')
			val methodName = methods.get(expr)
			if(methodName == null)
				xbaseCompiler.compileAsJavaExpression(expr, it, valueAction.newTypeRef(Object))
			else{
				appendImplementationObject(it, valueAction.newTypeRef(ValueProvider).type, "Object value", 
					[append('''«methodName»(propertyContainer)''')]
				)
			}
		}
		append(')')
	}
	
	def private apppendRequirementValueAction(ITreeAppendable it, ValueAction valueAction) {
		append('.over(')
		appendImplementationObject(it, valueAction.newTypeRef(ValueProvider).type, "Object value",
			[
				append(valueAction.newTypeRef(Arrays).type)
				append('.asList(')
				var firstValue = true
				for (expr: valueAction.valueProviders){
					if(firstValue)
						firstValue = false
					else
						append(', ')
					val methodName = methods.get(expr)
					if(methodName == null)
						xbaseCompiler.compileAsJavaExpression(expr, it, valueAction.newTypeRef(Object))
					else
						append('''«methodName»(propertyContainer)''')
				}
				append(')')
			]
		) 
		append(')')
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
		.append(script.newTypeRef(PropertyContainer).type)
		.append(''' propertyContainer) { return ''')
		expression.exec(it)
		append('; }')
		decreaseIndentation
		newLine
		append('}')
	}

	def private appendCalledMethods(String[] calledMethodNames, ITreeAppendable it) {
		var first = true
		for(calledMethodName:calledMethodNames){
			if(first)
				first = false
			else
				append(' && ')
			append('''«calledMethodName»(propertyContainer)''')
		}
	}
	
	private def inferStrategyFields(){
		for (strategy : script.strategies) {
			val methodName = "defineStrategy" + strategy.name.toFirstUpper
			jvmType.members += strategy.toField(strategy.name.toFirstLower, strategy.newTypeRef(org.dpolivaev.tsgen.ruleengine.Strategy)) [
				setInitializer [
					append('''«methodName»()''')
				]
				final = true
				visibility = JvmVisibility::PUBLIC
			]
		}
	}
	
	private def inferRunMethods(){
		inferRunMethodImplementations();
		inferMainMethod();
	}
	
	private def inferRunMethodImplementations(){
		var counter = 0
		for (run : script.runs) {
			counter = counter + 1
			val methodName = "run" + counter
			jvmType.members += run.toMethod(methodName, run.newTypeRef(void)) [
				body = [
					append(run.newTypeRef(StrategyRunner).type)
					append(' ')
					append('strategyRunner = new ')
					append(run.newTypeRef(StrategyRunner).type)
					append('();')
					appendOutputConfiguration(it, "Output", run.outputConfiguration)
					appendOutputConfiguration(it, "Report", run.reportConfiguration)
					newLine
					append('strategyRunner.run(')
					combinedStrategy(it, run.strategies, true)
					append(');')
				]
				visibility = JvmVisibility::PUBLIC
			]
		}
	}

	def private appendOutputConfiguration(ITreeAppendable it, String target, OutputConfiguration outputConfiguration) {
		if(outputConfiguration != null){
			newLine
			append('strategyRunner.get' + target + 'Configuration()')
			if(outputConfiguration.xslt != null){
				appendOutputFile(it, "Xml", outputConfiguration.xml)
				append('.setXsltSource("')
				append(outputConfiguration.xslt)
				append('")')
				appendOutputFile(it, "File", outputConfiguration.fileExtension)
			}
			else
				appendOutputFile(it, "File", outputConfiguration.xml)
			append(';')
		}
	}

	def private appendOutputFile(ITreeAppendable it, String target, String fileConfig) {
		if(fileConfig != null){
			val path = fileConfig.replace('\\'.charAt(0), '/'.charAt(0))
			val lastSeparator = path.lastIndexOf('/')
			if(lastSeparator == -1){
				append('.set' + target + 'Extension("')
				append(fileConfig)
				append('")')
			}
			else{
				append('.set' + target + 'Directory("')
				append(fileConfig.substring(0, lastSeparator))
				append('")')
				append('.set' + target + 'Extension("')
				append(fileConfig.substring(lastSeparator + 1))
				append('")')
			}
		}
	}
	
	private def combinedStrategy(ITreeAppendable it, Collection<StrategyReference> strategies, boolean startWithStrategyName){
		var first = startWithStrategyName
		for(strategy : strategies){
			if(first){
				first = false
				appendStrategyReference(it, strategy)
			}
			else{
				append(".with(")
				appendStrategyReference(it, strategy)
				append(")")
			}
		}
		return it.toString
	}

	def private appendStrategyReference(ITreeAppendable it, StrategyReference ref) {
		val methodName = methods.get(ref.expr)
		if(methodName != null){
			append(methodName)
			append('()')
		}
		else
			append(ref.expr.toString.toFirstLower)
	}
	
	private def inferMainMethod(){
		if(! script.runs.empty){
			val className = jvmType.simpleName
			jvmType.members += script.toMethod("main", script.newTypeRef(Void::TYPE)) [
				parameters += script.toParameter("args", script.newTypeRef(typeof(String)).addArrayTypeDimension)
				body = [
					append('''«className» instance = new «className»();''')
					var counter = 0
					for (run : script.runs) {
						counter = counter + 1
						val methodName = "run" + counter
						newLine
						append('''instance.«methodName»();''')
						}
				]
				visibility = JvmVisibility::PUBLIC
				static = true
			]
		}
	}
	
}
