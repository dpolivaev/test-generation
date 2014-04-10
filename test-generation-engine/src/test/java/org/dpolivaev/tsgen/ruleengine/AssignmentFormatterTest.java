package org.dpolivaev.tsgen.ruleengine;

import static org.dpolivaev.tsgen.ruleengine.AssignmentFormatter.create;
import static org.dpolivaev.tsgen.testutils.TestUtils.assignmentMock;
import static org.dpolivaev.tsgen.testutils.TestUtils.ruleMock;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.dpolivaev.tsgen.ruleengine.AssignmentFormatter;
import org.dpolivaev.tsgen.ruleengine.Rule;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
            assignmentMock("name","value", "reason->"));
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
            assignmentMock("name","value", "reason->"));
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

    @Test
    public void formatsOnlyIncludedRules() {
        Rule rule1 = ruleMock("name");
        Rule rule2 = ruleMock("name2");
        
        Assignments assignments = new Assignments();
        assignments.add(new Assignment(rule1, "value", ""));
        assignments.add(new Assignment(rule2, "value2", ""));
        
        formatter.include("name");
        assertThat(formatter.format(assignments), equalTo("name=value"));
    }
    
    @Test
    public void excludesRules() {
        Rule rule1 = ruleMock("name");
        Rule rule2 = ruleMock("name2");
        Rule rule3 = ruleMock(" nameAfterSpace");
        
        Assignments assignments = new Assignments();
        assignments.add(new Assignment(rule1, "value", ""));
        assignments.add(new Assignment(rule2, "value2", ""));
        assignments.add(new Assignment(rule3, "value3", ""));
        
        formatter.exclude("name2");
        assertThat(formatter.format(assignments), equalTo("name=value"));
    }

    
    @Test
    public void excludesUndefinedValues() {
        Rule rule1 = ruleMock("name");
        Rule rule2 = ruleMock("name2");
        
        Assignments assignments = new Assignments();
        assignments.add(new Assignment(rule1, "value", ""));
        assignments.add(new Assignment(rule2, SpecialValue.UNDEFINED, ""));
        
        formatter.excludeUndefined(true);
        assertThat(formatter.format(assignments), equalTo("name=value"));
    }
}
