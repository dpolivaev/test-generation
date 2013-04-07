package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ruleengine.TestUtils.ruleStub;
import static ruleengine.TestUtils.set;

import org.junit.Test;

public class ConstantValuesRuleTest {
	private void produceCombination(Rule rule, State state) {
		rule.nextCombination(state);
		rule.finishCombination(state);
	}

	@Test
	public void ruleWithOneValue_hasNotFinishedUntilCombinationIsFinished() {
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
	public void ruleWithOneValue_hasFinishedAfterCombinationIsFinished() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"value");
		OnePropertyStateStub stateStub = stateStub();
		produceCombination(constantValuesRule, stateStub);
		assertThat(constantValuesRule.hasFinished(), is(true));
	}

	private OnePropertyStateStub stateStub() {
		return new OnePropertyStateStub();
	}

	@Test
	public void ruleWithTwoValues_hasNotFinishedAfterFirstCombinationIsFinished() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"1", "2");
		produceCombination(constantValuesRule, stateStub());
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithTwoValues_assignsSecondValueOnSecondTime() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"1", "2");
		OnePropertyStateStub stateStub = stateStub();
		produceCombination(constantValuesRule, stateStub);
		constantValuesRule.nextCombination(stateStub);
		assertThat(stateStub.getValue(), is((Object) "2"));
	}

	@Test
	public void ruleWithOneValue_assignsItsValueOnSecondTime() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"value");
		OnePropertyStateStub stateStub = stateStub();
		produceCombination(constantValuesRule, stateStub);
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

	@Test
	public void secondCallOfFinishCombinationIsIgnored() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"1", "2");
		OnePropertyStateStub stateStub = stateStub();
		constantValuesRule.nextCombination(stateStub);
		constantValuesRule.finishCombination(stateStub);
		constantValuesRule.finishCombination(stateStub);
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
