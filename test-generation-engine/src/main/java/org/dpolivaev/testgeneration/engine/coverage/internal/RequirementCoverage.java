package org.dpolivaev.testgeneration.engine.coverage.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.GoalFunction;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.PropertyAccessor;

public class RequirementCoverage implements GoalFunction{
	private static final String REQUIREMENT_PREFIX = "[";

	@Override
	public Collection<CoverageEntry> check(PropertyContainer propertyContainer) {
    	List<String> sortedRequirementProperties = new PropertyAccessor(propertyContainer).sortedPropertiesForPrefix(REQUIREMENT_PREFIX);
    	Collection<CoverageEntry> coverageEntries = new ArrayList<>();
    	for(String propertyName :  sortedRequirementProperties){
    		final Object value = propertyContainer.get(propertyName);
    		if(value == SpecialValue.UNDEFINED)
    			continue;
    		String requirementId = propertyName.substring(REQUIREMENT_PREFIX.length(), propertyName.length() - REQUIREMENT_PREFIX.length());
			String description = value.toString();
			final CoverageEntry coverageEntry = new CoverageEntry(requirementId, description);
			coverageEntries.add(coverageEntry);
    	}
    	return coverageEntries;
    }
}