package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static ruleengine.StatefulRuleBuilder.Factory.iterate;
import static ruleengine.StatefulRuleBuilder.Factory.when;
import static ruleengine.TestUtils.set;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class StatefulRuleTest {
    private EngineState engineState;
    private PropertyAssignedEvent propertyAssignedEvent;

    private void iterationWith(StatefulRule statefulRule) {
        statefulRule.propertyCombinationStarted(engineState);
        statefulRule.propertyCombinationFinished(engineState);
    }

    @Before
    public void setup() {
        engineState = mock(EngineState.class);
        propertyAssignedEvent = mock(PropertyAssignedEvent.class);

    }

	@Test
	public void ruleWithOneValue_hasNotFinishedUntilCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
		assertThat(statefulRule.hasFinished(), is(false));
	}

    @Test
    public void ruleWithOneValue_assignsItsValue() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();

        statefulRule.propertyCombinationStarted(engineState);
        verify(engineState).setPropertyValue(statefulRule, "value");
    }

    @Test
    public void ruleWithAssignedValue_isActive() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        statefulRule.propertyCombinationStarted(engineState);
        assertThat(statefulRule.isActive(), is(true));
    }

    @Test
    public void ruleIsNotActive_afterItHasFinished() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        iterationWith(statefulRule);
        assertThat(statefulRule.isActive(), is(false));
    }

    @Test
	public void ruleWithOneValue_hasFinishedAfterCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        iterationWith(statefulRule);
		assertThat(statefulRule.hasFinished(), is(true));
	}

	@Test
	public void ruleWithTwoValues_hasNotFinishedAfterFirstCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("1", "2").asRule();

        iterationWith(statefulRule);
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithTwoValues_assignsSecondValueOnSecondTime() {
        StatefulRule statefulRule = iterate("name").over("1", "2").asRule();

        iterationWith(statefulRule);
        statefulRule.propertyCombinationStarted(engineState);
        verify(engineState).setPropertyValue(statefulRule, "2");
	}

	@Test
	public void ruleWithOneValue_assignsItsValueOnSecondTime() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        EngineState engineState1 = mock(EngineState.class);
        statefulRule.propertyCombinationStarted(engineState1);
        statefulRule.propertyCombinationFinished(engineState1);
        EngineState engineState2 = mock(EngineState.class);
        statefulRule.propertyCombinationStarted(engineState2);
        verify(engineState2).setPropertyValue(statefulRule, "value");
	}

	@Test
	public void triggeredRuleWithOneValue_ignoresCombinationWithoutItsTriggeringProperty() {
        StatefulRule statefulRule = when("triggeredBy").iterate("name")
            .over("value").asRule();

        iterationWith(statefulRule);
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_assignsItsValue() {
        Rule triggeringRule = iterate("triggeredBy").over("value1").asRule();
        StatefulRule triggeredRule = when("triggeredBy").iterate("name").over("value2").asRule();
        Mockito.when(engineState.containsPropertyValues(set("triggeredBy"))).thenReturn(true);
        triggeredRule.propertyValueSet( //
            new PropertyAssignedEvent(engineState, triggeringRule, Collections.<String> emptySet()));
        verify(engineState).setPropertyValue(triggeredRule, "value2");
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_finishes() {
        Rule triggeringRule = iterate("triggeredBy").over("value1").asRule();
        StatefulRule triggeredRule = when("triggeredBy").iterate("name").over("value2").asRule();
        Mockito.when(engineState.containsPropertyValues(set("triggeredBy"))).thenReturn(true);
        triggeredRule.propertyValueSet(new PropertyAssignedEvent(engineState, triggeringRule, Collections
            .<String> emptySet()));

        triggeredRule.propertyCombinationFinished(engineState);

        assertThat(triggeredRule.hasFinished(), is(true));
	}

	@Test
	public void ruleIgnoresAssignmentsToNonTriggeringProperties() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        statefulRule.propertyValueSet(propertyAssignedEvent);
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void secondCallOfFinishCombinationIsIgnored() {
        StatefulRule statefulRule = iterate("name").over("1", "2").asRule();
        iterationWith(statefulRule);
        
        statefulRule.propertyCombinationFinished(engineState);
		assertThat(statefulRule.hasFinished(), is(false));
	}

	@Test
	public void givenTriggeredRuleIsNotFinished_ruleIsNotFinished() {
        StatefulRule topRule = iterate("x").over("1").asRule();
        StatefulRule triggeredRule = when("x").iterate("y").over("3", "4")
            .asRule();
		MapBasedState state = new MapBasedState();
		topRule.propertyCombinationStarted(state);
		triggeredRule.propertyCombinationStarted(state);
        topRule.propertyValueSet(new PropertyAssignedEvent(engineState,
				triggeredRule, triggeredRule.getTriggeringProperties()));

        topRule.propertyCombinationFinished(engineState);
		assertThat(topRule.hasFinished(), is(false));
	}

    @Test
    public void ruleWithTemporaryRule_addsItsRule() {
        StatefulRule statefulRule = iterate("x").over("a", iterate("y").over("b")).asRule();

        statefulRule.propertyCombinationStarted(engineState);
    }

}
