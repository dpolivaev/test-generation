package org.dpolivaev.tsgen.coverage.internal;

import org.dpolivaev.tsgen.coverage.code.CodeCoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;

public class CodeCoverageResetter implements PropertyHandler{

	final private CodeCoverageTracker codeCoverageTracker;

	public CodeCoverageResetter(CodeCoverageTracker codeCoverageTracker) {
		this.codeCoverageTracker = codeCoverageTracker;
	}

	@Override
	public void handlePropertyCombination(PropertyContainer propertyContainer) {
		codeCoverageTracker.clear();
	}

}
