package org.dpolivaev.dsl.tsgen.strategydsl.test

import com.google.inject.Inject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.dpolivaev.dsl.tsgen.StrategyDslInjectorProvider
import org.junit.Rule

@RunWith(XtextRunner)
@InjectWith(StrategyDslInjectorProvider)
class StrategyCompilationTest {
	@Rule public val testName = new TestName()

	@Inject extension CompilationTestGoldenMasterHelper
	
	@Test def singlePackageDeclaration() {
		'''
			package packagename
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def singleStrategyDeclaration() {
		'''
			package packagename
			strategy first
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def withOneTopRule() {
		'''
			strategy first
				let "x y" be 1, 2, 3
		'''.assertCompilesToFile(testName)
	}	
	@Test def withConditionForSingleValue() {
		'''
			strategy first
				let x be 'a', 'b'{
					let y be 'A' only if :x=='a',
							'B' ordered
				}
		'''.assertCompilesToFile(testName)
	}

	@Test def withValueProvider() {
		'''
			import org.dpolivaev.tsgen.strategies.StrategyHelper;
			strategy first
				let testcase be StrategyHelper.idProvider
		'''.assertCompilesToFile(testName)
	}


	@Test def withExplicitTriggeredRule() {
		'''
			strategy first
				let "x y" be 1
				for each "x y" let z be 2
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def withMultilineString() {
		'''
			strategy first
				let x be "line1
			line2
			line3
			"
		'''.assertCompilesToFile(testName)
	}

	@Test def withTriggeredRules() {
		'''
			strategy first
				for each x1, x2 let y be 1, 2, 3
		'''.assertCompilesToFile(testName)
	}

	@Test def withCalculation() {
		'''
			strategy first
				let y be :x
		'''.assertCompilesToFile(testName)
	}

	@Test def withCondition() {
		'''
			strategy first
				if 1 < 2 let y be 3
		'''.assertCompilesToFile(testName)
	}
	@Test def withConditionalGroup() {
		'''
			strategy first
				if 1 < 2 {
					let y be 3
				}
		'''.assertCompilesToFile(testName)
	}
	@Test def withConditionalGroupAndCondition() {
		'''
			strategy first
				if 1 < 2 {
					if 3 < 4 let y be 5
				}
			'''.assertCompilesToFile(testName)
	}
	@Test def withOneDefaultRule() {
		'''
			strategy first
				let default x be 1, 2, 3
		'''.assertCompilesToFile(testName)
	}

	@Test def withTriggeringGroupAndTriggerRules() {
		'''
			strategy first
				for each x1 {
					for each x2 let y be 1, 2, 3
				}
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def withOneRuleForValue() {
		'''
			strategy first
				let x be 1 {
					let y be 2
				}
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def withAppliedStrategyForValue() {
		'''
			strategy other
				let y be 2
			strategy first
				let x be 1 {
					apply other
				}
		'''.assertCompilesToFile(testName)
	}

	@Test def withAppliedStrategyAndRulesForValue() {
		'''
			strategy other
				let y be 2
			strategy first
				let x be 1 {
					let z1 be 3
					apply other
					let z2 be 4
				}
		'''.assertCompilesToFile(testName)
	}

	@Test def withTwoRuleForValue() {
		'''
			strategy first
				let x be 1 {
					let y be 2
					let z be 3
				}
		'''.assertCompilesToFile(testName)
	}
		
	@Test def withOneRuleGroupForValue() {
		'''
			strategy first
				let x be 1 {
					for each x {
						let y be 2
					}
				}
		'''.assertCompilesToFile(testName)
	}
	
	@Test def withSharedValues() {
		'''
			strategy First
			let a be listA with 1 {let b be 2}, 3 {}
			let default d be from listA
		'''.assertCompilesToFile(testName)
	}

	@Test def ordered() {
		'''
			strategy first
				let x be 2, 3 ordered
		'''.assertCompilesToFile(testName)
	}

	@Test def shuffled() {
		'''
			strategy first
				let x be 2, 3 shuffled
		'''.assertCompilesToFile(testName)
	}

	@Test def concatenatedValues(){
		'''
			strategy first
				let x be ("a" "b")
		'''.assertCompilesToFile(testName)
	}

	@Test def oneStrategyRun() {
		'''
			strategy First
			run strategy First
		'''.assertCompilesToFile(testName)
	}

	@Test def twoStrategiesRun() {
		'''
			strategy first
			strategy second
			run strategy first with strategy second
		'''.assertCompilesToFile(testName)
	}

	@Test def appliesStrategies() {
		'''
			strategy first
			strategy second apply first
		'''.assertCompilesToFile(testName)
	}


	@Test def extendExternalStrategies() {
		'''
			import org.dpolivaev.tsgen.ruleengine.Strategy
			strategy first apply new Strategy()
		'''.assertCompilesToFile(testName)
	}
	@Test def runWithXslt() {
		'''
			strategy First
			run strategy First output "testoutput/xml", apply "my.xslt" output "testoutput/java"
		'''.assertCompilesToFile(testName)
	}

	@Test def runWithXml() {
		'''
			strategy First
			run strategy First output "testoutput/xml"
		'''.assertCompilesToFile(testName)
	}

	@Test def runWithReport() {
		'''
			strategy First
			run strategy First report "testoutput/xml", apply "my.xslt" output "testoutput/report"
		'''.assertCompilesToFile(testName)
	}

	@Test def coverage() {
		'''
			strategy First
				let [req1] be 123
			
			run strategy goal First 	
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def coverageWithReference() {
		'''
			strategy First
				let default a be 123
				let [req1] be :a
		'''.assertCompilesToFile(testName)
	}

	@Test def withOneParameter() {
		'''
			strategy first(int p)
				let default x be p
		'''.assertCompilesToFile(testName)
	}

	@Test def withParameterizedRuleName() {
		'''
			strategy first(int p)
				let default ("x#" p) be p
		'''.assertCompilesToFile(testName)
	}

	@Test def withConditionallyAppliedStrategy() {
		'''
			strategy other
				let y be 2
			strategy first
				let x be 1
				if (:x == 1) apply other
		'''.assertCompilesToFile(testName)
	}

}