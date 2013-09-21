package org.dpolivaev.tsgen.ruleengine;

import java.util.Collection;

import org.dpolivaev.tsgen.coverage.Goal;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public interface PropertyHandler {
	void handlePropertyCombination(PropertyContainer propertyContainer, Collection<Goal> goals);
}
