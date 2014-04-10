package org.dpolivaev.dsl.tsgen.jvmmodel

import java.util.Collection
import java.util.HashSet
import java.util.Set
import javax.inject.Inject
import org.dpolivaev.dsl.tsgen.strategydsl.Generation
import org.dpolivaev.dsl.tsgen.strategydsl.OracleReference
import org.dpolivaev.dsl.tsgen.strategydsl.OutputConfiguration
import org.dpolivaev.dsl.tsgen.strategydsl.StrategyReference
import org.dpolivaev.tsgen.coverage.CoverageTracker
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy
import org.dpolivaev.tsgen.coverage.TrackingRuleEngine
import org.dpolivaev.tsgen.ruleengine.PropertyContainer
import org.dpolivaev.tsgen.ruleengine.RuleEngine
import org.dpolivaev.tsgen.scriptwriter.WriterFactory
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

class GenerationInferrer{
	@Inject extension JvmTypesBuilder jvmTypesBuilder
	val Methods methods
	val Set<String> declaredFields
	var JvmGenericType jvmType
	var Generation script

	new(){
		this.methods = new Methods
		this.declaredFields = new HashSet<String>
	}
	
	
	def void inferGeneration(JvmGenericType jvmType, Generation script){
		this.script = script
		this.jvmType = jvmType
		inferExpressions()
		inferOracles()
		inferStrategyFields()
		inferStrategyMethods()
		inferRunMethods()
	}
	
	private def inferOracles(){
		for(oracle:script.oracles)
			jvmType.members += oracle.toField(oracle.name, oracle.newTypeRef(StrategyDslJvmModelInferrer.qualifiedClassName(
				script.package, oracle.name.toFirstUpper
			))) [
				setInitializer [
					append('''new «oracle.name.toFirstUpper»()''')
				]
				final = true
				visibility = JvmVisibility::PUBLIC
				static = true
			]
	}
	
	private def inferExpressions(){
		for(strategy : script.strategies)
			declaredFields += strategy.name
		for(oracle : script.oracles)
			declaredFields += oracle.name
		val contents = EcoreUtil2.eAllContents(script)
		for(obj : contents){
			if (obj instanceof StrategyReference)
				appendStrategyReferences(obj as StrategyReference)
			else if (obj instanceof OracleReference) 
				appendOracleReferences(obj as OracleReference)
		}

	}
	
	final static val EXTERNAL_STRATEGY = "externalStrategy"
	private def appendStrategyReferences(StrategyReference ref){
		if(ref.expr != null && ! declaredFields.contains(ref.expr.toString)) {
			createMethod(ref.expr, EXTERNAL_STRATEGY, ref.expr.inferredType, false)
		}
	}
	
	final static val EXTERNAL_ORACLE = "externalOracle"
	private def appendOracleReferences(OracleReference ref){
		if(ref.expr != null && ! declaredFields.contains(ref.expr.toString)) {
			createMethod(ref.expr, EXTERNAL_ORACLE, ref.expr.inferredType, false)
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

	private def inferStrategyMethods(){
		for (strategy : script.strategies) {
			val methodName = strategy.name
			jvmType.members += strategy.toMethod(methodName, strategy.newTypeRef(RequirementBasedStrategy)) [
				for(parameter:strategy.parameters)
					parameters += parameter.toParameter(parameter.name, parameter.parameterType)
				body = [
					val className = StrategyInferrer.strategyClassName(strategy.name)
					append('''return new «className»(''')
					var firstParameter = true
					for(parameter:strategy.parameters){
						if(firstParameter)
							firstParameter = false
						else
							append(', ')
						append(parameter.name)
					}
					append(''').«methodName»();''')
				]
				visibility = JvmVisibility::PUBLIC
				static = true
			]
		}
	}

	private def inferStrategyFields(){
		for (strategy : script.strategies) {
			if(strategy.parameters.empty){
				val methodName = strategy.name
				jvmType.members += strategy.toField(strategy.name, strategy.newTypeRef(RequirementBasedStrategy)) [
					setInitializer [
						append('''«methodName»()''')
					]
					final = true
					visibility = JvmVisibility::PUBLIC
					static = true
				]
			}
		}
	}

	private def inferRunMethods(){
		if(! script.runs.empty)
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
					appendOutputConfiguration(it, "output", run, run.outputConfiguration)
					appendOutputConfiguration(it, "report", run, run.reportConfiguration)
					newLine
					append(run.newTypeRef(CoverageTracker).type)
					append(' __coverageTracker = new ')
					append(run.newTypeRef(CoverageTracker).type)
					append('();')
					
					newLine
					append(run.newTypeRef(WriterFactory).type)
					append(' __writerFactory = new ')
					append(run.newTypeRef(WriterFactory).type)
					append('(__outputConfiguration, __reportConfiguration);')
					newLine
					append('__writerFactory.addCoverageTracker(__coverageTracker);')
					if(! run.strategies.empty && run.strategies.get(0).goal){
						newLine
						appendReference(it, EXTERNAL_STRATEGY, run.strategies.get(0).expr)
						append('.registerRequiredItems(__writerFactory);')
					}
					
					newLine 
					append(run.newTypeRef(RuleEngine).type) append(' __ruleEngine = new ') append(run.newTypeRef(TrackingRuleEngine).type) append('(__coverageTracker);')
					for(oracle:run.oracles){
						newLine
						append('__ruleEngine.addHandler(')
						appendReference(it, EXTERNAL_ORACLE, oracle.expr)
						append(');')
						if(oracle.goal){
							newLine
							appendReference(it, EXTERNAL_ORACLE, oracle.expr)
							append('.setCoverageTracker(__coverageTracker);')
							newLine
							appendReference(it, EXTERNAL_ORACLE, oracle.expr)
							append('.registerRequiredItems(__writerFactory);')
						}
					}
					newLine append('__writerFactory.configureEngine(__ruleEngine);')
					newLine append('new ') append(run.newTypeRef(RequirementBasedStrategy).type) append('()')
					combinedStrategy(it, run.strategies) 
					append('.run(__ruleEngine);')
				]
				visibility = JvmVisibility::PUBLIC
				static = true
			]
		}
	}
	
	def private appendOutputConfiguration(ITreeAppendable it, String target, EObject context, OutputConfiguration outputConfiguration) {
		newLine
		append(context.newTypeRef(org.dpolivaev.tsgen.scriptwriter.OutputConfiguration).type)
		append(''' __«target»Configuration = new ''')
		append(context.newTypeRef(org.dpolivaev.tsgen.scriptwriter.OutputConfiguration).type)
		append('();')
		
		if(outputConfiguration != null){
			newLine
			append('''__«target»Configuration''')
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
	
	private def combinedStrategy(ITreeAppendable it, Collection<StrategyReference> strategies){
		for(strategy : strategies){
			append(".with(")
			appendReference(it, EXTERNAL_STRATEGY, strategy.expr)
			append(")")
		}
		return it.toString
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
