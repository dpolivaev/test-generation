package ruleengine;

import ruleengine.impl.TrueCondition;

public interface Condition {

	public final static Condition TRUE = new TrueCondition();

	boolean isSatisfied(PropertyContainer propertyContainer);

}
