package org.dpolivaev.testgeneration.dsl.jvmmodel

import com.google.inject.Inject
import com.google.inject.Injector
import java.util.List
import org.dpolivaev.testgeneration.dsl.testspec.Generation
import org.dpolivaev.testgeneration.dsl.testspec.Oracle
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry
import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import java.util.Arrays
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory
import org.dpolivaev.testgeneration.dsl.testspec.Strategy

/**
 * <p>Infers a JVM model from the source model.</p> 
 *
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>     
 */
class TestSpecJvmModelInferrer extends AbstractModelInferrer {
	/**
     * convenience API to build and initialize JVM types and their members.
     */
     
    @Inject Injector injector 
    @Inject ConstructorInferrer constructorInferrer
	@Inject extension JvmTypesBuilder jvmTypesBuilder

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
		val qualifiedClassName = qualifiedClassName(script.package, className)
		acceptor.accept(script.toClass(qualifiedClassName)).initializeLater([
			injector.getInstance(GenerationInferrer).inferGeneration(it, script)
		])
		for(strategy:script.strategies)
			inferStrategy(acceptor, script.package, strategy)
		for(oracle:script.oracles)
			inferOracle(acceptor, script.package, oracle)
	}
	
	static def qualifiedClassName(String classPackage, String className) {
		if(classPackage != null) classPackage + '.' + className else className
	}
	
	private def inferStrategy(IJvmDeclaredTypeAcceptor acceptor, String classPackage, Strategy strategy) {
		val qualifiedClassName = qualifiedClassName(classPackage, StrategyInferrer.strategyClassName(strategy.name))
		acceptor.accept(strategy.toClass(qualifiedClassName)).initializeLater([
			visibility = JvmVisibility::DEFAULT
			injector.getInstance(StrategyInferrer).inferStrategy(it, strategy)
		])
	}

	private def inferOracle(IJvmDeclaredTypeAcceptor acceptor, String classPackage, Oracle oracle) {
		val qualifiedClassName = qualifiedClassName(classPackage, oracle.name.toFirstUpper)
		val oracleClass =oracle.toClass(qualifiedClassName)
		acceptor.accept(oracleClass).initializeLater([
			superTypes += oracle.newTypeRef(PropertyHandler)
			if(!oracle.parameters.empty)
				constructorInferrer.inferConstructor(oracleClass, oracle, oracle.parameters)
			members += oracle.toField("labels", oracle.newTypeRef(List, oracle.newTypeRef(CoverageEntry)))[
				setInitializer [
					append(oracle.newTypeRef(Arrays).type)
					append('.asList(')
					injector.getInstance(CoverageEntriesInferrer).appendArrayInitializer(it, oracle)
					append(')')
				]
				final = true
				visibility = JvmVisibility::PUBLIC
				static = true			
			]
			
			members += oracle.toField("propertyContainer", oracle.newTypeRef(PropertyContainer))
			members += oracle.toField("coverageTracker", oracle.newTypeRef(CoverageTracker))[
				setInitializer [
					append('''null''')
				]
			]
			
			members += oracle.toMethod("setCoverageTracker", oracle.newTypeRef(Void::TYPE)) [
				parameters += oracle.toParameter("coverageTracker", oracle.newTypeRef(CoverageTracker))
				body = [
						trace(oracle).append('this.coverageTracker = coverageTracker;')
				]
				visibility = JvmVisibility::PUBLIC
			]
			
			members += oracle.toMethod("registerRequiredItems", oracle.newTypeRef(Void::TYPE)) [
				parameters += oracle.toParameter("writerFactory", oracle.newTypeRef(WriterFactory))
				body = [
						trace(oracle).append('writerFactory.registerRequiredItems(labels);')
				]
				visibility = JvmVisibility::PUBLIC
			]
			
			members += oracle.toMethod("generationStarted", oracle.newTypeRef(Void::TYPE)) [
				annotations += oracle.toAnnotation(Override)
				parameters += oracle.toParameter("propertyContainer", oracle.newTypeRef(PropertyContainer))
				body = [
						trace(oracle).append('this.propertyContainer=propertyContainer;')
				]
				visibility = JvmVisibility::PUBLIC
			]
			
			members += oracle.toMethod("handlePropertyCombination", oracle.newTypeRef(Void::TYPE)) [
				annotations += oracle.toAnnotation(Override)
				parameters += oracle.toParameter("propertyContainer", oracle.newTypeRef(PropertyContainer))
				body = []
				visibility = JvmVisibility::PUBLIC
			]
			
			members += oracle.toMethod("generationFinished", oracle.newTypeRef(Void::TYPE)) [
				annotations += oracle.toAnnotation(Override)
				body = [
						trace(oracle).append('this.propertyContainer=null;')
				]
				visibility = JvmVisibility::PUBLIC
			]
			for(expr:oracle.vars){
				val declaration = expr as XVariableDeclaration
				members += oracle.toField(declaration.name, declaration.type)[
					setInitializer(declaration.right)
					final = ! declaration.writeable
					visibility = JvmVisibility::PUBLIC
				]
			}
			
			for(method:oracle.subs){
				members += oracle.toMethod(method.name, method.returnType)[
					for(parameter:method.parameters)
						parameters += parameter.toParameter(parameter.name, parameter.parameterType)
					body = method.body
				]
			}
			
		])
	}
}
