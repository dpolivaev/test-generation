package ruleengine;

import static org.junit.Assert.*;

import org.junit.Test;

public class AssignmentsTest {

    @Test(expected=UnknownPropertyException.class)
    public void test() {
        Assignments assignments = new Assignments();
        assignments.get("unknown");
    }

}
