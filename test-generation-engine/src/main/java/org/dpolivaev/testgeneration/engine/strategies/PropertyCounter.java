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
		return new PropertyCounter(basePropertyName, startValue, Integer.MAX_VALUE);
	}

	private PropertyCounter(String basePropertyName, int startValue, int bound) {
		this.basePropertyName = basePropertyName;
		this.value = startValue;
		this.increment = 0;
		this.bound = bound;
	}

	public PropertyCounter next() {
		next(1);
		return this;
	}

	private void next(int increment) {
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
		next(increment);
		return new PropertyCounter(basePropertyName, value, value + increment);
	}
}
