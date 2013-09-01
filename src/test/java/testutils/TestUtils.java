package testutils;

import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.mockito.Mockito;

import ruleengine.Rule;
import ruleengine.impl.Assignment;

public class TestUtils {
    @SafeVarargs
    public static <T> Set<T> set(T... elements) {
        HashSet<T> set = new HashSet<T>();
        set.addAll(Arrays.asList(elements));
		return set;
	}

    public static Rule ruleMock(String targetedPropertyName) {
        Rule rule = mock(Rule.class);
        Mockito.when(rule.getTargetedPropertyName()).thenReturn(targetedPropertyName);
        return rule;
    }
    
    public static Assignment assignmentMock(String name, Object value){
        return new Assignment(ruleMock(name), value, "");
    }

}
