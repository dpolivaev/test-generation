package org.dpolivaev.tsgen.coverage;

import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

public interface GoalFunction {

	Collection<CoverageEntry> check(PropertyContainer propertyContainer);

}
