package org.dpolivaev.tsgen.coverage.internal;

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyCombinationHandler;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

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
