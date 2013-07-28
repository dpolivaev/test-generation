package ruleengine;

import org.junit.Test;

public class AssignmentsTest {

    @Test(expected=UnknownPropertyException.class)
    public void test() {
        Assignments assignments = new Assignments();
        assignments.get("unknown");
    }

}
