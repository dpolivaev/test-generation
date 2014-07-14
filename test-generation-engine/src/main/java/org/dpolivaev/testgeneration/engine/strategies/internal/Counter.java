package org.dpolivaev.testgeneration.engine.strategies.internal;


public class Counter {

	private int value;
	private int nextIncrement;
	private final int bound;

	public static Counter counter(int begin) {
		return new Counter(begin, Integer.MAX_VALUE, 0);
	}
	
	Counter(int startValue, int bound, int nextIncrement) {
		this.value = startValue;
		this.nextIncrement = nextIncrement;
		this.bound = bound;
	}

	private void addIncrement(int nextIncrement) {
		value += this.nextIncrement;
		if(value >= bound)
			throw new CounterIndexOutOfBoundsException("value " + value + " exceeds bound " + bound);
		this.nextIncrement = nextIncrement;
	}
	
	public Counter next() {
		addIncrement(1);
		return this;
	}

	public Counter nextSubsequence(int length) {
		addIncrement(length);
		return subsequence(0, length);
	}

	public Counter copy() {
		return new Counter(value, bound, nextIncrement);
	}

	public Counter current() {
		return at(0);
	}

	public Counter at(int index) {
		return subsequence(index, 1);
	}

	public Counter subsequence(int begin, int length) {
		int subsequenceBegin = value + begin;
		int subsequenceBound = subsequenceBegin + length;
		if(subsequenceBegin <= 0)
			throw new CounterIndexOutOfBoundsException("non positive new sequence bound " + subsequenceBound);
		if(subsequenceBound > bound)
			throw new CounterIndexOutOfBoundsException("new sequence bound " + subsequenceBound + " greater than own bound " + bound);
		return new Counter(subsequenceBegin, subsequenceBound, 0);
	}

	public Counter subsequence(int begin) {
		int subsequenceBegin = value + begin;
		return subsequence(begin, bound-subsequenceBegin);
	}

	public int getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bound;
		result = prime * result + nextIncrement;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Counter other = (Counter) obj;
		if (bound != other.bound)
			return false;
		if (nextIncrement != other.nextIncrement)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	
}
