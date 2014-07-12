package org.dpolivaev.testgeneration.engine.strategies;

public class PropertyCounter {

	private int value;
	private int increment;
	private final int bound;
	final private String basePropertyName;

	static public PropertyCounter propertyCounter(String name) {
		return propertyCounter(name, 1);
	}

	static public PropertyCounter propertyCounter(String basePropertyName, int startValue) {
		return new PropertyCounter(basePropertyName, startValue, Integer.MAX_VALUE, 0);
	}

	private PropertyCounter(String basePropertyName, int startValue, int bound, int increment) {
		this.basePropertyName = basePropertyName;
		this.value = startValue;
		this.increment = increment;
		this.bound = bound;
	}

	public PropertyCounter next() {
		addIncrement(1);
		return this;
	}

	public PropertyCounter copy() {
		return new PropertyCounter(basePropertyName, value, bound, increment);
	}

	public PropertyCounter current() {
		return at(0);
	}

	public PropertyCounter at(int index) {
		return subsequence(index, 1);
	}

	public PropertyCounter subsequence(int begin, int length) {
		if(begin <= 0 || begin >= value + increment ||length > increment)
			throw new CounterIndexOutOfBoundsException();
		return new PropertyCounter(basePropertyName, value + begin, value + begin + length, 0);
	}

	private void addIncrement(int increment) {
		value += this.increment;
		if(value >= bound)
			throw new CounterIndexOutOfBoundsException();
		this.increment = increment;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return basePropertyName + value;
	}

	public PropertyCounter nextSubsequence(int increment) {
		addIncrement(increment);
		return new PropertyCounter(basePropertyName, value, value + increment, 0);
	}
}
