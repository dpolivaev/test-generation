package org.dpolivaev.testgeneration.engine.ruleengine;

public enum Order {
	ORDERED, SHUFFLED, ORDERED_THEN_SHUFFLED, SHUFFLED_KEEP_LAST_ELEMENT_POSITION(1), ORDERED_THEN_SHUFFLED_KEEP_LAST_ELEMENT_POSITION(1);
	public static Order defaultOrder = ORDERED_THEN_SHUFFLED;

	final private int fixElementCount;
	
	private Order() {
		this(0);
	}
	private Order(int fixElementCount) {
		this.fixElementCount = fixElementCount;
	}

	public int permutationSize(int valueNumber) {
		return valueNumber - fixElementCount;
	}
}