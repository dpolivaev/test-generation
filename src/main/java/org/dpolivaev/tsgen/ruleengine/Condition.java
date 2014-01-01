package org.dpolivaev.tsgen.ruleengine;

import org.dpolivaev.tsgen.ruleengine.internal.TrueCondition;

public interface Condition {

	public final static Condition TRUE = new TrueCondition();

	boolean isSatisfied(PropertyContainer propertyContainer);

}
