package org.dpolivaev.dsl.tsgen.jvmmodel

import com.google.inject.Inject
import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.Set
import org.dpolivaev.dsl.tsgen.strategydsl.Condition
import org.dpolivaev.dsl.tsgen.strategydsl.Generation
import org.dpolivaev.dsl.tsgen.strategydsl.OutputConfiguration
import org.dpolivaev.dsl.tsgen.strategydsl.Rule
import org.dpolivaev.dsl.tsgen.strategydsl.RuleGroup
import org.dpolivaev.dsl.tsgen.strategydsl.StrategyReference
import org.dpolivaev.dsl.tsgen.strategydsl.ValueAction
import org.dpolivaev.dsl.tsgen.strategydsl.ValueProvider
import org.dpolivaev.dsl.tsgen.strategydsl.Values
import org.dpolivaev.tsgen.ruleengine.PropertyContainer
import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory
import org.dpolivaev.tsgen.scriptwriter.StrategyRunner
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
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

import static extension org.dpolivaev.dsl.tsgen.jvmmodel.StrategyCompiler.*
import static extension org.eclipse.xtext.nodemodel.util.NodeModelUtils.*

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
		val className = script.eResource.URI.trimFileExtension.lastSegment
		val qualifiedClassName = if(script.package != null) script.package + '.' + className else className
		acceptor.accept(script.toClass(qualifiedClassName)).initializeLater([
			new ScriptInitializer(jvmTypesBuilder, xbaseCompiler, it, script).initializeClass()
		])
	}
}

class Methods{
	val map = new HashMap<String, String>
	
	def put(String prefix, XExpression expression, String name) {
		val text = expression.node.text
		map.put(prefix + text, name)
	}
	
	def contains(String prefix, XExpression expression) {
		val text = expression.node.text
		map.containsKey(prefix + text)
	}
	
	def size() {
		map.size
	}
	
	def get(String prefix, XExpression expression) {
		val text = expression.node.text
		map.get(prefix + text)
	}
	
}

class ScriptInitializer{
	val extension JvmTypesBuilder jvmTypesBuilder
	val XbaseCompiler xbaseCompiler
	val Methods methods
	val Set<String> declaredStrategies
	val JvmGenericType jvmType
	val Generation script
	val JvmType ruleFactoryType

	new(extension JvmTypesBuilder jvmTypesBuilder, XbaseCompiler xbaseCompiler, JvmGenericType jvmType, Generation script){
		this.jvmTypesBuilder = jvmTypesBuilder
		this.xbaseCompiler = xbaseCompiler
		this.methods = new Methods
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
	
	final static val EXTERNAL_STRATEGY = "externalStrategy"
	private def appendStrategyReferences(StrategyReference ref){
		if(ref.expr != null && ! declaredStrategies.contains(ref.expr.toString)) {
			createMethod(ref.expr, EXTERNAL_STRATEGY, ref.expr.newTypeRef(org.dpolivaev.tsgen.ruleengine.Strategy), false)
		}
	}
	
    final static val VALUE_PROVIDER = "valueProvider"
	private def appendValueProviders(ValueAction action){
		for(valueProvider:action.valueProviders)
			for(expr:valueProvider.expressions)
				if(shouldCreateMethodFor(expr)) {
					createMethod(expr, VALUE_PROVIDER, valueProvider.newTypeRef(Object), true)
				}
		
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
					static = true
				]
	}
	
	private def shouldCreateMethodFor(XExpression expr){
		return !(expr instanceof XStringLiteral || expr instanceof XNumberLiteral || expr instanceof XBooleanLiteral || expr instanceof XNullLiteral)
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
				static = true
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
		if(rule.skip){
			apppendSkip(it)
		}
		else{
			appendRuleName(it, rule)
			appendRuleValues(it, rule.values, true)
			appendRuleOrder(it, rule)
		}
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
				val methodName = methods.get(CONDITION, condition.expr)
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
	

	def private appendRuleName(ITreeAppendable it, Rule rule) {
		val name = rule.name.escapeQuotes;
		append('.iterate("')
		append(name)
		append('")')
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
			if(expressions.size == 1 && methods.get(VALUE_PROVIDER, expressions.get(0)) == null)
				xbaseCompiler.compileAsJavaExpression(expressions.get(0), it, valueProvider.newTypeRef(Object))
			else	
				appendImplementationObject(it, valueAction.newTypeRef(org.dpolivaev.tsgen.ruleengine.ValueProvider).type, "Object value", 
					[appendValueExpression(it, valueProvider)])
		}
		append(')')
	}

	def private appendValueExpression(ITreeAppendable it, ValueProvider valueProvider) {
		val concatenation = valueProvider.expressions.size() > 1
		if(concatenation)
			append("new StringBuilder()");
		
		for (expr : valueProvider.expressions){	
			if(concatenation)
				append(".append(");
			val methodName = methods.get(VALUE_PROVIDER, expr)
			if(methodName == null)
				xbaseCompiler.compileAsJavaExpression(expr, it, valueProvider.newTypeRef(Object))
			else{
				append('''«methodName»(propertyContainer)''')
			}
			if(concatenation)
				append(")");
		}
		if(concatenation)
			append(".toString()");
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
			jvmType.members += strategy.toField(strategy.name, strategy.newTypeRef(org.dpolivaev.tsgen.ruleengine.Strategy)) [
				setInitializer [
					append('''«methodName»()''')
				]
				final = true
				visibility = JvmVisibility::PUBLIC
				static = true
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
				static = true
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
		val methodName = methods.get(EXTERNAL_STRATEGY, ref.expr)
		if(methodName != null){
			append(methodName)
			append('()')
		}
		else
			append(ref.expr.toString)
	}
	
	private def inferMainMethod(){
		if(! script.runs.empty){
			val className = jvmType.simpleName
			jvmType.members += script.toMethod("main", script.newTypeRef(Void::TYPE)) [
				parameters += script.toParameter("args", script.newTypeRef(typeof(String)).addArrayTypeDimension)
				body = [
					var counter = 0
					for (run : script.runs) {
						if(counter != 0)
							newLine
						counter = counter + 1
						val methodName = "run" + counter
						append('''«className».«methodName»();''')
						}
				]
				visibility = JvmVisibility::PUBLIC
				static = true
			]
		}
	}
	
}
