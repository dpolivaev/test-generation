package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import static org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder.Factory.iterate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.AssignmentFormatter;
import org.dpolivaev.testgeneration.engine.ruleengine.Assignments;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.utils.internal.Utils;
import org.junit.Before;
import org.junit.Test;

public class AssignmentsTest {

	Assignments assignments;

    private Rule ruleForProperty(String propertyName) {
        return iterate(propertyName).create();
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
            "", Collections.<String>emptySet(), Collections.<String>emptySet()));
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
            "", Collections.<String>emptySet(), Collections.<String>emptySet()));
		assertThat(assignments.containsProperties(Utils.set("x")), equalTo(true));
	}

	@Test
	public void stateWithTwoPropertiesSetAtFirstCombination() {
		clearAssignments();
        assignments.add(new Assignment(ruleForProperty("x"), "a",
            "", Collections.<String>emptySet(), Collections.<String>emptySet()));
        assignments.add(new Assignment(ruleForProperty("y"), "b",
            "", Collections.<String>emptySet(), Collections.<String>emptySet()));
        String expectedScriptPropertyCombinations = "x=a, y=b";
		assertEquals(expectedScriptPropertyCombinations,
		AssignmentFormatter.create("=", ", ").format(assignments));
	}


    @Test
    public void oldPropertiesAreRemovedAfterIterationEnd() {
        clearAssignments();
        assignments.add(new Assignment(ruleForProperty("x"), "a",
            "", Collections.<String>emptySet(), Collections.<String>emptySet()));
        clearAssignments();
        assertThat(assignments.containsProperties(Utils.set("x")), equalTo(false));
    }


}
