package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ruleengine.TestUtils.ruleStub;
import static ruleengine.TestUtils.set;

import org.junit.Test;

public class StatefulRuleTest {
	private void produceCombination(Rule rule, State state) {
		rule.combinationStarted(state);
		rule.combinationFinished(state);
	}

	private OnePropertyStateStub stateStub() {
		return new OnePropertyStateStub();
	}

	private OnePropertyStateStub stateStub(String name, String value) {
		OnePropertyStateStub stateStub = stateStub();
		stateStub.setPropertyValue(ruleStub(name), value);
		return stateStub;
	}

	@Test
	public void ruleWithOneValue_hasNotFinishedUntilCombinationIsFinished() {
		StatefulRule statefulRule = new StatefulRule("name", "value");
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithOneValue_assignsItsValue() {
		StatefulRule statefulRule = new StatefulRule("name", "value");
		OnePropertyStateStub stateStub = stateStub();
		statefulRule.combinationStarted(stateStub);
		assertThat(stateStub.getValue(), is((Object) "value"));
	}

	@Test
	public void ruleWithOneValue_hasFinishedAfterCombinationIsFinished() {
		StatefulRule statefulRule = new StatefulRule("name", "value");
		OnePropertyStateStub stateStub = stateStub();
		produceCombination(statefulRule, stateStub);
		assertThat(statefulRule.hasFinished(), is(true));
	}

	@Test
	public void ruleWithTwoValues_hasNotFinishedAfterFirstCombinationIsFinished() {
		StatefulRule statefulRule = new StatefulRule("name", "1", "2");
		produceCombination(statefulRule, stateStub());
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithTwoValues_assignsSecondValueOnSecondTime() {
		StatefulRule statefulRule = new StatefulRule("name", "1", "2");
		OnePropertyStateStub stateStub = stateStub();
		produceCombination(statefulRule, stateStub);
		statefulRule.combinationStarted(stateStub);
		assertThat(stateStub.getValue(), is((Object) "2"));
	}

	@Test
	public void ruleWithOneValue_assignsItsValueOnSecondTime() {
		StatefulRule statefulRule = new StatefulRule("name", "value");
		OnePropertyStateStub stateStub = stateStub();
		produceCombination(statefulRule, stateStub);
		statefulRule.combinationStarted(stateStub);
		assertThat(stateStub.getValue(), is((Object) "value"));
	}

	@Test
	public void triggeredRuleWithOneValue_ignoresCombinationWithoutItsTriggeringProperty() {
		StatefulRule statefulRule = new StatefulRule(set("triggeredBy"),
				"name", "value");
		OnePropertyStateStub stateStub = stateStub();
		produceCombination(statefulRule, stateStub);
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_assignsItsValue() {
		StatefulRule statefulRule = new StatefulRule(set("triggeredBy"),
				"name", "value");
		OnePropertyStateStub stateStub = stateStub("triggeredBy",
				"triggeredByValue");
		statefulRule.propertyValueSet(stateStub.event());
		assertThat(stateStub.getValue(), is((Object) "value"));
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_finishes() {
		StatefulRule statefulRule = new StatefulRule(set("triggeredBy"),
				"name", "value");
		OnePropertyStateStub stateStub = stateStub("triggeredBy",
				"triggeredByValue");
		statefulRule.propertyValueSet(stateStub.event());
		statefulRule.combinationFinished(stateStub);
		assertThat(statefulRule.hasFinished(), is(true));
	}

	@Test
	public void ruleIgnoresAssignmentsToNonTriggeringProperties() {
		StatefulRule statefulRule = new StatefulRule(set(), "name", "value");
		OnePropertyStateStub stateStub = stateStub("triggeredBy",
				"triggeredByValue");
		statefulRule.propertyValueSet(stateStub.event());
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void secondCallOfFinishCombinationIsIgnored() {
		StatefulRule statefulRule = new StatefulRule("name", "1", "2");
		OnePropertyStateStub stateStub = stateStub();
		statefulRule.combinationStarted(stateStub);
		statefulRule.combinationFinished(stateStub);
		statefulRule.combinationFinished(stateStub);
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void givenTriggeredRuleIsNotFinished_ruleIsNotFinished() {
		StatefulRule topRule = new StatefulRule("x", "1");
		StatefulRule triggeredRule = new StatefulRule(set("x"), "y", "3", "4");
		MapBasedState state = new MapBasedState();
		topRule.combinationStarted(state);
		triggeredRule.combinationStarted(state);
		topRule.propertyValueSet(new PropertyAssignedEvent(state,
				triggeredRule, triggeredRule.getTriggeringProperties()));
		topRule.combinationFinished(state);
		assertThat(topRule.hasFinished(), is(false));

	}
}
