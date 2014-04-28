package org.dpolivaev.tsgen.utils;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.dpolivaev.tsgen.utils.internal.LinkedMap;
import org.junit.Before;
import org.junit.Test;

public class LinkedMapTest {
	private void  assertValuesEqualTo(Integer... values) {
		ArrayList<Integer> actualValues = new ArrayList<>(values.length);
		for (Integer item : linkedMap.iterable())
			actualValues.add(item);
		assertThat(actualValues, equalTo(asList(values)));
	}

	private void add(Integer value) {
		linkedMap.add(value, value);
	}

	LinkedMap<Integer, Integer> linkedMap;
	@Before
	public void setup(){
		 linkedMap = new LinkedMap<>();
	}

	@Test
	public void oneValue() {
		add(1);
		assertValuesEqualTo(1);
	}

	@Test
	public void twoValues() {
		add(2);
		add(1);
		assertValuesEqualTo(2, 1);
	}


	@Test
	public void threeValues() {
		add(3);
		add(2);
		add(1);
		assertValuesEqualTo(3, 2, 1);
	}

	@Test
	public void threeValuesRemoveFirst() {
		add(3);
		add(2);
		add(1);
		linkedMap.remove(3);
		assertValuesEqualTo(2, 1);
	}

	@Test
	public void threeValuesRemoveMiddle() {
		add(3);
		add(2);
		add(1);
		linkedMap.remove(2);
		assertValuesEqualTo(3, 1);
	}

	@Test
	public void threeValuesRemoveLast() {
		add(3);
		add(2);
		add(1);
		linkedMap.remove(1);
		assertValuesEqualTo(3, 2);
	}

	@Test
	public void addAfterValue() {
		add(1);
		linkedMap.insertAfter(1, 2, 2);
		assertValuesEqualTo(1, 2);
	}

	@Test
	public void addBeforeValue() {
		add(1);
		add(3);
		linkedMap.insertAfter(1, 2, 2);
		assertValuesEqualTo(1, 2, 3);
	}

	@Test(expected = NullPointerException.class)
	public void putNotExisting() {
		linkedMap.put(1, 1);
	}

	@Test(expected = NullPointerException.class)
	public void addAfterNotExisting() {
		linkedMap.insertAfter(1, 2, 2);
	}

	@Test(expected = IllegalStateException.class)
	public void addExisting() {
		add(1);
		linkedMap.insertAfter(1, 1, 1);
	}


	@Test(expected = IllegalStateException.class)
	public void addExistingInTheMiddle() {
		add(1);
		add(2);
		linkedMap.insertAfter(1, 1, 1);
	}

}
