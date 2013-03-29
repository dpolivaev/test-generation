package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class ConstantValuesRuleTest {

	@Test
	public void ruleWithOneValue_isNotFinishedUntilValueIsReturned() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "value");
		assertThat(constantValuesRule.isFinished(), is(false));
	}

	@Test
	public void ruleWithOneValue_returnsItsValue() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "value");
		assertThat(constantValuesRule.nextValue(), is((Object)"value"));
	}

	@Test
	public void ruleWithOneValue_isFinishedAfterValueIsReturned() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "value");
		constantValuesRule.nextValue();
		assertThat(constantValuesRule.isFinished(), is(true));
	}

	@Test
	public void ruleWithTwoValues_isNotFinishedAfterFirstValueIsReturned() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "1", "2");
		constantValuesRule.nextValue();
		assertThat(constantValuesRule.isFinished(), is(false));
	}

	@Test
	public void ruleWithTwoValues_returnsSecondValueOnSecondTime() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "1", "2");
		constantValuesRule.nextValue();
		assertThat(constantValuesRule.nextValue(), is((Object)"2"));
	}

	@Test
	public void ruleWithOneValue_returnsItsValueOnSecondTime() {
		ConstantValuesRule constantValuesRule = new ConstantValuesRule("name", "value");
		constantValuesRule.nextValue();
		assertThat(constantValuesRule.nextValue(), is((Object)"value"));
	}
}
