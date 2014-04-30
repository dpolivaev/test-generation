package org.dpolivaev.testgeneration.engine.coverage.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequiredCoverageItemCollector;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;

public class RequirementChecker implements PropertyHandler{
	final private Set<CoverageEntry> items;

	public RequirementChecker(){
		this.items = new HashSet<CoverageEntry>();
	}
	
	public void addItems(Collection<CoverageEntry> items){
		this.items.addAll(items);
	}

	public void addItems(CoverageEntry... items){
		this.items.addAll(Arrays.asList(items));
	}

	@Override
	public void generationStarted(PropertyContainer propertyContainer) {
		
	}

	@Override
	public void handlePropertyCombination(PropertyContainer propertyContainer) {
		for(CoverageEntry item : items)
			propertyContainer.get(item.getName());
	}

	@Override
	public void generationFinished() {
		
	}
	
	public void registerRequiredItems(RequiredCoverageItemCollector requiredCoverageItemCollector){
		if(! items.isEmpty()){
			requiredCoverageItemCollector.registerRequiredItems(items);
		}
	}
	
	public RequirementChecker with(RequirementChecker anotherChecker){
		final RequirementChecker requirementChecker = new RequirementChecker();
		requirementChecker.addItems(items);
		requirementChecker.addItems(anotherChecker.items);
		return requirementChecker;
	}
}
