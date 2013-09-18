package org.dpolivaev.tsgen.coverage.internal;

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ScriptProducer;

public class CoverageProducer implements ScriptProducer{
	final private ScriptProducer delegate;
	final private CoverageTracker coverageTracker;

	public CoverageProducer(ScriptProducer delegate, CoverageTracker coverageTracker) {
		super();
		this.delegate = delegate;
		this.coverageTracker = coverageTracker;
	}

	public void makeScriptFor(PropertyContainer propertyContainer) {
		coverageTracker.checkGoals(propertyContainer);
		delegate.makeScriptFor(propertyContainer);
	}
	
}