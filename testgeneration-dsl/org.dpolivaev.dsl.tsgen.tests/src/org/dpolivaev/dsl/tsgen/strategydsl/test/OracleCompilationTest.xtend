package org.dpolivaev.dsl.tsgen.strategydsl.test

import com.google.inject.Inject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.dpolivaev.dsl.tsgen.StrategyDslInjectorProvider
import org.junit.Rule
import org.junit.rules.TestName

@RunWith(XtextRunner)
@InjectWith(StrategyDslInjectorProvider)
class OracleCompilationTest {
	@Rule public val testName = new TestName
	@Inject extension CompilationTestGoldenMasterHelper
	
	@Test def minimalOracle() {
		'''
			oracle myOracle
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def parameterizedOracle() {
		'''
			oracle myOracle(String x, boolean b)
		'''.assertCompilesToFile(testName)
	}	
	

	@Test def oracleWithLabels() {
		'''
			oracle myOracle
				def calculate(int i){
					["req1" "reason1"] 
					val x = ["req2"] 2
					x
				}
		'''.assertCompilesToFile(testName)
	}	

	@Test def oracleWithFields() {
		'''
			oracle myOracle
				val x = 0
				var y = 2
		'''.assertCompilesToFile(testName)
	}

	@Test def registeredOracle() {
		'''
			oracle myOracle
				def calculate(int i){0}

			strategy s
				let x be traced myOracle.calculate(0)

			run strategy s with oracle goal myOracle
		'''.assertCompilesToFile(testName)
	}

	@Test def registeredOracleInCondition() {
		'''
			oracle myOracle
				def calculate(){true}

			strategy s
				if traced myOracle.calculate() let x be 1

			run strategy s with oracle goal myOracle
		'''.assertCompilesToFile(testName)
	}

	@Test def referencingOracles() {
		'''
			oracle myOracle1
				def calculate(){1}


			oracle myOracle2
				def calculate(){myOracle1.calculate()}

		'''.assertCompilesToFile(testName)
	}

}