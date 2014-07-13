package org.dpolivaev.testgeneration.engine.strategies;

import static org.dpolivaev.testgeneration.engine.strategies.StepCounter.stepCounter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;

public class StepCounterTest {

	@Test
	public void returnsStringAsNameAndValue() {
		StepCounter counter = stepCounter("name");
		counter.next();
		String string = counter.toString();
		assertThat(string, equalTo("name#1"));
	}
	
}