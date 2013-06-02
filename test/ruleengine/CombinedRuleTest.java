package ruleengine;

import static ruleengine.StatefulRuleBuilder.Factory.*;

import org.junit.*;

//import static org.mockito.Mockito.*;

//import org.mockito.*;
public class CombinedRuleTest {

//    private Rule ruleMock(boolean active) {
//        Rule ruleMock = mock(Rule.class);
//        when(ruleMock.isActive()).thenReturn(active);
//        return ruleMock;
//    }
//
//    @Test
//    public void test() {
//        Rule first = ruleMock(false);
//        Rule second = ruleMock(false);
//
//        State state = mock(State.class);
//
//        new CombinedRule(first, second).propertyCombinationStarted(state);
//
//        InOrder inOrder = inOrder(first, second);
//        inOrder.verify(second).propertyCombinationStarted(state);
//        inOrder.verify(second).isActive();
//        inOrder.verify(first).propertyCombinationStarted(state);
//        inOrder.verify(first).isActive();
//        inOrder.verifyNoMoreInteractions();
//    }

    @Test(expected = IllegalArgumentException.class)
    public void combinedRuleForDifferentTargetedPropertiesAreNotAllowed() {
        new CombinedRule(iterate("a").asRule(), iterate("b").asRule());
    }

    @Test(expected = IllegalArgumentException.class)
    public void combinedRuleForDifferentTriggeringPropertiesAreNotAllowed() {
        new CombinedRule(iterate("y").asRule(), when("x").iterate("y").asRule());
    }
}
