package org.dpolivaev.tsgen.ruleengine.internal;

import static org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory.iterate;
import static org.dpolivaev.tsgen.testutils.TestUtils.rulePropertyNameMatches;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;

import java.util.Collections;
import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.EngineState;
import org.dpolivaev.tsgen.ruleengine.Rule;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.internal.PropertyAssignedEvent;
import org.dpolivaev.tsgen.utils.internal.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RuleTest {
    private EngineState engineState;
    private Strategy strategy;
    private PropertyAssignedEvent propertyAssignedEvent;

    private void iterationWith(Rule statefulRule) {
        statefulRule.propertyCombinationStarted(engineState);
        statefulRule.propertyCombinationFinished(engineState);
    }

	@Before
    public void setup() {
        engineState = mock(EngineState.class);
        strategy = mock(Strategy.class);
        when(engineState.currentStrategy()).thenReturn(strategy);
        when(engineState.getCombinationCounter()).thenReturn(1);
        propertyAssignedEvent = mock(PropertyAssignedEvent.class);

    }

	@Test
    public void ruleWithOneValue_blocksRequiredPropertiesUntilCombinationIsFinished() {
        Rule statefulRule = iterate("name").over("value").create();
        statefulRule.propertyCombinationStarted(engineState);
        assertThat(statefulRule.blocksRequiredProperties(), equalTo(true));
	}

    @Test
    public void ruleWithOneValue_assignsItsValue() {
        Rule statefulRule = iterate("name").over("value").create();

        statefulRule.propertyCombinationStarted(engineState);
        verify(engineState).setPropertyValue(statefulRule, "value", true);
    }

    @Test
    public void ruleWithAssignedValue_isActive() {
        Rule statefulRule = iterate("name").over("value").create();
        statefulRule.propertyCombinationStarted(engineState);
        assertThat(statefulRule.isValueAddedToCurrentCombination(), equalTo(true));
    }

    @Test
    public void ruleIsNotActive_afterItHasFinished() {
        Rule statefulRule = iterate("name").over("value").create();
        iterationWith(statefulRule);
        assertThat(statefulRule.isValueAddedToCurrentCombination(), equalTo(false));
    }

    @Test
    public void ruleWithOneValue_doesNotBlockRequiredPropertiesAfterCombinationIsFinished() {
        Rule statefulRule = iterate("name").over("value").create();
        iterationWith(statefulRule);
        assertThat(statefulRule.blocksRequiredProperties(), equalTo(false));
    }

    @Test
    public void ruleWithOneValue_blocksRequiredProperties_afterRuleEngineCombinationCounterIsReset() {
        Rule statefulRule = iterate("name").over("value").create();
        when(engineState.getCombinationCounter()).thenReturn(1);
        statefulRule.propertyCombinationStarted(engineState);
        statefulRule.propertyCombinationFinished(engineState);
        statefulRule.propertyCombinationStarted(engineState);
        assertThat(statefulRule.blocksRequiredProperties(), equalTo(true));
    }

	@Test
    public void ruleWithTwoValues_blocksRequiredPropertiesAfterFirstCombinationIsFinished() {
        Rule statefulRule = iterate("name").over("1", "2").create();

        iterationWith(statefulRule);
        assertThat(statefulRule.blocksRequiredProperties(), equalTo(true));
	}

	@Test
	public void ruleWithTwoValues_assignsSecondValueOnSecondTime() {
        Rule statefulRule = iterate("name").over("1", "2").create();

        iterationWith(statefulRule);
        statefulRule.propertyCombinationStarted(engineState);
        verify(engineState).setPropertyValue(statefulRule, "2", true);
	}

    @Test
    public void topRuleWithTwoValues_forcesIteration() {
        Rule rule = iterate("name").over("1", "2").create();
        assertThat(rule.forcesIteration(), equalTo(true));
    }
    
    @Test
    public void topRuleWithOneValues_forcesNoIteration() {
        Rule rule = iterate("name").over("1").create();
        assertThat(rule.forcesIteration(), equalTo(false));
    }
    
    @Test
    public void triggeredRuleWithTwoValues_forcesIteration() {
        Rule rule = iterate("name").over("1", "2").when("otherProptery").create();
        assertThat(rule.forcesIteration(), equalTo(true));
    }
    
    @Test
    public void defaultRuleWithTwoValues_doesNotforceIteration() {
        Rule rule = iterate("name").over("1", "2").asDefaultRule().create();
        assertThat(rule.forcesIteration(), equalTo(false));
    }
    
    @Test
    public void ifTriggeringPropertiesAreNotSet_triggeredDefaultRuleIsNotFired() {
        Rule rule = iterate("name").over("1", "2").when("x").asDefaultRule().create();
        when(engineState.containsTriggeringPropertyValue("x")).thenReturn(false);
        rule.propertyRequired(engineState);
        verify(engineState, Mockito.never()).setPropertyValue(Mockito.<Rule>any(), Mockito.any(), Mockito.anyBoolean());
    }
    
    @Test
    public void ifTriggeringPropertiesAreSet_triggeredDefaultRuleIsFired() {
        Rule rule = iterate("name").over("1", "2").when("x").asDefaultRule().create();
        when(engineState.containsTriggeringPropertyValue("x")).thenReturn(true);
        rule.propertyRequired(engineState);
        verify(engineState).setPropertyValue(Mockito.eq(rule), Mockito.any(), Mockito.anyBoolean());
    }
    
	@Test
	public void ruleWithOneValue_assignsItsValueOnSecondTime() {
        Rule statefulRule = iterate("name").over("value").create();
        EngineState engineState1 = mock(EngineState.class);
        statefulRule.propertyCombinationStarted(engineState1);
        statefulRule.propertyCombinationFinished(engineState1);
        EngineState engineState2 = mock(EngineState.class);
        statefulRule.propertyCombinationStarted(engineState2);
        verify(engineState2).setPropertyValue(statefulRule, "value", true);
	}

	@Test
    public void triggeredRuleWithOneValue_ignoresCombinationWithoutItsTriggeringProperty() {
        Rule statefulRule = iterate("name").when("triggeredBy").over("value").create();

        iterationWith(statefulRule);
        assertThat(statefulRule.blocksRequiredProperties(), equalTo(false));
    }

    @Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_assignsItsValue() {
        Rule triggeringRule = iterate("triggeredBy").over("value1").create();
        Rule triggeredRule = iterate("name").over("value2").when("triggeredBy").create();
        Mockito.when(engineState.containsTriggeringPropertyValue("triggeredBy")).thenReturn(true);
        triggeredRule.propertyValueSet( //
            new PropertyAssignedEvent(engineState, triggeringRule, Collections.<String> emptySet(), true));
        verify(engineState).setPropertyValue(triggeredRule, "value2", true);
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_finishes() {
        Rule triggeringRule = iterate("triggeredBy").over("value1").create();
        Rule triggeredRule = iterate("name").over("value2").when("triggeredBy").create();
        Mockito.when(engineState.containsTriggeringPropertyValue("triggeredBy")).thenReturn(true);
        triggeredRule.propertyValueSet(new PropertyAssignedEvent(engineState, triggeringRule, Collections
            .<String> emptySet(), true));

        triggeredRule.propertyCombinationFinished(engineState);

        assertThat(triggeredRule.blocksRequiredProperties(), equalTo(false));
	}

	@Test
	public void ruleIgnoresAssignmentsToNonTriggeringProperties() {
        Rule statefulRule = iterate("name").over("value").create();
        statefulRule.propertyCombinationStarted(engineState);
        statefulRule.propertyValueSet(propertyAssignedEvent);
        assertThat(statefulRule.blocksRequiredProperties(), equalTo(true));
	}

	@Test
	public void secondCallOfFinishCombinationIsIgnored() {
        Rule statefulRule = iterate("name").over("1", "2").create();
        iterationWith(statefulRule);
        
        statefulRule.propertyCombinationFinished(engineState);
        assertThat(statefulRule.blocksRequiredProperties(), equalTo(true));
	}

    @SuppressWarnings("unchecked")
    @Test
	public void givenTriggeredRuleIsNotFinished_ruleIsNotFinished() {
        Rule topRule = iterate("x").over("1").create();

        Rule triggeredRule = mock(Rule.class);
        when(triggeredRule.calculateRequiredProperties(any(Set.class))).thenReturn(Utils.set("x"));
        when(triggeredRule.blocksRequiredProperties()).thenReturn(true);

        topRule.propertyCombinationStarted(engineState);
        topRule.propertyValueSet(new PropertyAssignedEvent(engineState, triggeredRule, Collections.<String> emptySet(),
            true));
        topRule.propertyCombinationFinished(engineState);
        assertThat(topRule.blocksRequiredProperties(), equalTo(true));
	}

    @Test
    public void ruleWithTemporaryRule_addsItsRule() {
        RuleBuilder temporaryRule = iterate("y").over("b");
        Rule statefulRule = iterate("x").over("a").with(temporaryRule).create();

        statefulRule.propertyCombinationStarted(engineState);
        verify(engineState).addRule(argThat(rulePropertyNameMatches("x")), argThat(rulePropertyNameMatches("y")));
    }

    @Test
    public void ruleWithTwoTemporaryRules_addsItsRules() {
    	RuleBuilder temporaryRule1 = iterate("y1").over("b");
    	RuleBuilder temporaryRule2 = iterate("y2").over("b");
        Rule statefulRule = iterate("x").over("a").with(temporaryRule1).with(temporaryRule2).create();

        statefulRule.propertyCombinationStarted(engineState);
        verify(engineState).addRule(argThat(rulePropertyNameMatches("x")), argThat(rulePropertyNameMatches("y1")));
        verify(engineState).addRule(argThat(rulePropertyNameMatches("y1")), argThat(rulePropertyNameMatches("y2")));
    }

    @Test
    public void ruleWithTemporaryRule_removesItsRule() {
    	RuleBuilder temporaryRuleCreator = mock(RuleBuilder.class);
        Rule temporaryRule = mock(Rule.class);
        when(temporaryRule.blocksRequiredProperties()).thenReturn(false);
        when(temporaryRuleCreator.create()).thenReturn(temporaryRule);

        Rule statefulRule = iterate("x").over("a").with(temporaryRuleCreator).create();
        statefulRule.propertyCombinationStarted(engineState);
        statefulRule.propertyCombinationFinished(engineState);

        verify(strategy).removeRule(temporaryRule);
    }

    @Test
    public void topRule_canTriggerOtherRules() {
        Rule topRule = iterate("x").over("1").create();
        assertThat(topRule.isDefaultRule(), equalTo(false));
    }

    @Test
    public void triggeredRule_canTriggerOtherRules() {
        Rule triggeredRule = iterate("x").over("1").when("y").create();
        assertThat(triggeredRule.isDefaultRule(), equalTo(false));
    }

    @Test
    public void defaultRule_canNotTriggerOtherRules() {
        Rule defaultRule = iterate("x").over("1").asDefaultRule().create();
        assertThat(defaultRule.isDefaultRule(), equalTo(true));
    }

}
