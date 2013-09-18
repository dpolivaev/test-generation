package org.dpolivaev.tsgen.coverage;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

public interface Goal {
	public void check(PropertyContainer propertyContainer,
			CoverageTracker coverageTracker);
}
