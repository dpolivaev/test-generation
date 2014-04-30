package org.dpolivaev.testgeneration.engine.coverage.internal;

import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyCombinationHandler;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;

public class CodeCoverageResetter extends PropertyCombinationHandler{

	final private CoverageTracker coverageTracker;

	public CodeCoverageResetter(CoverageTracker coverageTracker) {
		this.coverageTracker = coverageTracker;
	}

	@Override
	public void handlePropertyCombination(PropertyContainer propertyContainer) {
		coverageTracker.clear();
	}
}
