package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.dpolivaev.testgeneration.engine.ruleengine.Order;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.ConstantValue;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.OrderedValueProviders;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.ShuffledValueProviders;
import org.junit.Test;

public class ShuffledValueProvidersTest {
	private ShuffledValueProviders shuffledProviders(Order order) {
		ConstantValue[] values = new ConstantValue[]{new ConstantValue(1), new ConstantValue(2), new ConstantValue(3), new ConstantValue(4), new ConstantValue(5),
				new ConstantValue(6), new ConstantValue(7), new ConstantValue(8), new ConstantValue(9), new ConstantValue(10)};
		OrderedValueProviders orderedValueProviders = new OrderedValueProviders(values);
		ShuffledValueProviders shuffledValueProviders = new ShuffledValueProviders(orderedValueProviders, order);
		return shuffledValueProviders;
	}

	private void checkShuffled(ShuffledValueProviders shuffledValueProviders) {
		checkShuffled(shuffledValueProviders, 1, 10);
	}

	private void checkShuffled(ShuffledValueProviders shuffledValueProviders,
			final int from, final int to) {
		int movedNumberCounter = 0;
		for(int i = from; i <= to; i++){
			shuffledValueProviders.next();
			if(i != (Integer)shuffledValueProviders.currentProvider().value(null))
				movedNumberCounter++;
		}
		assertThat(movedNumberCounter, not(equalTo(0)));
	}

	private void checkOrdered(ShuffledValueProviders shuffledValueProviders) {
		checkOrdered(shuffledValueProviders, 1, 10);
	}

	private void checkOrdered(ShuffledValueProviders shuffledValueProviders,
			final int from, final int to) {
		for(int i = from; i <= to; i++){
			shuffledValueProviders.next();
			assertThat((Integer)shuffledValueProviders.currentProvider().value(null), equalTo(i));
		}
	}

	private void checkShuffledFixLast(
			ShuffledValueProviders shuffledValueProviders) {
		checkShuffled(shuffledValueProviders, 1, 9);
		checkOrdered(shuffledValueProviders, 10, 10);
	}

	@Test
	public void orderedValuesNeverGetShuffled() {
		ShuffledValueProviders shuffledValueProviders = shuffledProviders(Order.ORDERED);
		checkOrdered(shuffledValueProviders);
		checkOrdered(shuffledValueProviders);
	}

	@Test
	public void shuffledValuesAlwaysGetShuffled() {
		ShuffledValueProviders shuffledValueProviders = shuffledProviders(Order.SHUFFLED);
		checkShuffled(shuffledValueProviders);
		checkShuffled(shuffledValueProviders);
	}

	@Test
	public void shuffledValuesWithFixLastValue() {
		ShuffledValueProviders shuffledValueProviders = shuffledProviders(Order.SHUFFLED_FIX_LAST);
		checkShuffledFixLast(shuffledValueProviders);
		checkShuffledFixLast(shuffledValueProviders);
	}

	@Test
	public void orderedThenShuffledValuesGetShuffledAfterFirstIteration() {
		ShuffledValueProviders shuffledValueProviders = shuffledProviders(Order.ORDERED_THEN_SHUFFLED);
		checkOrdered(shuffledValueProviders);
		checkShuffled(shuffledValueProviders);
	}


	@Test
	public void orderedThenShuffledWithFixLastValue() {
		ShuffledValueProviders shuffledValueProviders = shuffledProviders(Order.ORDERED_THEN_SHUFFLED_FIX_LAST);
		checkOrdered(shuffledValueProviders);
		checkShuffledFixLast(shuffledValueProviders);
	}

}
