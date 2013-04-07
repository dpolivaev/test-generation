package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ruleengine.TestUtils.ruleStub;
import static ruleengine.TestUtils.set;

import org.junit.Test;

public class ConstantValuesRuleTest {

	@Test
	public void ruleWithOneValue_hasNotFinishedUntilIterationIsFinished() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"value");
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithOneValue_assignsItsValue() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"value");
		OnePropertyHolder propertyHolder = propertyHolder();
		constantValuesRule.nextIteration(propertyHolder);
		assertThat(propertyHolder.getValue(), is((Object) "value"));
	}

	@Test
	public void ruleWithOneValue_hasFinishedAfterIterationIsFinished() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"value");
		OnePropertyHolder propertyHolder = propertyHolder();
		constantValuesRule.nextIteration(propertyHolder);
		constantValuesRule.finishIteration(propertyHolder);
		assertThat(constantValuesRule.hasFinished(), is(true));
	}

	private OnePropertyHolder propertyHolder() {
		return new OnePropertyHolder();
	}

	@Test
	public void ruleWithTwoValues_hasNotFinishedAfterFirstIterationIsFinished() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"1", "2");
		constantValuesRule.nextIteration(propertyHolder());
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithTwoValues_assignsSecondValueOnSecondTime() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"1", "2");
		OnePropertyHolder propertyHolder = propertyHolder();
		constantValuesRule.nextIteration(propertyHolder);
		constantValuesRule.nextIteration(propertyHolder);
		assertThat(propertyHolder.getValue(), is((Object) "2"));
	}

	@Test
	public void ruleWithOneValue_assignsItsValueOnSecondTime() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name",
				"value");
		OnePropertyHolder propertyHolder = propertyHolder();
		constantValuesRule.nextIteration(propertyHolder);
		constantValuesRule.finishIteration(propertyHolder);
		constantValuesRule.nextIteration(propertyHolder);
		assertThat(propertyHolder.getValue(), is((Object) "value"));
	}

	@Test
	public void triggeredRuleWithOneValue_ignoresIterationBegin() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule(
				set("triggeredBy"), "name", "value");
		OnePropertyHolder propertyHolder = propertyHolder();
		constantValuesRule.nextIteration(propertyHolder);
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_assignsItsValue() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule(
				set("triggeredBy"), "name", "value");
		OnePropertyHolder propertyHolder = propertyHolder("triggeredBy",
				"triggeredByValue");
		PropertyAssignedEvent event = new PropertyAssignedEvent(propertyHolder,
				propertyHolder.ruleStub());
		constantValuesRule.propertyValueSet(event);
		assertThat(propertyHolder.getValue(), is((Object) "value"));
	}

	@Test
	public void triggeredRuleWithOneValue_whenTriggeredPropertyIsAssigned_finishes() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule(
				set("triggeredBy"), "name", "value");
		OnePropertyHolder propertyHolder = propertyHolder("triggeredBy",
				"triggeredByValue");
		PropertyAssignedEvent event = new PropertyAssignedEvent(propertyHolder,
				propertyHolder.ruleStub());
		constantValuesRule.propertyValueSet(event);
		constantValuesRule.finishIteration(propertyHolder);
		assertThat(constantValuesRule.hasFinished(), is(true));
	}

	private OnePropertyHolder propertyHolder(String name, String value) {
		OnePropertyHolder propertyHolder = propertyHolder();
		propertyHolder.setPropertyValue(ruleStub(name), value);
		return propertyHolder;
	}

	@Test
	public void ruleIgnoresAssignmentsToNonTriggeringProperties() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule(set(),
				"name", "value");
		OnePropertyHolder propertyHolder = propertyHolder("triggeredBy",
				"triggeredByValue");
		PropertyAssignedEvent event = new PropertyAssignedEvent(propertyHolder,
				propertyHolder.ruleStub());
		constantValuesRule.propertyValueSet(event);
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	// @Test
	// public void ruleWithOneValueIsNotFinished_untilTriggeredRuleIsFinished()
	// {
	// ConstantValuesRule rule = new ConstantValuesRule("x", "a");
	// ConstantValuesRule triggeredRule = new ConstantValuesRule(set("x"),
	// "y", "a", "b");
	// State state = new MapBasedState();
	// rule.nextIteration(state);
	//
	// triggeredRule.propertyValueSet(new PropertyAssignedEvent(state, "x"));
	//
	// assertThat(rule.hasFinished(), is(false));
	// }
}
