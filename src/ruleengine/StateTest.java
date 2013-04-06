package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ruleengine.TestUtils.set;

import org.junit.Before;
import org.junit.Test;

public class StateTest {

	State state;

	@Before
	public void setup() {
		state = new State();
	}

	@Test
	public void stateWithOnePropertySetAtFirstIteration() {
		state.nextIteration();
		state.setPropertyValue("x", "a");
		String expectedScriptPropertyCombinations = "1 : x=a\n";
		assertEquals(expectedScriptPropertyCombinations,
				state.getAssignedPropertiesAsString());
	}

	@Test
	public void stateWithOneProperty_containsItsValue() {
		state.nextIteration();
		state.setPropertyValue("x", "a");
		assertThat(state.containsPropertyValue("x"), is(true));
		assertThat(state.containsPropertyValues(set("x")), is(true));
	}

	@Test
	public void stateWithTwoPropertiesSetAtFirstIteration() {
		state.nextIteration();
		state.setPropertyValue("x", "a");
		state.setPropertyValue("y", "b");
		String expectedScriptPropertyCombinations = "1 : x=a\ty=b\n";
		assertEquals(expectedScriptPropertyCombinations,
				state.getAssignedPropertiesAsString());
	}

}
