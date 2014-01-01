package org.dpolivaev.tsgen.ruleengine;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.internal.OrderedValueProviders;
import org.dpolivaev.tsgen.ruleengine.internal.ShuffledValueProviders;
import org.dpolivaev.tsgen.ruleengine.internal.ValueProviders;
import org.hamcrest.CoreMatchers;

import static org.junit.Assert.*;

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
	
	@Test
	public void forcedOrderedValues(){
		ValueProviders ruleValues = RuleBuilder.Factory.iterate("x").over("a", "b").ordered().ruleValues();
		assertTrue(ruleValues instanceof OrderedValueProviders);
	}

	
	@Test
	public void forcedShuffledValues(){
		ShuffledValueProviders ruleValues = (ShuffledValueProviders) RuleBuilder.Factory.iterate("x").over("a", "b").shuffled().ruleValues();
		assertThat(ruleValues.getOrder(), CoreMatchers.equalTo(Order.SHUFFLED));
	}

	
	@Test
	public void orderedThanShuffledValues(){
		ShuffledValueProviders ruleValues = (ShuffledValueProviders) RuleBuilder.Factory.iterate("x").over("a", "b").ruleValues();
		assertThat(ruleValues.getOrder(), CoreMatchers.equalTo(Order.ORDERED_THEN_SHUFFLED));
	}

}
