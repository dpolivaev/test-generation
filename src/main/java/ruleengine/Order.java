package ruleengine;

public enum Order {
	ORDERED, SHUFFLED, ORDERED_THEN_SHUFFLED;
	public static Order defaultOrder = ORDERED_THEN_SHUFFLED;
}