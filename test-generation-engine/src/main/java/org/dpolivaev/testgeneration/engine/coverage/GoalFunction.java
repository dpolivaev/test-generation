package org.dpolivaev.testgeneration.engine.coverage;

import java.util.Collection;

import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;

public interface GoalFunction {

	Collection<CoverageEntry> check(PropertyContainer propertyContainer);

}
