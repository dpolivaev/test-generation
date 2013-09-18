package org.dpolivaev.tsgen.coverage;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

public class Goal {

	final private String name;
	private ValueProvider goalFunction;
	private Object[] expectedValues;

	public Goal(String name, ValueProvider goalFunction, Object... expectedValues) {
		this.name = name;
		this.goalFunction = goalFunction;
		this.expectedValues = expectedValues;
	}

	public void check(PropertyContainer propertyContainer,
			CoverageTracker coverageTracker) {
		coverageTracker.add(new CoverageEntry(name, goalFunction.value(propertyContainer)));
	}

}
