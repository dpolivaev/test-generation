package org.dpolivaev.tsgen.scriptwriter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;

public class RequirementChecker implements PropertyHandler{
	final private Collection<CoverageEntry> items;

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
	
	public void registerBy(StrategyRunner runner){
		if(! items.isEmpty()){
			runner.addPropertyHandler(this);
			runner.addRequiredItems(items);
		}
	}
}
