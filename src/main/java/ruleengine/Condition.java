package ruleengine;

import ruleengine.internal.TrueCondition;

public interface Condition {

	public final static Condition TRUE = new TrueCondition();

	boolean isSatisfied(PropertyContainer propertyContainer);

}
