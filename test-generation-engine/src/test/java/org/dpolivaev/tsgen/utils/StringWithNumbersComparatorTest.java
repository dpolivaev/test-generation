package org.dpolivaev.tsgen.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.dpolivaev.tsgen.utils.internal.StringWithNumbersComparator;
import org.junit.Test;

public class StringWithNumbersComparatorTest {
	@Test
	public void comparesStrings() {
		assertThat(StringWithNumbersComparator.compare("A", "B"), equalTo("A".compareTo("B")));
	}

	@Test
	public void comparesNumbers() {
		assertThat(StringWithNumbersComparator.compare("10", "2"), equalTo(Integer.valueOf(10).compareTo(2)));
	}

	@Test
	public void comparesDifferentStringsFollowedByNumbers() {
		assertThat(StringWithNumbersComparator.compare("A10", "B2"), equalTo("A".compareTo("B")));
	}
	@Test
	public void comparesNumbersFollowedByStrings() {
		assertThat(StringWithNumbersComparator.compare("10A", "10B"), equalTo("A".compareTo("B")));
	}


	@Test
	public void comparesNumbersStringsNumbers() {
		assertThat(StringWithNumbersComparator.compare("5A10", "5A2"),  equalTo(Integer.valueOf(10).compareTo(2)));
	}
}
