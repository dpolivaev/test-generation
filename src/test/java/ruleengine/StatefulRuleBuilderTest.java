package ruleengine;

import org.junit.Test;

public class StatefulRuleBuilderTest {

	@Test(expected=IllegalStateException.class)
	public void trigggeringPropertiesSetTwice() {
		RuleBuilder.Factory.when("x").when("y");
	}
	
	@Test(expected=IllegalStateException.class)
	public void conditionSetTwice() {
		Condition falseCondition = new Condition() {
			@Override
			public boolean isSatisfied(PropertyContainer propertyContainer) {
				return false;
			}
		};
		RuleBuilder.Factory._if(falseCondition)._if(falseCondition);
	}

}
