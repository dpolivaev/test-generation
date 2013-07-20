package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ruleengine.StatefulRuleBuilder.Factory.iterate;
import static ruleengine.TestUtils.set;

import org.junit.Before;
import org.junit.Test;


public class MapBasedStateTest {

	MapBasedState mapBasedState;

    private StatefulRule ruleForProperty(String propertyName) {
        return iterate(propertyName).asRule();
    }


	@Before
	public void setup() {
		mapBasedState = new MapBasedState();
	}

	@Test
	public void stateWithOnePropertySetAtFirstCombination() {
		mapBasedState.nextCombination();
		mapBasedState.setPropertyValue(ruleForProperty("x"), "a");
        String expectedScriptPropertyCombinations = "x=a";
		assertEquals(expectedScriptPropertyCombinations,
            new StateFormatter().getAssignedPropertiesAsString(mapBasedState));
	}

	@Test
	public void stateWithOneProperty_containsItsValue() {
		mapBasedState.nextCombination();
		mapBasedState.setPropertyValue(ruleForProperty("x"), "a");
		assertThat(mapBasedState.containsPropertyValues(set("x")), is(true));
	}

	@Test
	public void stateWithTwoPropertiesSetAtFirstCombination() {
		mapBasedState.nextCombination();
		mapBasedState.setPropertyValue(ruleForProperty("x"), "a");
        mapBasedState.setPropertyValue(ruleForProperty("y"), "b");
        String expectedScriptPropertyCombinations = "x=a\ty=b";
		assertEquals(expectedScriptPropertyCombinations,
            new StateFormatter().getAssignedPropertiesAsString(mapBasedState));
	}


    @Test
    public void oldPropertiesAreRemovedAfterIterationEnd() {
        mapBasedState.nextCombination();
        mapBasedState.setPropertyValue(ruleForProperty("x"), "a");
        mapBasedState.nextCombination();
        assertThat(mapBasedState.containsPropertyValues(set("x")), is(false));
    }


}
