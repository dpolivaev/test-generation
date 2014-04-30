package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;

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