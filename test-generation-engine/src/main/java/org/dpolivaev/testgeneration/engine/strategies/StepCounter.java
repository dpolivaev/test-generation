package org.dpolivaev.testgeneration.engine.strategies;

import static org.dpolivaev.testgeneration.engine.strategies.internal.Counter.counter;

import org.dpolivaev.testgeneration.engine.strategies.internal.Counter;

public class StepCounter {

	final private String basePropertyName;
	final private Counter counter;
	
	

	StepCounter(String basePropertyName, Counter counter) {
		super();
		this.basePropertyName = basePropertyName;
		this.counter = counter;
	}

	static public StepCounter stepCounter(String basePropertyName) {
		return new StepCounter(basePropertyName, counter(1));
	}

	public StepCounter next() {
		counter.next();
		return this;
	}

	public StepCounter nextSubsequence(int length) {
		return withCounter(counter.nextSubsequence(length));
	}

	public StepCounter subsequence(int startValue) {
		return withCounter(counter.subsequence(startValue));
	}

	public StepCounter copy() {
		return withCounter(counter.copy());
	}

	public StepCounter current() {
		return withCounter(counter.current());
	}

	public StepCounter at(int index) {
		return withCounter(counter.at(index));
	}

	public StepCounter subsequence(int begin, int length) {
		return withCounter(counter.subsequence(begin, length));
	}

	private StepCounter withCounter(Counter counter) {
		return new StepCounter(basePropertyName, counter);
	}

	public int getValue() {
		return counter.getValue();
	}

	@Override
	public String toString() {
		return new StringBuilder(basePropertyName).append('#').append(getValue()).toString();
	}
	
	
}
