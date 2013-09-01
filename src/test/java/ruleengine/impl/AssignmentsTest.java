package ruleengine.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import ruleengine.SpecialValue;
import ruleengine.impl.Assignments;

public class AssignmentsTest {

    @Test
    public void returnsUndefinedForNotAssignedProperty() {
        Assignments assignments = new Assignments();
        assertThat(assignments.get("unknown"), equalTo((Object)SpecialValue.UNDEFINED));
    }

}
