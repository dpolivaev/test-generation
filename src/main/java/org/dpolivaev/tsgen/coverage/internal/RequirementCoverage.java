package org.dpolivaev.tsgen.coverage.internal;

import java.util.List;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.OpenGoal;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.internal.PropertyAccessor;

public class RequirementCoverage implements OpenGoal{
	private static final String REQUIREMENT_PREFIX = "requirement.";

	@Override
	public void check(PropertyContainer propertyContainer,
			CoverageTracker coverageTracker) {
    	List<String> sortedRequirementProperties = new PropertyAccessor(propertyContainer).sortedPropertiesForPrefix(REQUIREMENT_PREFIX);
    	for(String propertyName :  sortedRequirementProperties){
    		final Object value = propertyContainer.get(propertyName);
    		if(value == SpecialValue.UNDEFINED)
    			continue;
    		@SuppressWarnings("unchecked")
			List<Object> coverageItems = (List<Object>) value;
    		String requirementId = propertyName.substring(REQUIREMENT_PREFIX.length());
    		for(Object item : coverageItems){
    			String description = item.toString();
    			final CoverageEntry coverageEntry = new CoverageEntry(requirementId, description);
				coverageTracker.add(coverageEntry);
    		}
    	}
    }
}