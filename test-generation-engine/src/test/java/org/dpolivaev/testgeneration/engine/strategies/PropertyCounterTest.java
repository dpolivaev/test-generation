package org.dpolivaev.testgeneration.engine.strategies;

import static org.dpolivaev.testgeneration.engine.strategies.PropertyCounter.propertyCounter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;

public class PropertyCounterTest {

	@Test
	public void nextReturnsCounterItself() {
		PropertyCounter counter = propertyCounter("name", 1);
		PropertyCounter same = counter.next();
		assertThat(same, equalTo(counter));
	}
	
	@Test
	public void returnsCurrentCounterValue() {
		PropertyCounter counter = propertyCounter("name", 1);
		int value = counter.getValue();
		assertThat(value, equalTo(1));
	}
	
	@Test
	public void returnsStringAsNameAndValue() {
		PropertyCounter counter = propertyCounter("name", 1);
		String string = counter.toString();
		assertThat(string, equalTo("name1"));
	}
	
	@Test
	public void nextChangesIncrementAndValue() {
		PropertyCounter counter = propertyCounter("name", 1);
		counter.next().next();
		int value = counter.getValue();
		assertThat(value, equalTo(2));
	}
	
	@Test
	public void subsequence(){
		PropertyCounter counter = propertyCounter("name", 1);
		PropertyCounter subsequence = counter.nextSubsequence(1);
		assertThat(subsequence.getValue(), equalTo(1));
	}
	
	@Test(expected = CounterIndexOutOfBoundsException.class)
	public void subsequenceIsLimited(){
		PropertyCounter counter = propertyCounter("name", 1);
		PropertyCounter subsequence = counter.nextSubsequence(0);
		subsequence.next();
	}
}
