package org.dpolivaev.tsgen.ruleengine;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.junit.Test;

public class RuleBuilderTest {

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
