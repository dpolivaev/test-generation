package org.dpolivaev.tsgen.ruleengine.internal;

import static org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory.iterate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.dpolivaev.tsgen.ruleengine.AssignmentFormatter;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.internal.Assignment;
import org.dpolivaev.tsgen.ruleengine.internal.Assignments;
import org.dpolivaev.tsgen.ruleengine.internal.StatefulRule;
import org.dpolivaev.tsgen.utils.Utils;
import org.junit.Before;
import org.junit.Test;

public class AssignmentsTest {

	Assignments assignments;

    private StatefulRule ruleForProperty(String propertyName) {
        return iterate(propertyName).asRule();
    }


	@Before
	public void setup() {
		assignments = new Assignments();
	}

    @Test
    public void returnsUndefinedForNotAssignedProperty() {
        assertThat(assignments.get("unknown"), equalTo((Object)SpecialValue.UNDEFINED));
    }

	@Test
	public void stateWithOnePropertySetAtFirstCombination() {
		clearAssignments();
        assignments.add(new Assignment(ruleForProperty("x"), "a",
            ""));
        String expectedScriptPropertyCombinations = "x=a";
		assertEquals(expectedScriptPropertyCombinations,
            AssignmentFormatter.create("=", ", ").format(assignments));
	}


    private void clearAssignments() {
        assignments = new Assignments();
    }

	@Test
	public void stateWithOneProperty_containsItsValue() {
		clearAssignments();
        assignments.add(new Assignment(ruleForProperty("x"), "a",
            ""));
		assertThat(assignments.containsProperties(Utils.set("x")), equalTo(true));
	}

	@Test
	public void stateWithTwoPropertiesSetAtFirstCombination() {
		clearAssignments();
        assignments.add(new Assignment(ruleForProperty("x"), "a",
            ""));
        assignments.add(new Assignment(ruleForProperty("y"), "b",
            ""));
        String expectedScriptPropertyCombinations = "x=a, y=b";
		assertEquals(expectedScriptPropertyCombinations,
		AssignmentFormatter.create("=", ", ").format(assignments));
	}


    @Test
    public void oldPropertiesAreRemovedAfterIterationEnd() {
        clearAssignments();
        assignments.add(new Assignment(ruleForProperty("x"), "a",
            ""));
        clearAssignments();
        assertThat(assignments.containsProperties(Utils.set("x")), equalTo(false));
    }


}
