package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ruleengine.RuleBuilder.Factory.iterate;

import java.util.Collections;

import org.junit.Test;
public class AlternatingRuleTest {

    @SuppressWarnings("unchecked")
    private Rule ruleMock(boolean active) {
        Rule ruleMock = mock(Rule.class);
        when(ruleMock.isValueAddedToCurrentCombination()).thenReturn(active);
        when(ruleMock.getTargetedPropertyName()).thenReturn("x");
        when(ruleMock.getTriggeringProperties()).thenReturn(Collections.EMPTY_SET);
        return ruleMock;
    }

    @Test
    public void givenNoActiveRules_propagatesPropertyCombinationStartedEventToAllRules() {
        Rule first = ruleMock(false);
        Rule second = ruleMock(false);
        EngineState state = mock(EngineState.class);

        new AlternatingRule(first, second).propertyCombinationStarted(state);

        verify(second).propertyCombinationStarted(state);
        verify(first).propertyCombinationStarted(state);
    }

    @Test
    public void givenActiveRule_propagatesPropertyCombinationStartedUntilActiveRuleIsFound() {
        Rule first = ruleMock(false);
        Rule second = ruleMock(true);
        EngineState state = mock(EngineState.class);

        new AlternatingRule(first, second).propertyCombinationStarted(state);

        verify(second).propertyCombinationStarted(state);
        verify(first, never()).propertyCombinationStarted(state);
    }

    @Test
    public void givenActiveRule_propagatesPropertyRequiredUntilActiveRuleIsFound() {
        Rule first = ruleMock(false);
        Rule second = ruleMock(true);
        EngineState state = mock(EngineState.class);
        when(state.containsPropertyValues(anySetOf(String.class))).thenReturn(true);

        new AlternatingRule(first, second).propertyRequired(state);

        verify(second).propertyRequired(state);
        verify(first, never()).propertyRequired(state);
    }

    @Test
    public void givenNoActiveRules_propagatesPropertyValueSetEventToAllRules() {
        Rule first = ruleMock(false);
        Rule second = ruleMock(false);
        PropertyAssignedEvent event = mock(PropertyAssignedEvent.class);

        new AlternatingRule(first, second).propertyValueSet(event);

        verify(second).propertyValueSet(event);
        verify(first).propertyValueSet(event);
    }

    @Test
    public void givenActiveRule_propagatesPropertyValueSetUntilActiveRuleIsFound() {
        Rule first = ruleMock(false);
        Rule second = ruleMock(true);
        PropertyAssignedEvent event = mock(PropertyAssignedEvent.class);

        new AlternatingRule(first, second).propertyValueSet(event);

        verify(second).propertyValueSet(event);
        verify(first, never()).propertyValueSet(event);
    }

    @Test
    public void givenActiveRule_propagatesPropertyCombinationFinishedToTheActiveRule() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(false);
        EngineState state = mock(EngineState.class);

        AlternatingRule alternatingRule = new AlternatingRule(first, second);
        alternatingRule.propertyCombinationStarted(state);
        alternatingRule.propertyCombinationFinished(state);

        verify(first).propertyCombinationFinished(state);
        verify(second, never()).propertyCombinationFinished(state);
    }

    @Test
    public void givenActiveRule_propagatesSetNotFinishedToTheActiveRule() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(false);
        EngineState state = mock(EngineState.class);

        AlternatingRule alternatingRule = new AlternatingRule(first, second);
        alternatingRule.propertyCombinationStarted(state);
        alternatingRule.setBlocksRequiredProperties();

        verify(first).setBlocksRequiredProperties();
        verify(second, never()).setBlocksRequiredProperties();
    }

    @Test
    public void targetedPropertyNameIsTakenFromTheFirstRules() {
        AlternatingRule alternatingRule = new AlternatingRule(iterate("a").asRule(), iterate("a").asRule());
        assertThat(alternatingRule.getTargetedPropertyName(), is("a"));
    }
    
    @Test
    public void triggeringPropertiesAreTakenFromTheFirstRules() {
        StatefulRule first = iterate("a").when("b", "c").asRule();
        StatefulRule second = iterate("a").when("b", "c").asRule();
        AlternatingRule alternatingRule = new AlternatingRule(first, second);
        assertThat(alternatingRule.getTriggeringProperties(), is(first.getTriggeringProperties()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void combinedRuleForDifferentTargetedPropertiesAreNotAllowed() {
        new AlternatingRule(iterate("a").asRule(), iterate("b").asRule());
    }

    @Test(expected = IllegalArgumentException.class)
    public void combinedRuleForDifferentTriggeringPropertiesAreNotAllowed() {
        new AlternatingRule(iterate("y").asRule(), iterate("y").when("x").asRule());
    }

    @Test
    public void CombinedRuleWithSizeMoreThan2ReturnsItself_AfterContainedRuleIsDeleted() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(true);
        Rule third = ruleMock(true);

        AlternatingRule alternatingRule = new AlternatingRule(first, second);
        alternatingRule.combineWith(third);

        assertThat(alternatingRule.without(third), is((Rule) alternatingRule));
    }

    @Test
    public void CombinedRuleWithSizeEqual2ReturnsAnotherContainedRule_AfterContainedRuleIsDeleted() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(true);

        AlternatingRule alternatingRule = new AlternatingRule(first, second);

        assertThat(alternatingRule.without(first), is(second));
    }

    @Test(expected = IllegalArgumentException.class)
    public void combinedRuleForDefaultRuleAndTriggeringRuleAreNotAllowed() {
        new AlternatingRule(iterate("a").asRule(), iterate("a").asDefaultRule());
    }

    @Test(expected = InconsistentRuleException.class)
    public void conflictingTopRules() {
        Rule rule1 = mock(Rule.class);
        when(rule1.getTargetedPropertyName()).thenReturn("x");
        when(rule1.isDefaultRule()).thenReturn(false);
        when(rule1.isValueAddedToCurrentCombination()).thenReturn(true);
        when(rule1.blocksRequiredProperties()).thenReturn(true);
        Rule rule2 = mock(Rule.class);
        when(rule2.getTargetedPropertyName()).thenReturn("x");
        when(rule1.isDefaultRule()).thenReturn(false);
        when(rule2.isValueAddedToCurrentCombination()).thenReturn(false).thenReturn(true);
        when(rule2.blocksRequiredProperties()).thenReturn(true);
        EngineState state = mock(EngineState.class);
        AlternatingRule alternatingRule = new AlternatingRule(rule1, rule2);

        alternatingRule.propertyCombinationStarted(state);
        alternatingRule.propertyCombinationStarted(state);

    }


    @Test
    public void givenActiveRule_propagatesForcesIterationToTheActiveRule() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(false);
        EngineState state = mock(EngineState.class);

        AlternatingRule alternatingRule = new AlternatingRule(first, second);
        alternatingRule.propertyCombinationStarted(state);
        alternatingRule.forcesIteration();

        verify(first).forcesIteration();
        verify(second, never()).forcesIteration();
    }
}
