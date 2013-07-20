package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static ruleengine.StatefulRuleBuilder.Factory.iterate;
import static ruleengine.TestUtils.set;

import java.util.Collections;
import java.util.Set;

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
    public void ruleWithOneValue_blocksRequiredPropertiesUntilCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        assertThat(statefulRule.blocksRequiredProperties(), is(true));
	}

    @Test
    public void ruleWithOneValue_assignsItsValue() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();

        statefulRule.propertyCombinationStarted(engineState);
        verify(engineState).setPropertyValue(statefulRule, "value", true);
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
    public void ruleWithOneValue_doesNotBlockRequiredPropertiesAfterCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        iterationWith(statefulRule);
        assertThat(statefulRule.blocksRequiredProperties(), is(false));
	}

	@Test
    public void ruleWithTwoValues_blocksRequiredPropertiesAfterFirstCombinationIsFinished() {
        StatefulRule statefulRule = iterate("name").over("1", "2").asRule();

        iterationWith(statefulRule);
        assertThat(statefulRule.blocksRequiredProperties(), is(true));
	}

	@Test
	public void ruleWithTwoValues_assignsSecondValueOnSecondTime() {
        StatefulRule statefulRule = iterate("name").over("1", "2").asRule();

        iterationWith(statefulRule);
        statefulRule.propertyCombinationStarted(engineState);
        verify(engineState).setPropertyValue(statefulRule, "2", true);
	}

	@Test
	public void ruleWithOneValue_assignsItsValueOnSecondTime() {
        StatefulRule statefulRule = iterate("name").over("value").asRule();
        EngineState engineState1 = mock(EngineState.class);
        statefulRule.propertyCombinationStarted(engineState1);
        statefulRule.propertyCombinationFinished(engineState1);
        EngineState engineState2 = mock(EngineState.class);
        statefulRule.propertyCombinationStarted(engineState2);
        verify(engineState2).setPropertyValue(statefulRule, "value", true);
	}

	@Test
    public void triggeredRuleWithOneValue_ignoresCombinationWithoutItsTriggeringProperty() {
        StatefulRule statefulRule = iterate("name").when("triggeredBy").over("value").asRule();

        iterationWith(statefulRule);
        assertThat(statefulRule.blocksRequiredProperties(), is(false));
    }

    @Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_assignsItsValue() {
        Rule triggeringRule = iterate("triggeredBy").over("value1").asRule();
        StatefulRule triggeredRule = iterate("name").over("value2").when("triggeredBy").asRule();
        Mockito.when(engineState.containsPropertyValues(set("triggeredBy"))).thenReturn(true);
        triggeredRule.propertyValueSet( //
            new PropertyAssignedEvent(engineState, triggeringRule, Collections.<String> emptySet(), true));
        verify(engineState).setPropertyValue(triggeredRule, "value2", true);
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_finishes() {
        Rule triggeringRule = iterate("triggeredBy").over("value1").asRule();
        StatefulRule triggeredRule = iterate("name").over("value2").when("triggeredBy").asRule();
        Mockito.when(engineState.containsPropertyValues(set("triggeredBy"))).thenReturn(true);
        triggeredRule.propertyValueSet(new PropertyAssignedEvent(engineState, triggeringRule, Collections
            .<String> emptySet(), true));

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

    @SuppressWarnings("unchecked")
    @Test
	public void givenTriggeredRuleIsNotFinished_ruleIsNotFinished() {
        StatefulRule topRule = iterate("x").over("1").asRule();

        StatefulRule triggeredRule = mock(StatefulRule.class);
        when(triggeredRule.requiredProperties(any(Set.class))).thenReturn(set("x"));
        when(triggeredRule.blocksRequiredProperties()).thenReturn(true);

        topRule.propertyCombinationStarted(engineState);
        topRule.propertyValueSet(new PropertyAssignedEvent(engineState, triggeredRule, Collections.<String> emptySet(),
            true));
        topRule.propertyCombinationFinished(engineState);
        assertThat(topRule.blocksRequiredProperties(), is(true));
	}

    @Test
    public void ruleWithTemporaryRule_addsItsRule() {
        StatefulRule temporaryRule = iterate("y").over("b").asTriggeredRule();
        StatefulRule statefulRule = iterate("x").over("a").with(temporaryRule).asRule();

        statefulRule.propertyCombinationStarted(engineState);
        verify(strategy).addRule(temporaryRule);
    }

    @Test
    public void ruleWithTemporaryRule_removesItsRule() {
        StatefulRule temporaryRule = mock(StatefulRule.class);
        when(temporaryRule.blocksRequiredProperties()).thenReturn(false);

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
        StatefulRule defaultRule = iterate("x").over("1").asDefaultRule();
        assertThat(defaultRule.isDefaultRule(), is(true));
    }

}
