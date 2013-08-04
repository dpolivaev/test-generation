package ruleengine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AssignmentsTest {

    @Test
    public void returnsUndefinedForNotAssignedProperty() {
        Assignments assignments = new Assignments();
        assertThat(assignments.get("unknown"), equalTo((Object)SpecialValues.UNDEFINED));
    }

}
