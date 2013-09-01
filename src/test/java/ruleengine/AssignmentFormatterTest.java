package ruleengine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static ruleengine.AssignmentFormatter.create;
import static testutils.TestUtils.ruleMock;

import java.util.AbstractMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ruleengine.impl.Assignment;
import ruleengine.impl.Assignments;

public class AssignmentFormatterTest {

    private AssignmentFormatter formatter;
    private StringBuilder assignedPropertiesStringBuilder;

    public AssignmentFormatterTest() {
    }

    @Before
    public void setup() {
        formatter = create("=", ",");
        assignedPropertiesStringBuilder = new StringBuilder();
    }

    @Test
    public void formatsOneEntry() {
        formatter.append(assignedPropertiesStringBuilder, //
            new AbstractMap.SimpleEntry<String, Assignment>("name", new Assignment(null, "value", "reason->")));
        assertThat(assignedPropertiesStringBuilder.toString(), equalTo("reason->name=value"));
    }

    @Test
    public void formatsTwoEntries() {
        Rule rule1 = ruleMock("name");
        Rule rule2 = ruleMock("name2");
        Assignments assignments = new Assignments();
        assignments.add(new Assignment(rule1, "value", ""));
        assignments.add(new Assignment(rule2, "value2", ""));
        assertThat(formatter.format(assignments), equalTo("name=value,name2=value2"));
    }

    @Test
    public void formatsOneEntryWithoutReason() {
        formatter.appendReasons(false);
        formatter.append(assignedPropertiesStringBuilder, //
            new AbstractMap.SimpleEntry<String, Assignment>("name", new Assignment(null, "value", "reason->")));
        assertThat(assignedPropertiesStringBuilder.toString(), equalTo("name=value"));
    }


    @Test
    public void formatsOnlyIteratingRules() {
        Rule rule1 = ruleMock("name");
        Mockito.when(rule1.forcesIteration()).thenReturn(true);
        Rule rule2 = ruleMock("name2");
        Mockito.when(rule2.forcesIteration()).thenReturn(false);
        
        Assignments assignments = new Assignments();
        assignments.add(new Assignment(rule1, "value", ""));
        assignments.add(new Assignment(rule2, "value2", ""));
        
        formatter.shouldFormatIteratingRulesOnly(true);
        assertThat(formatter.format(assignments), equalTo("name=value"));
    }
}
