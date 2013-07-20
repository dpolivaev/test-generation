package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static ruleengine.StatefulRuleBuilder.Factory.iterate;
import static ruleengine.TestUtils.set;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RuleTest {
    private EngineState engineState;
    private Strategy strategy;
    private PropertyAssignedEvent propertyAssignedEvent;

    private void iterationWith(StatefulRule statefulRule) {
        statefulRule.propertyCombinationStarted(engineState);
        statefulRule.propertyCombinationFinished(engineState);
    }

    @Before
    public void setup() {
        engineState = mock(EngineState.class);
        strategy = mock(Strategy.class);
        when(engineState.currentStrategy()).thenReturn(strategy);
        propertyAssignedEvent = mock(PropertyAssignedEvent.class);

    }

	@Test
	public void ruleWithOneValue_hasNotFinishedUntilCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        assertThat(statefulRule.blocksRequiredProperties(), is(true));
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
        assertThat(statefulRule.blocksRequiredProperties(), is(false));
	}

	@Test
	public void ruleWithTwoValues_hasNotFinishedAfterFirstCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("1", "2").asRule();

        iterationWith(statefulRule);
        assertThat(statefulRule.blocksRequiredProperties(), is(true));
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
        StatefulRule statefulRule = iterate("name").when("triggeredBy")
            .over("value").asRule();

        iterationWith(statefulRule);
        assertThat(statefulRule.blocksRequiredProperties(), is(true));
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_assignsItsValue() {
        Rule triggeringRule = iterate("triggeredBy").over("value1").asRule();
        StatefulRule triggeredRule = iterate("name").over("value2").when("triggeredBy").asRule();
        Mockito.when(engineState.containsPropertyValues(set("triggeredBy"))).thenReturn(true);
        triggeredRule.propertyValueSet( //
            new PropertyAssignedEvent(engineState, triggeringRule, Collections.<String> emptySet()));
        verify(engineState).setPropertyValue(triggeredRule, "value2");
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_finishes() {
        Rule triggeringRule = iterate("triggeredBy").over("value1").asRule();
        StatefulRule triggeredRule = iterate("name").over("value2").when("triggeredBy").asRule();
        Mockito.when(engineState.containsPropertyValues(set("triggeredBy"))).thenReturn(true);
        triggeredRule.propertyValueSet(new PropertyAssignedEvent(engineState, triggeringRule, Collections
            .<String> emptySet()));

        triggeredRule.propertyCombinationFinished(engineState);

        assertThat(triggeredRule.blocksRequiredProperties(), is(false));
	}

	@Test
	public void ruleIgnoresAssignmentsToNonTriggeringProperties() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        statefulRule.propertyValueSet(propertyAssignedEvent);
        assertThat(statefulRule.blocksRequiredProperties(), is(true));
	}

	@Test
	public void secondCallOfFinishCombinationIsIgnored() {
        StatefulRule statefulRule = iterate("name").over("1", "2").asRule();
        iterationWith(statefulRule);
        
        statefulRule.propertyCombinationFinished(engineState);
        assertThat(statefulRule.blocksRequiredProperties(), is(true));
	}

	@Test
	public void givenTriggeredRuleIsNotFinished_ruleIsNotFinished() {
        StatefulRule topRule = iterate("x").over("1").asRule();
        StatefulRule triggeredRule = iterate("y").over("3", "4").when("x")
            .asRule();
        topRule.propertyCombinationStarted(engineState);
        triggeredRule.propertyCombinationStarted(engineState);
        topRule.propertyValueSet(new PropertyAssignedEvent(engineState,
				triggeredRule, triggeredRule.getTriggeringProperties()));

        topRule.propertyCombinationFinished(engineState);
        assertThat(topRule.blocksRequiredProperties(), is(true));
	}

    @Test
    public void ruleWithTemporaryRule_addsItsRule() {
        StatefulRule temporaryRule = iterate("y").over("b").asRule();
        StatefulRule statefulRule = iterate("x").over("a").with(temporaryRule).asRule();

        statefulRule.propertyCombinationStarted(engineState);
        verify(strategy).addRule(temporaryRule);
    }

    @Test
    public void ruleWithTemporaryRule_removesItsRule() {
        StatefulRule temporaryRule = iterate("y").over("b").asRule();
        StatefulRule statefulRule = iterate("x").over("a").with(temporaryRule).asRule();

        statefulRule.propertyCombinationStarted(engineState);
        statefulRule.propertyCombinationFinished(engineState);

        verify(strategy).removeRule(temporaryRule);
    }

    @Test
    public void topRule_canTriggerOtherRules() {
        StatefulRule topRule = iterate("x").over("1").asRule();
        assertThat(topRule.isDefaultRule(), is(false));
    }

    @Test
    public void triggeredRule_canTriggerOtherRules() {
        StatefulRule triggeredRule = iterate("x").over("1").when("y").asRule();
        assertThat(triggeredRule.isDefaultRule(), is(false));
    }

    @Test
    public void defaultRule_canNotTriggerOtherRules() {
        StatefulRule defaultRule = iterate("x").over("1").byDefault().asRule();
        assertThat(defaultRule.isDefaultRule(), is(true));
    }

}
