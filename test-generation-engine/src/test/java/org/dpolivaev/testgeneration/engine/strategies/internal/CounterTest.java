package org.dpolivaev.testgeneration.engine.strategies.internal;

import static org.dpolivaev.testgeneration.engine.strategies.internal.Counter.counter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CounterTest {
	@Test
	public void nextReturnsCounterItself() {
		Counter counter = counter(1);
		Counter same = counter.next();
		assertThat(same, equalTo(counter));
	}
	
	@Test
	public void returnsCurrentCounterValue() {
		Counter counter = counter(1);
		counter.next();
		int value = counter.getValue();
		assertThat(value, equalTo(1));
	}
	
	@Test
	public void nextChangesIncrementAndValue() {
		Counter counter = counter(1);
		counter.next().next();
		int value = counter.getValue();
		assertThat(value, equalTo(2));
	}
	
	@Test
	public void subsequence(){
		Counter counter = counter(1);
		Counter subsequence = counter.nextSubsequence(1);
		subsequence.next();
		assertThat(subsequence.getValue(), equalTo(1));
	}
	
	@Test(expected = CounterIndexOutOfBoundsException.class)
	public void subsequenceIsLimited(){
		Counter counter = counter(1);
		Counter subsequence = counter.nextSubsequence(0);
		subsequence.next();
	}
}