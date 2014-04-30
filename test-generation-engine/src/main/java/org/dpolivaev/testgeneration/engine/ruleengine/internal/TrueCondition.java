package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;

public class TrueCondition implements Condition {
	@Override
	public boolean isSatisfied(PropertyContainer propertyContainer) {
		return true;
	}
}