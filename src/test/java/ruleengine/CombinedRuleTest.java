package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ruleengine.StatefulRuleBuilder.Factory.iterate;

import java.util.Collections;

import org.junit.Test;
public class CombinedRuleTest {

    @SuppressWarnings("unchecked")
    private Rule ruleMock(boolean active) {
        Rule ruleMock = mock(Rule.class);
        when(ruleMock.isActive()).thenReturn(active);
        when(ruleMock.getTargetedPropertyName()).thenReturn("x");
        when(ruleMock.getTriggeringProperties()).thenReturn(Collections.EMPTY_SET);
        return ruleMock;
    }

    @Test
    public void givenNoActiveRules_propagatesPropertyCombinationStartedEventToAllRules() {
        Rule first = ruleMock(false);
        Rule second = ruleMock(false);
        EngineState state = mock(EngineState.class);

        new CombinedRule(first, second).propertyCombinationStarted(state);

        verify(second).propertyCombinationStarted(state);
        verify(first).propertyCombinationStarted(state);
    }

    @Test
    public void givenActiveRule_propagatesPropertyCombinationStartedUntilActiveRuleIsFound() {
        Rule first = ruleMock(false);
        Rule second = ruleMock(true);
        EngineState state = mock(EngineState.class);

        new CombinedRule(first, second).propertyCombinationStarted(state);

        verify(second).propertyCombinationStarted(state);
        verify(first, never()).propertyCombinationStarted(state);
    }

    @Test
    public void givenNoActiveRules_propagatesPropertyValueSetEventToAllRules() {
        Rule first = ruleMock(false);
        Rule second = ruleMock(false);
        PropertyAssignedEvent event = mock(PropertyAssignedEvent.class);

        new CombinedRule(first, second).propertyValueSet(event);

        verify(second).propertyValueSet(event);
        verify(first).propertyValueSet(event);
    }

    @Test
    public void givenActiveRule_propagatesPropertyValueSetUntilActiveRuleIsFound() {
        Rule first = ruleMock(false);
        Rule second = ruleMock(true);
        PropertyAssignedEvent event = mock(PropertyAssignedEvent.class);

        new CombinedRule(first, second).propertyValueSet(event);

        verify(second).propertyValueSet(event);
        verify(first, never()).propertyValueSet(event);
    }

    @Test
    public void givenActiveRule_propagatesPropertyCombinationFinishedToTheActiveRule() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(false);
        EngineState state = mock(EngineState.class);

        CombinedRule combinedRule = new CombinedRule(first, second);
        combinedRule.propertyCombinationStarted(state);
        combinedRule.propertyCombinationFinished(state);

        verify(first).propertyCombinationFinished(state);
        verify(second, never()).propertyCombinationFinished(state);
    }

    @Test
    public void givenActiveRule_propagatesSetNotFinishedToTheActiveRule() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(false);
        EngineState state = mock(EngineState.class);

        CombinedRule combinedRule = new CombinedRule(first, second);
        combinedRule.propertyCombinationStarted(state);
        combinedRule.setNotFinished();

        verify(first).setNotFinished();
        verify(second, never()).setNotFinished();
    }
    @Test
    public void targetedPropertyNameIsTakenFromTheFirstRules() {
        CombinedRule combinedRule = new CombinedRule(iterate("a").asRule(), iterate("a").asRule());
        assertThat(combinedRule.getTargetedPropertyName(), is("a"));
    }
    
    @Test
    public void triggeringPropertiesAreTakenFromTheFirstRules() {
        StatefulRule first = iterate("a").when("b", "c").asRule();
        StatefulRule second = iterate("a").when("b", "c").asRule();
        CombinedRule combinedRule = new CombinedRule(first, second);
        assertThat(combinedRule.getTriggeringProperties(), is(first.getTriggeringProperties()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void combinedRuleForDifferentTargetedPropertiesAreNotAllowed() {
        new CombinedRule(iterate("a").asRule(), iterate("b").asRule());
    }

    @Test(expected = IllegalArgumentException.class)
    public void combinedRuleForDifferentTriggeringPropertiesAreNotAllowed() {
        new CombinedRule(iterate("y").asRule(), iterate("y").when("x").asRule());
    }

    @Test
    public void CombinedRuleWithSizeMoreThan2ReturnsItself_AfterContainedRuleIsDeleted() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(true);
        Rule third = ruleMock(true);

        CombinedRule combinedRule = new CombinedRule(first, second);
        combinedRule.combineWith(third);

        assertThat(combinedRule.without(third), is((Rule) combinedRule));
    }

    @Test
    public void CombinedRuleWithSizeEqual2ReturnsAnotherContainedRule_AfterContainedRuleIsDeleted() {
        Rule first = ruleMock(true);
        Rule second = ruleMock(true);

        CombinedRule combinedRule = new CombinedRule(first, second);

        assertThat(combinedRule.without(first), is(second));
    }
}
