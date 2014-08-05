package org.dpolivaev.testgeneration.dsl.jvmmodel

import com.google.inject.Inject
import com.google.inject.Injector
import java.util.List
import org.dpolivaev.testgeneration.dsl.testspec.Generation
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry
import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import java.util.Arrays
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory
import org.dpolivaev.testgeneration.dsl.testspec.Strategy
import org.dpolivaev.testgeneration.dsl.testspec.TestSpecClass

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
    @Inject ClassInferrer classInferrer
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
		val type = script.toClass(qualifiedClassName)
		acceptor.accept(type).initializeLater([
			injector.getInstance(GenerationInferrer).inferGeneration(it, script)
		])
		for(strategy:script.strategies)
			inferStrategy(acceptor, script.package, type.simpleName, strategy)
		for(oracle:script.classes)
			inferOracle(acceptor, script.package, oracle)
	}
	
	static def qualifiedClassName(String classPackage, String className) {
		if(classPackage != null) classPackage + '.' + className else className
	}
	
	private def inferStrategy(IJvmDeclaredTypeAcceptor acceptor, String classPackage, String factoryName, Strategy strategy) {
		val qualifiedClassName = qualifiedClassName(classPackage, StrategyInferrer.strategyClassName(factoryName, strategy))
		acceptor.accept(strategy.toClass(qualifiedClassName)).initializeLater([
			visibility = JvmVisibility::DEFAULT
			injector.getInstance(StrategyInferrer).inferStrategy(it, strategy)
		])
	}

	private def inferOracle(IJvmDeclaredTypeAcceptor acceptor, String classPackage, TestSpecClass testSpecClass) {
		val qualifiedClassName = qualifiedClassName(classPackage, testSpecClass.name.toFirstUpper)
		val oracleClass =testSpecClass.toClass(qualifiedClassName)
		acceptor.accept(oracleClass).initializeLater([
			if(testSpecClass.isOracle)
				superTypes += testSpecClass.newTypeRef(PropertyHandler)
			if(! (testSpecClass.parameters.empty && testSpecClass.vars.empty))
				classInferrer.inferConstructor(oracleClass, testSpecClass, testSpecClass.parameters, testSpecClass.vars)
			classInferrer.inferMemberVariables(oracleClass, testSpecClass.vars, JvmVisibility::PUBLIC)
			classInferrer.inferMemberMethods(oracleClass, testSpecClass.subs, JvmVisibility::PUBLIC, false)

			if(testSpecClass.isOracle) {
				members += testSpecClass.toField("labels", testSpecClass.newTypeRef(List, testSpecClass.newTypeRef(CoverageEntry)))[
					setInitializer [
						append(Arrays)
						append('.asList(')
						injector.getInstance(CoverageEntriesInferrer).appendArrayInitializer(it, testSpecClass)
						append(')')
					]
					final = true
					visibility = JvmVisibility::PUBLIC
					static = true			
				]
				
				members += testSpecClass.toField("propertyContainer", testSpecClass.newTypeRef(PropertyContainer))
				members += testSpecClass.toField("coverageTracker", testSpecClass.newTypeRef(CoverageTracker))[
					setInitializer [
						append('''null''')
					]
				]
				
				members += testSpecClass.toMethod("setCoverageTracker", testSpecClass.newTypeRef(Void::TYPE)) [
					parameters += testSpecClass.toParameter("coverageTracker", testSpecClass.newTypeRef(CoverageTracker))
					body = [
							trace(testSpecClass).append('this.coverageTracker = coverageTracker;')
					]
					visibility = JvmVisibility::PUBLIC
				]
				
				members += testSpecClass.toMethod("registerRequiredItems", testSpecClass.newTypeRef(Void::TYPE)) [
					parameters += testSpecClass.toParameter("writerFactory", testSpecClass.newTypeRef(WriterFactory))
					body = [
							trace(testSpecClass).append('writerFactory.registerRequiredItems(labels);')
					]
					visibility = JvmVisibility::PUBLIC
				]
				
				members += testSpecClass.toMethod("generationStarted", testSpecClass.newTypeRef(Void::TYPE)) [
					annotations += testSpecClass.toAnnotation(Override)
					parameters += testSpecClass.toParameter("propertyContainer", testSpecClass.newTypeRef(PropertyContainer))
					body = [
							trace(testSpecClass).append('this.propertyContainer=propertyContainer;')
					]
					visibility = JvmVisibility::PUBLIC
				]
				
				members += testSpecClass.toMethod("handlePropertyCombination", testSpecClass.newTypeRef(Void::TYPE)) [
					annotations += testSpecClass.toAnnotation(Override)
					parameters += testSpecClass.toParameter("propertyContainer", testSpecClass.newTypeRef(PropertyContainer))
					body = []
					visibility = JvmVisibility::PUBLIC
				]
				
				members += testSpecClass.toMethod("generationFinished", testSpecClass.newTypeRef(Void::TYPE)) [
					annotations += testSpecClass.toAnnotation(Override)
					body = [
							trace(testSpecClass).append('this.propertyContainer=null;')
					]
					visibility = JvmVisibility::PUBLIC
				]
			
			}
			
		])
	}
}
