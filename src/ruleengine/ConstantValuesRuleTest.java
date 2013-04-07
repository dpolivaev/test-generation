package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ruleengine.TestUtils.performCombination;
import static ruleengine.TestUtils.ruleStub;
import static ruleengine.TestUtils.set;

import org.junit.Test;

public class ConstantValuesRuleTest {

	@Test
	public void ruleWithOneValue_hasNotFinishedUntilIterationIsFinished() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"value");
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithOneValue_assignsItsValue() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"value");
		OnePropertyStateStub stateStub = stateStub();
		constantValuesRule.nextCombination(stateStub);
		assertThat(stateStub.getValue(), is((Object) "value"));
	}

	@Test
	public void ruleWithOneValue_hasFinishedAfterIterationIsFinished() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"value");
		OnePropertyStateStub stateStub = stateStub();
		performCombination(constantValuesRule, stateStub);
		assertThat(constantValuesRule.hasFinished(), is(true));
	}

	private OnePropertyStateStub stateStub() {
		return new OnePropertyStateStub();
	}

	@Test
	public void ruleWithTwoValues_hasNotFinishedAfterFirstIterationIsFinished() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"1", "2");
		constantValuesRule.nextCombination(stateStub());
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithTwoValues_assignsSecondValueOnSecondTime() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"1", "2");
		OnePropertyStateStub stateStub = stateStub();
		performCombination(constantValuesRule, stateStub);
		constantValuesRule.nextCombination(stateStub);
		assertThat(stateStub.getValue(), is((Object) "2"));
	}

	@Test
	public void ruleWithOneValue_assignsItsValueOnSecondTime() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"value");
		OnePropertyStateStub stateStub = stateStub();
		performCombination(constantValuesRule, stateStub);
		constantValuesRule.nextCombination(stateStub);
		assertThat(stateStub.getValue(), is((Object) "value"));
	}

	@Test
	public void triggeredRuleWithOneValue_ignoresCombinationBegin() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule(
				set("triggeredBy"), "name", "value");
		OnePropertyStateStub stateStub = stateStub();
		constantValuesRule.nextCombination(stateStub);
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_assignsItsValue() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule(
				set("triggeredBy"), "name", "value");
		OnePropertyStateStub stateStub = stateStub("triggeredBy",
				"triggeredByValue");
		constantValuesRule.propertyValueSet(stateStub.event());
		assertThat(stateStub.getValue(), is((Object) "value"));
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_finishes() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule(
				set("triggeredBy"), "name", "value");
		OnePropertyStateStub stateStub = stateStub("triggeredBy",
				"triggeredByValue");
		constantValuesRule.propertyValueSet(stateStub.event());
		constantValuesRule.finishCombination(stateStub);
		assertThat(constantValuesRule.hasFinished(), is(true));
	}

	private OnePropertyStateStub stateStub(String name, String value) {
		OnePropertyStateStub stateStub = stateStub();
		stateStub.setPropertyValue(ruleStub(name), value);
		return stateStub;
	}

	@Test
	public void ruleIgnoresAssignmentsToNonTriggeringProperties() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule(set(),
				"name", "value");
		OnePropertyStateStub stateStub = stateStub("triggeredBy",
				"triggeredByValue");
		constantValuesRule.propertyValueSet(stateStub.event());
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	// @Test
	// public void ruleWithOneValueIsNotFinished_untilTriggeredRuleIsFinished()
	// {
	// ConstantValuesRule rule = new ConstantValuesRule("x", "a");
	// ConstantValuesRule triggeredRule = new ConstantValuesRule(set("x"),
	// "y", "a", "b");
	// State state = new MapBasedState();
	// rule.nextCombination(state);
	//
	// triggeredRule.propertyValueSet(new PropertyAssignedEvent(state, "x"));
	//
	// assertThat(rule.hasFinished(), is(false));
	// }
}
