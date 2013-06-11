package ruleengine;

public interface Condition {

	public final static Condition TRUE = new TrueCondition();

	boolean isSatisfied();

}
