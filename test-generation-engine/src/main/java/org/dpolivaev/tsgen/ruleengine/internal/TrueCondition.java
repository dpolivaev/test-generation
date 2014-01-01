package org.dpolivaev.tsgen.ruleengine.internal;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

public class TrueCondition implements Condition {
	@Override
	public boolean isSatisfied(PropertyContainer propertyContainer) {
		return true;
	}
}