package org.dpolivaev.tsgen.testutils;

import static org.mockito.Mockito.mock;

import org.dpolivaev.tsgen.ruleengine.Assignment;
import org.dpolivaev.tsgen.ruleengine.Rule;
import org.mockito.Mockito;

public class TestUtils {
    public static Rule ruleMock(String targetedPropertyName) {
        Rule rule = mock(Rule.class);
        Mockito.when(rule.getTargetedPropertyName()).thenReturn(targetedPropertyName);
        return rule;
    }
 
	static public Assignment assignmentMock(String name, Object value, String reason) {
		return new Assignment(ruleMock(name), value, reason);
	}

}
