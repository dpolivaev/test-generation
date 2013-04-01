package ruleengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StateTest {

	State state ; 
	
	@Before
	public void setup() {
		state = new State();
	}
	
	
	@Test
	public void stateWithOnePropertySetAtFirstIteration() {
		state.nextIteration();
		state.addProperty("x","a");
		String expectedScriptPropertyCombinations = "1 : x=a\n";
		assertEquals(expectedScriptPropertyCombinations, state.getAssignedPropertiesAsString());
	}

	@Test
	public void stateWithTwoPropertiesSetAtFirstIteration() {
		state.nextIteration();
		state.addProperty("x","a");
		state.addProperty("y","b");
		String expectedScriptPropertyCombinations = "1 : x=a\ty=b\n";
		assertEquals(expectedScriptPropertyCombinations, state.getAssignedPropertiesAsString());
	}

}
