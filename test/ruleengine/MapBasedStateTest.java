package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ruleengine.TestUtils.ruleStub;
import static ruleengine.TestUtils.set;

import org.junit.Before;
import org.junit.Test;

public class MapBasedStateTest {

	MapBasedState mapBasedState;

	@Before
	public void setup() {
		mapBasedState = new MapBasedState();
	}

	@Test
	public void stateWithOnePropertySetAtFirstCombination() {
		mapBasedState.nextCombination();
		mapBasedState.setPropertyValue(ruleStub("x"), "a");
		String expectedScriptPropertyCombinations = "1 : x=a\n";
		assertEquals(expectedScriptPropertyCombinations,
				mapBasedState.getAssignedPropertiesAsString());
	}

	@Test
	public void stateWithOneProperty_containsItsValue() {
		mapBasedState.nextCombination();
		mapBasedState.setPropertyValue(ruleStub("x"), "a");
		assertThat(mapBasedState.containsPropertyValues(set("x")), is(true));
	}

	@Test
	public void stateWithTwoPropertiesSetAtFirstCombination() {
		mapBasedState.nextCombination();
		mapBasedState.setPropertyValue(ruleStub("x"), "a");
		mapBasedState.setPropertyValue(ruleStub("y"), "b");
		String expectedScriptPropertyCombinations = "1 : x=a\ty=b\n";
		assertEquals(expectedScriptPropertyCombinations,
				mapBasedState.getAssignedPropertiesAsString());
	}

}
