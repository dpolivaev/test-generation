package ruleengine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static ruleengine.AssignmentFormatter.formatter;
import static ruleengine.TestUtils.ruleMock;

import java.util.AbstractMap;

import org.junit.Before;
import org.junit.Test;

public class AssignmentFormatterTest {

    private AssignmentFormatter formatter;
    private StringBuilder assignedPropertiesStringBuilder;

    public AssignmentFormatterTest() {
    }

    @Before
    public void setup() {
        formatter = formatter(",", "=");
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

}
