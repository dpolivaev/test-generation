package ruleengine;

import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.mockito.Mockito;

public class TestUtils {
    @SafeVarargs
    public static <T> Set<T> set(T... elements) {
        HashSet<T> set = new HashSet<T>();
        set.addAll(Arrays.asList(elements));
		return set;
	}

    static Rule ruleMock(String targetedPropertyName) {
        Rule rule = mock(Rule.class);
        Mockito.when(rule.getTargetedPropertyName()).thenReturn(targetedPropertyName);
        return rule;
    }
    
    static Assignment assignmentMock(String name, String value){
        return new Assignment(ruleMock(name), value, "");
    }

}
