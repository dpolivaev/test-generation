package org.dpolivaev.tsgen.coverage.internal;

import org.dpolivaev.tsgen.coverage.code.CodeCoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyCombinationHandler;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

public class CodeCoverageResetter extends PropertyCombinationHandler{

	final private CodeCoverageTracker codeCoverageTracker;

	public CodeCoverageResetter(CodeCoverageTracker codeCoverageTracker) {
		this.codeCoverageTracker = codeCoverageTracker;
	}

	@Override
	public void handlePropertyCombination(PropertyContainer propertyContainer) {
		codeCoverageTracker.clear();
	}
}
