package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;

public interface AssignmentFilter {

	public abstract boolean matches(Assignment assignment);

}