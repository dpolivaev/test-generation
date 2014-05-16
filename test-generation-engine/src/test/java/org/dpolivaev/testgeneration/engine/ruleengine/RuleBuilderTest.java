package org.dpolivaev.testgeneration.engine.ruleengine;

import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.Order;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.OrderedValueProviders;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.ShuffledValueProviders;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.ValueProviders;
import org.dpolivaev.testgeneration.engine.utils.internal.Utils;
import org.hamcrest.CoreMatchers;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

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
	
	@Test
	public void nameProvider(){
		ValueProvider nameProvider = mock(ValueProvider.class);
		PropertyContainer propertyContainer = mock(PropertyContainer.class);
		when(nameProvider.value(Mockito.any(PropertyContainer.class))).thenReturn("x");
		final Rule rule = new RuleBuilder().iterate(nameProvider).over("a").create(propertyContainer);
		assertThat(rule.getTargetedPropertyName(), equalTo("x"));
	}


	@Test
	public void triggeringPropertyNameProvider(){
		ValueProvider nameProvider = mock(ValueProvider.class);
		PropertyContainer propertyContainer = mock(PropertyContainer.class);
		when(nameProvider.value(Mockito.any(PropertyContainer.class))).thenReturn("x");
		final Rule rule = new RuleBuilder().when(nameProvider).iterate("y").over("a").create(propertyContainer);
		assertThat(rule.getTriggeringProperties(), equalTo(Utils.set("x")));
	}
}
