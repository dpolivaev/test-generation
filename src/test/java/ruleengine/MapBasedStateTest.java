package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ruleengine.StatefulRuleBuilder.Factory.iterate;
import static ruleengine.TestUtils.set;

import org.junit.Before;
import org.junit.Test;


public class MapBasedStateTest {

	Assignments assignments;

    private StatefulRule ruleForProperty(String propertyName) {
        return iterate(propertyName).asRule();
    }


	@Before
	public void setup() {
		assignments = new Assignments();
	}

	@Test
	public void stateWithOnePropertySetAtFirstCombination() {
		clearAssignments();
        assignments.add(new Assignment(ruleForProperty("x"), "a",
            ""));
        String expectedScriptPropertyCombinations = "x=a";
		assertEquals(expectedScriptPropertyCombinations,
            new AssignmentFormatter().format(assignments));
	}


    private void clearAssignments() {
        assignments = new Assignments();
    }

	@Test
	public void stateWithOneProperty_containsItsValue() {
		clearAssignments();
        assignments.add(new Assignment(ruleForProperty("x"), "a",
            ""));
		assertThat(assignments.containsProperties(set("x")), is(true));
	}

	@Test
	public void stateWithTwoPropertiesSetAtFirstCombination() {
		clearAssignments();
        assignments.add(new Assignment(ruleForProperty("x"), "a",
            ""));
        assignments.add(new Assignment(ruleForProperty("y"), "b",
            ""));
        String expectedScriptPropertyCombinations = "x=a\ty=b";
		assertEquals(expectedScriptPropertyCombinations,
            new AssignmentFormatter().format(assignments));
	}


    @Test
    public void oldPropertiesAreRemovedAfterIterationEnd() {
        clearAssignments();
        assignments.add(new Assignment(ruleForProperty("x"), "a",
            ""));
        clearAssignments();
        assertThat(assignments.containsProperties(set("x")), is(false));
    }


}
