package org.dpolivaev.testgeneration.dsl.test

import com.google.inject.Inject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.dpolivaev.testgeneration.dsl.TestSpecInjectorProvider
import org.junit.Rule

@RunWith(XtextRunner)
@InjectWith(TestSpecInjectorProvider)
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

	@Test def withListEndingWithComma() {
		'''
			strategy first
				let x be 1, 2, 3,
		'''.assertCompilesToFile(testName)
	}
		
	@Test def withSkip() {
		'''
			strategy first
				skip 
		'''.assertCompilesToFile(testName)
	}	

	@Test def withDisable() {
		'''
			strategy first
				disable rule x 
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def namedRule() {
		'''
			strategy first
				rule y let y be 'A', 'B'
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def namedConditionalRule() {
		'''
			strategy first
				rule y if :x == 'c' let y be 'A', 'B'
		'''.assertCompilesToFile(testName)
	}	
	

	@Test def withConditionForSingleValue() {
		'''
			strategy first
				let x be 'a', 'b'{
					let ordered y be 'A' only if :x=='a', 'B'
				}
		'''.assertCompilesToFile(testName)
	}

	@Test def withConditionForSingleValueInsideNotOrderedRule() {
		'''
			strategy first
				let x be 'a', 'b'{
					let y be 'A' only if :x=='a', 'B'
				}
		'''.assertCompilesToFile(testName)
	}

	@Test def withConditionForSingleValueInsideShuffledRule() {
		'''
			strategy first
				let x be 'a', 'b'{
					let shuffled y be 'A' only if :x=='a', 'B'
				}
		'''.assertCompilesToFile(testName)
	}

	@Test def withConditionForSingleValueAtLastPosition() {
		'''
			strategy first
				let x be 'a', 'b'{
					let ordered y be 'A', 'B'  only if :x=='a'
				}
		'''.assertCompilesToFile(testName)
	}

	@Test def withValueProvider() {
		'''
			import org.dpolivaev.testgeneration.engine.strategies.StrategyHelper;
			strategy first
				let testcase be StrategyHelper.testcaseName
		'''.assertCompilesToFile(testName)
	}


	@Test def withExplicitTriggeredRule() {
		'''
			strategy first
				let "x y" be 1
				for each "x y" let z be 2
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def withExplicitTriggeredRuleAndExpressionInForEach() {
		'''
			strategy first
				let "x y" be 1
				for each ("x" " " "y") let z be 2
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def withBigInteger() {
		'''
			strategy first
				let x be 0x1#bi
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def withBuildTriggers() {
		'''
			strategy first
				let "x y" be 1
				for each ("x y") let z be 2
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

	@Test def withConditionalGroupInPackage() {
		'''
			package somepackage
			strategy first
				if 1 < 2 {
					let y be 3
				}
		'''.assertCompilesToFile(testName)
	}

	@Test def withTracedConditionalGroup() {
		'''
			strategy first
				if traced 1 < 2 {
					let y be 3
				}
		'''.assertCompilesToFile(testName)
	}

	@Test def withConditionInsideInnerGroup() {
		'''
			strategy first
				let x be 1 {
					if 1 < 2 {
						let y be 3
					}
				}
		'''.assertCompilesToFile(testName)
	}

	@Test def withConditionInsideInnerGroupInCondition() {
		'''
			strategy first
				if 1 < 2  let x be 1 {
					if 2 < 3 {
						let y be 3
					}
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

	@Test def withConditionalGroupAndTrigger() {
		'''
			strategy first
				if 1 < 2 {
					for each x let y be 5
				}
			'''.assertCompilesToFile(testName)
	}

	@Test def withOneDefaultRule() {
		'''
			strategy first
				let lazy x be 1, 2, 3
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

	@Test def withStrategyAppliedTwice() {
		'''
			strategy other
			strategy first
				apply other
				apply other
			run strategy goal first
		'''.assertCompilesToFile(testName)
	}

	@Test def withAppliedStrategyAndConditions() {
		'''
			strategy other
				let y be 2
			strategy first
				for each z if (:z as int) > 0 {
					for each z2 if (:z2 as int) <0 {
						let x be 1 
						apply other
						let z2 be 4
					}
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
			let lazy d be from listA
		'''.assertCompilesToFile(testName)
	}

	@Test def ordered() {
		'''
			strategy first
				let ordered x be 2, 3
		'''.assertCompilesToFile(testName)
	}

	@Test def shuffled() {
		'''
			strategy first
				let shuffled x be 2, 3
		'''.assertCompilesToFile(testName)
	}

	@Test def strategyWithVal() {
		'''
			strategy first
				val x = 1
		'''.assertCompilesToFile(testName)
	}

	@Test def strategyWithVar() {
		'''
			strategy first
				var x = 1
		'''.assertCompilesToFile(testName)
	}

	@Test def strategyWithUnassignedVar() {
		'''
			strategy first
				var boolean x
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

	@Test def twoRunsWithValues() {
		'''
			strategy first(String string)
			run
			val string = "string" 
			strategy first(string)
			
			run
			strategy first(string)
		'''.assertCompilesToFile(testName)
	}

	@Test def twoRunsWithSubs() {
		'''
			strategy first(String string)
			run
			def string() {"string"} 
			strategy first(string)
			
			run
			strategy first(string)
		'''.assertCompilesToFile(testName)
	}

	@Test def appliesStrategies() {
		'''
			strategy first
			strategy second apply first
		'''.assertCompilesToFile(testName)
	}

	@Test def appliesStrategiesMixedWithRules() {
		'''
			strategy first
			strategy second
				let x be 1 
				apply first
				let y be 2 
		'''.assertCompilesToFile(testName)
	}

	@Test def appliesStrategiesMixedWithRulesForValue() {
		'''
			strategy first
			strategy second
				let x be 1 {
					let y be 2 
					apply first
					let y be 2 
				}
		'''.assertCompilesToFile(testName)
	}


	@Test def extendExternalStrategies() {
		'''
			import org.dpolivaev.testgeneration.engine.ruleengine.Strategy
			strategy first apply new Strategy()
		'''.assertCompilesToFile(testName)
	}
	
	@Test def runWithXslt() {
		'''
			strategy First
			run strategy First output "testoutput/xml", apply "my.xslt" output "testoutput/java"
		'''.assertCompilesToFile(testName)
	}

	@Test def runWithXsltParemeters() {
		'''
			strategy First
			
			run
			val value = 1 
			strategy First output "testoutput/xml", apply "my.xslt"(key=value) output "testoutput/java"
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
	
	@Test def quotedRequirementId() {
		'''
			strategy First
				let "[req1]" be 123
			
			run strategy goal First 	
		'''.assertCompilesToFile(testName)
	}	
	
	@Test def coverageWithReference() {
		'''
			strategy First
				let lazy a be 123
				let [req1] be :a
		'''.assertCompilesToFile(testName)
	}

	@Test def withOneParameter() {
		'''
			strategy first(int p)
				let lazy x be p
		'''.assertCompilesToFile(testName)
	}

	@Test def withOverloadedParameter() {
		'''
			strategy first
				apply first(1)
			strategy first(int p)
				let lazy x be p
		'''.assertCompilesToFile(testName)
	}

	@Test def withParameterizedRuleName() {
		'''
			strategy first(int p)
				let lazy ("x#" p) be p
		'''.assertCompilesToFile(testName)
	}

	@Test def withParameterizedRuleValue() {
		'''
			strategy first(boolean p)
				let lazy x be if (p) 1 else 0
		'''.assertCompilesToFile(testName)
	}

	@Test def withParameterizedRuleNameReference() {
		'''
			strategy first(int p)
				let lazy y be :("x#"  p)
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

	@Test def withMethodsAndValues() {
		'''
			strategy first
				def a() {return 1}
				val b = 2
				let x be a + b
		'''.assertCompilesToFile(testName)
	}

	@Test def propertyCallTypeCast() {
		'''
			strategy first
				let x be 1
				if (:("x") as int > 0) let y be 2
		'''.assertCompilesToFile(testName)
	}

	@Test def nameBasedOnPropertyValue() {
		'''
			strategy first
				let x be "x"
				let (:x) be "y"
		'''.assertCompilesToFile(testName)
	}

	@Test def withGlobalVals() {
		'''
			global val value1 = 1 
			strategy first
				let x be value1
			global val value2 = value1
		'''.assertCompilesToFile(testName)
	}	

	@Test def withGlobalSubs() {
		'''
			global def sub1(int i) {i} 
			strategy first
				let x be sub1(1)
		'''.assertCompilesToFile(testName)
	}	
}