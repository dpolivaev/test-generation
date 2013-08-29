package ruleengine;

import org.junit.Test;

public class StatefulRuleBuilderTest {

	@Test(expected=IllegalStateException.class)
	public void trigggeringPropertiesSetTwice() {
		StatefulRuleBuilder.Factory.when("x").when("y");
	}
	
	@Test(expected=IllegalStateException.class)
	public void conditionSetTwice() {
		Condition falseCondition = new Condition() {
			@Override
			public boolean isSatisfied(PropertyContainer propertyContainer) {
				return false;
			}
		};
		StatefulRuleBuilder.Factory._if(falseCondition)._if(falseCondition);
	}

}
