package ruleengine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static ruleengine.AssignmentFormatter.formatter;

import java.util.AbstractMap;
import java.util.Map.Entry;

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
        formatter.append(assignedPropertiesStringBuilder, //
            new AbstractMap.SimpleEntry<String, Assignment>("name", new Assignment(null, "value", "")));
        formatter.append(assignedPropertiesStringBuilder, //
            new AbstractMap.SimpleEntry<String, Assignment>("name2", new Assignment(null, "value2", "")));
        assertThat(assignedPropertiesStringBuilder.toString(), equalTo("name=value,name2=value2"));
    }

    @Test
    public void formatsOneEntryWithoutReason() {
        formatter.appendReasons(false);
        formatter.append(assignedPropertiesStringBuilder, //
            new AbstractMap.SimpleEntry<String, Assignment>("name", new Assignment(null, "value", "reason->")));
        assertThat(assignedPropertiesStringBuilder.toString(), equalTo("name=value"));
    }

}
