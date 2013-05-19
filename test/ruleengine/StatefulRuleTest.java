package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ruleengine.StatefulRuleBuilder.Factory.*;

import org.junit.Before;
import org.junit.Test;

public class StatefulRuleTest {
    private OnePropertyStateStub stateStub;

    private void produceCombination(Rule rule, State state) {
		rule.combinationStarted(state);
		rule.combinationFinished(state);
	}

    private OnePropertyStateStub stateStub(String name, String value) {

		stateStub.setPropertyValue(iterate(name).asRule(), value);
		return stateStub;
	}

    @Before
    public void setup() {
        stateStub = OnePropertyStateStub.stateStub();
    }

	@Test
	public void ruleWithOneValue_hasNotFinishedUntilCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
		assertThat(statefulRule.hasFinished(), is(false));
	}

    @Test
    public void ruleWithOneValue_assignsItsValue() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        statefulRule.combinationStarted(stateStub);
        assertThat(stateStub.getValue(), is((Object) "value"));
    }

    @Test
    public void ruleWithAssignedValue_isActive() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        statefulRule.combinationStarted(stateStub);
        assertThat(statefulRule.isActive(), is(true));
    }

    @Test
    public void ruleIsNotActive_afterItHasFinished() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        statefulRule.combinationStarted(stateStub);
        statefulRule.combinationFinished(stateStub);
        assertThat(statefulRule.isActive(), is(false));
    }

	@Test
	public void ruleWithOneValue_hasFinishedAfterCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
		produceCombination(statefulRule, stateStub);
		assertThat(statefulRule.hasFinished(), is(true));
	}

	@Test
	public void ruleWithTwoValues_hasNotFinishedAfterFirstCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("1", "2").asRule();
        produceCombination(statefulRule, stateStub);
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithTwoValues_assignsSecondValueOnSecondTime() {
        StatefulRule statefulRule = iterate("name").over("1", "2").asRule();
		produceCombination(statefulRule, stateStub);
		statefulRule.combinationStarted(stateStub);
		assertThat(stateStub.getValue(), is((Object) "2"));
	}

	@Test
	public void ruleWithOneValue_assignsItsValueOnSecondTime() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
		produceCombination(statefulRule, stateStub);
		statefulRule.combinationStarted(stateStub);
		assertThat(stateStub.getValue(), is((Object) "value"));
	}

	@Test
	public void triggeredRuleWithOneValue_ignoresCombinationWithoutItsTriggeringProperty() {
        StatefulRule statefulRule = when("triggeredBy").iterate("name")
            .over("value").asRule();
		produceCombination(statefulRule, stateStub);
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_assignsItsValue() {
        StatefulRule statefulRule = when("triggeredBy").iterate("name")
            .over("value").asRule();
		OnePropertyStateStub stateStub = stateStub("triggeredBy",
				"triggeredByValue");
		statefulRule.propertyValueSet(stateStub.event());
		assertThat(stateStub.getValue(), is((Object) "value"));
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_finishes() {
        StatefulRule statefulRule = when("triggeredBy").iterate("name")
            .over("value").asRule();
		OnePropertyStateStub stateStub = stateStub("triggeredBy",
				"triggeredByValue");
		statefulRule.propertyValueSet(stateStub.event());
		statefulRule.combinationFinished(stateStub);
		assertThat(statefulRule.hasFinished(), is(true));
	}

	@Test
	public void ruleIgnoresAssignmentsToNonTriggeringProperties() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
		OnePropertyStateStub stateStub = stateStub("triggeredBy",
				"triggeredByValue");
		statefulRule.propertyValueSet(stateStub.event());
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void secondCallOfFinishCombinationIsIgnored() {
        StatefulRule statefulRule = iterate("name").over("1", "2").asRule();
		statefulRule.combinationStarted(stateStub);
		statefulRule.combinationFinished(stateStub);
		statefulRule.combinationFinished(stateStub);
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void givenTriggeredRuleIsNotFinished_ruleIsNotFinished() {
        StatefulRule topRule = iterate("x").over("1").asRule();
        StatefulRule triggeredRule = when("x").iterate("y").over("3", "4")
            .asRule();
		MapBasedState state = new MapBasedState();
		topRule.combinationStarted(state);
		triggeredRule.combinationStarted(state);
		topRule.propertyValueSet(new PropertyAssignedEvent(state,
				triggeredRule, triggeredRule.getTriggeringProperties()));
		topRule.combinationFinished(state);
		assertThat(topRule.hasFinished(), is(false));

	}
}
