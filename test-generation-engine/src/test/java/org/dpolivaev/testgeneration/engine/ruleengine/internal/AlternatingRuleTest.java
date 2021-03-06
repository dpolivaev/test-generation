package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import static org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder.Factory.iterate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.dpolivaev.testgeneration.engine.ruleengine.EngineState;
import org.dpolivaev.testgeneration.engine.ruleengine.InconsistentRuleException;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.AlternatingRule;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.PropertyAssignedEvent;
import org.junit.Test;
public class AlternatingRuleTest {

    private Rule ruleMock(boolean active) {
        Rule ruleMock = mock(Rule.class);
        when(ruleMock.isValueAddedToCurrentCombination()).thenReturn(active);
        when(ruleMock.getTargetedPropertyName()).thenReturn("x");
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
        alternatingRule.setBlocksRequiredPropertiesItself(true);

        verify(first).setBlocksRequiredPropertiesItself(true);
        verify(second, never()).setBlocksRequiredPropertiesItself(true);
    }

    @Test
    public void targetedPropertyNameIsTakenFromTheFirstRules() {
        AlternatingRule alternatingRule = new AlternatingRule(iterate("a").create(), iterate("a").create());
        assertThat(alternatingRule.getTargetedPropertyName(), equalTo("a"));
    }
    
    @Test
    public void CombinedRuleWithSizeMoreThan2ReturnsItself_AfterContainedRuleIsDeleted() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(true);
        Rule third = ruleMock(true);

        AlternatingRule alternatingRule = new AlternatingRule(first, second);
        alternatingRule.combineWith(third);

        assertThat(alternatingRule.without(third), equalTo((Rule) alternatingRule));
    }

    @Test
    public void CombinedRuleWithSizeEqual2ReturnsAnotherContainedRule_AfterContainedRuleIsDeleted() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(true);

        AlternatingRule alternatingRule = new AlternatingRule(first, second);

        assertThat(alternatingRule.without(first), equalTo(second));
    }

    @Test(expected = IllegalArgumentException.class)
    public void combinedRuleForLazyRuleAndTriggeringRuleAreNotAllowed() {
        new AlternatingRule(iterate("a").create(), iterate("a").asLazyRule().create());
    }

    @Test(expected = InconsistentRuleException.class)
    public void conflictingTopRules() {
        Rule rule1 = mock(Rule.class);
        when(rule1.getTargetedPropertyName()).thenReturn("x");
        when(rule1.isLazyRule()).thenReturn(false);
        when(rule1.isValueAddedToCurrentCombination()).thenReturn(true);
        when(rule1.blocksRequiredProperties()).thenReturn(true);
        Rule rule2 = mock(Rule.class);
        when(rule2.getTargetedPropertyName()).thenReturn("x");
        when(rule1.isLazyRule()).thenReturn(false);
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

    @Test
    public void alternatingRuleRemovesItself() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(true);

        AlternatingRule alternatingRule = new AlternatingRule(first, second);
        assertThat(alternatingRule.without(alternatingRule), equalTo(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void alternatingRuleCanNotRemoveUnknownRule() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(true);
        Rule third = ruleMock(true);

        AlternatingRule alternatingRule = new AlternatingRule(first, second);
        alternatingRule.without(third);
    }

    @Test(expected=IllegalArgumentException.class)
    public void doesNotAllowFirstRuleWithoutTargetingPropertyName() {
        Rule first = mock(Rule.class);
        Rule second = ruleMock(false);
        EngineState state = mock(EngineState.class);

        new AlternatingRule(first, second).propertyCombinationStarted(state);
    }
}
