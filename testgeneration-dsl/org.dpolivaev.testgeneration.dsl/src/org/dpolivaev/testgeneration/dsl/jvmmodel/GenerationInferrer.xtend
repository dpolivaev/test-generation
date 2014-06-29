package org.dpolivaev.testgeneration.dsl.jvmmodel

import java.util.Collection
import javax.inject.Inject
import org.dpolivaev.testgeneration.dsl.testspec.Generation
import org.dpolivaev.testgeneration.dsl.testspec.OracleReference
import org.dpolivaev.testgeneration.dsl.testspec.OutputConfiguration
import org.dpolivaev.testgeneration.dsl.testspec.StrategyReference
import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy
import org.dpolivaev.testgeneration.engine.coverage.TrackingRuleEngine
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer
import org.dpolivaev.testgeneration.engine.ruleengine.RuleEngine
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import java.util.ArrayList
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.dpolivaev.testgeneration.dsl.testspec.Run
import org.dpolivaev.testgeneration.dsl.testspec.MethodDefinition

class GenerationInferrer{
	@Inject extension JvmTypesBuilder jvmTypesBuilder
	@Inject ClassInferrer classInferrer
	val Methods methods
	var JvmGenericType jvmType
	var Generation script

	new(){
		this.methods = new Methods
	}
	
	
	def void inferGeneration(JvmGenericType jvmType, Generation script){
		this.script = script
		this.jvmType = jvmType
		inferRunVariables()
		inferExpressions()
		inferOracles()
		inferStrategyMethods()
		inferRunMethods()
	}
	
	private def inferRunVariables(){
		val vars = new ArrayList<XExpression>()
		val subs = new ArrayList<MethodDefinition>()
		val scriptContents = EcoreUtil2.eAllContents(script)
		for(obj : scriptContents){
			if (obj instanceof Run){
				vars.addAll(obj.vars)
				subs.addAll(obj.subs)
			}
		}
		if(! (vars.empty) ){
			classInferrer.inferConstructor(jvmType, script, emptyList, vars)
			classInferrer.inferMemberVariables(jvmType, script, vars, JvmVisibility::PRIVATE)
		}
		classInferrer.inferMemberMethods(jvmType, script, subs)
	}
	
	private def inferOracles(){
		for(oracle:script.oracles)
			jvmType.members += oracle.toField(oracle.name, oracle.newTypeRef(TestSpecJvmModelInferrer.qualifiedClassName(
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
		val scriptContents = EcoreUtil2.eAllContents(script)
		for(obj : scriptContents){
			if (obj instanceof OracleReference)
				appendOracleReferences(obj as OracleReference)
		}
		for(run : script.runs){
			val runContents = EcoreUtil2.eAllContents(run)
			for(obj : runContents){
				if (obj instanceof StrategyReference)
					appendStrategyReferences(obj as StrategyReference)
			}
		}
	}

	final static val STRATEGY = "_strategy"
	private def appendStrategyReferences(StrategyReference ref){
		if(ref.expr != null) {
			createMethod(ref.expr, STRATEGY, ref.expr.inferredType, false)
		}
	}

	final static val ORACLE = "_oracle"
	private def appendOracleReferences(OracleReference ref){
		if(ref.expr != null) {
			createMethod(ref.expr, ORACLE, ref.expr.inferredType, false)
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
					static = ! (expr?.eContainer?.eContainer instanceof Run)
				]
	}

	private def inferStrategyMethods(){
		for (strategy : script.strategies) {
			val methodName = strategy.name
			jvmType.members += strategy.toMethod(methodName, strategy.newTypeRef(RequirementBasedStrategy)) [
				for(parameter:strategy.parameters)
					parameters += parameter.toParameter(parameter.name, parameter.parameterType)
				body = [appendable |
					val it = appendable.trace(strategy)
					val className = StrategyInferrer.strategyClassName(jvmType.simpleName, strategy.name)
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
				body = [appendable |
					val it = appendable.trace(run)
					appendOutputConfiguration(it, "output", run, run.outputConfiguration)
					appendOutputConfiguration(it, "report", run, run.reportConfiguration)
					newLine
					append(run.newTypeRef(CoverageTracker).type)
					append(' _coverageTracker = new ')
					append(run.newTypeRef(CoverageTracker).type)
					append('();')
					
					newLine
					append(run.newTypeRef(WriterFactory).type)
					append(' _writerFactory = new ')
					append(run.newTypeRef(WriterFactory).type)
					append('(_outputConfiguration, _reportConfiguration);')
					newLine
					append('_writerFactory.addCoverageTracker(_coverageTracker);')
					if(! run.strategies.empty && run.strategies.get(0).goal){
						newLine
						appendReference(it, STRATEGY, run.strategies.get(0).expr)
						append('.registerRequiredItems(_writerFactory);')
					}
					
					newLine 
					append(run.newTypeRef(RuleEngine).type) append(' _ruleEngine = new ') append(run.newTypeRef(TrackingRuleEngine).type) append('(_coverageTracker);')
					for(oracle:run.oracles){
						newLine
						append('_ruleEngine.addHandler(')
						appendReference(it, ORACLE, oracle.expr)
						append(');')
						if(oracle.goal){
							newLine
							appendReference(it, ORACLE, oracle.expr)
							append('.setCoverageTracker(_coverageTracker);')
							newLine
							appendReference(it, ORACLE, oracle.expr)
							append('.registerRequiredItems(_writerFactory);')
						}
					}
					newLine append('_writerFactory.configureEngine(_ruleEngine);')
					newLine append('new ') append(run.newTypeRef(RequirementBasedStrategy).type) append('()')
					combinedStrategy(it, run.strategies) 
					append('.run(_ruleEngine);')
				]
				visibility = JvmVisibility::DEFAULT
			]
		}
	}
	
	def private appendOutputConfiguration(ITreeAppendable it, String target, EObject context, OutputConfiguration outputConfiguration) {
		newLine
		append(context.newTypeRef(org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration).type)
		append(''' _«target»Configuration = new ''')
		append(context.newTypeRef(org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration).type)
		append('();')
		
		if(outputConfiguration != null){
			newLine
			append('''_«target»Configuration''')
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
			for(xsltParameter : outputConfiguration.xsltParamerers){
				newLine
				append('''_«target»Configuration.putXsltParameter("«xsltParameter.key»", "«xsltParameter.value»");''')
			}
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
			appendReference(it, STRATEGY, strategy.expr)
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
				body = [appendable |
						val it = appendable.trace(script, false)
						append('''new «className»().runAll();''')
				]
				visibility = JvmVisibility::PUBLIC
				static = true
			]
			jvmType.members += script.toMethod("runAll", script.newTypeRef(Void::TYPE)) [
				body = [appendable |
					var counter = 0
					for (run : script.runs) {
						val it = appendable.trace(run, true)
						if(counter != 0)
							newLine
						counter = counter + 1
						val methodName = "run" + counter
						append('''«methodName»();''')
						}
				]
				visibility = JvmVisibility::DEFAULT
			]
		}
	}
	
}
