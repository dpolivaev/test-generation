package org.dpolivaev.tsgen.ruleengine;

import org.dpolivaev.tsgen.coverage.CoverageTracker;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public interface ScriptWriter {
	void createScriptFor(PropertyContainer propertyContainer, CoverageTracker coverage);
}
