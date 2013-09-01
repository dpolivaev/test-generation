package ruleengine.impl;

import ruleengine.Condition;
import ruleengine.PropertyContainer;

public class TrueCondition implements Condition {
	@Override
	public boolean isSatisfied(PropertyContainer propertyContainer) {
		return true;
	}
}