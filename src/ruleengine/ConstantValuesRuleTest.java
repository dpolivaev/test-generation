package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class ConstantValuesRuleTest {

	@Test
	public void ruleWithOneValue_hasNotFinishedUntilValueIsReturned() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "value");
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithOneValue_returnsItsValue() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "value");
		OnePropertyHolder propertyHolder = propertyHolder();
		constantValuesRule.nextIteration(propertyHolder);
		assertThat(propertyHolder.getValue(), is((Object)"value"));
	}

	@Test
	public void ruleWithOneValue_hasFinishedAfterValueIsReturned() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "value");
		constantValuesRule.nextIteration(propertyHolder());
		assertThat(constantValuesRule.hasFinished(), is(true));
	}

	private OnePropertyHolder propertyHolder() {
		return new OnePropertyHolder();
	}

	@Test
	public void ruleWithTwoValues_hasNotFinishedAfterFirstValueIsReturned() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "1", "2");
		constantValuesRule.nextIteration(propertyHolder());
		assertThat(constantValuesRule.hasFinished(), is(false));
	}

	@Test
	public void ruleWithTwoValues_returnsSecondValueOnSecondTime() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "1", "2");
		OnePropertyHolder propertyHolder = propertyHolder();
		constantValuesRule.nextIteration(propertyHolder);
		constantValuesRule.nextIteration(propertyHolder);
		assertThat(propertyHolder.getValue(), is((Object)"2"));
	}

	@Test
	public void ruleWithOneValue_returnsItsValueOnSecondTime() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "value");
		OnePropertyHolder propertyHolder = propertyHolder();
		constantValuesRule.nextIteration(propertyHolder);
		constantValuesRule.nextIteration(propertyHolder);
		assertThat(propertyHolder.getValue(), is((Object)"value"));
	}
}
