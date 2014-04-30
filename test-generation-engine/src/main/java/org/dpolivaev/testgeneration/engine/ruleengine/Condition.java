package org.dpolivaev.testgeneration.engine.ruleengine;

import org.dpolivaev.testgeneration.engine.ruleengine.internal.TrueCondition;

public interface Condition {

	public final static Condition TRUE = new TrueCondition();

	boolean isSatisfied(PropertyContainer propertyContainer);

}
