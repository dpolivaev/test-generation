package org.dpolivaev.tsgen.coverage.internal;

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ScriptWriter;

public class CoverageProducer implements ScriptWriter{
	final private ScriptWriter delegate;
	final private CoverageTracker coverageTracker;

	public CoverageProducer(ScriptWriter delegate, CoverageTracker coverageTracker) {
		super();
		this.delegate = delegate;
		this.coverageTracker = coverageTracker;
	}

	public void createScriptFor(PropertyContainer propertyContainer) {
		coverageTracker.checkGoals(propertyContainer);
		delegate.createScriptFor(propertyContainer);
	}
	
}