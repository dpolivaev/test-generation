package org.dpolivaev.tsgen.coverage.internal;

import java.util.List;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ScriptProducer;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.internal.PropertyAccessor;

public class CoverageProducer implements ScriptProducer{
	private static final String REQUIREMENT_PREFIX = "requirement.";
	final private ScriptProducer delegate;
	final private CoverageTracker coverageTracker;

	public CoverageProducer(ScriptProducer delegate, CoverageTracker coverageTracker) {
		super();
		this.delegate = delegate;
		this.coverageTracker = coverageTracker;
	}

	public void makeScriptFor(PropertyContainer propertyContainer) {
		coverageTracker.checkGoals(propertyContainer);
		addCoverage(propertyContainer);
		delegate.makeScriptFor(propertyContainer);
	}
	
    private void addCoverage(PropertyContainer propertyContainer) {
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