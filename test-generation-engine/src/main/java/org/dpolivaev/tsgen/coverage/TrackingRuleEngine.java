package org.dpolivaev.tsgen.coverage;

import org.dpolivaev.tsgen.ruleengine.RuleEngine;

public class TrackingRuleEngine extends RuleEngine implements CoverageTrackerEnabler {
	final private CoverageTracker coverageTracker;
	public TrackingRuleEngine(CoverageTracker coverageTracker) {
		super();
		this.coverageTracker = coverageTracker;
	}

	@Override
	public void startTrace() {
		coverageTracker.startTrace();
	}

	@Override
	public void stopTrace() {
		coverageTracker.stopTrace();
	}

}
