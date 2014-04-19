package org.dpolivaev.tsgen.ruleengine.internal;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

public class ConjunctCondition implements Condition {
	private final Condition firstCondition;
	private final Condition secondCondition;

	public ConjunctCondition(Condition firstCondition, Condition secondCondition) {
		this.firstCondition = firstCondition;
		this.secondCondition = secondCondition;
	}

	@Override
	public boolean isSatisfied(PropertyContainer propertyContainer) {
		return firstCondition.isSatisfied(propertyContainer) && secondCondition.isSatisfied(propertyContainer);
	}
}