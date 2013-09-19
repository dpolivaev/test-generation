package org.dpolivaev.tsgen.coverage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

public class CoverageTracker {
	final private Set<CoverageEntry> firstTimeCoveredGoals = new LinkedHashSet<>();
	final private Set<CoverageEntry> repeatedlyCoveredGoals = new LinkedHashSet<>();
	final private CheckList completeCoverage= new CheckList(); 
		
	final private Collection<Goal> goals = new ArrayList<>();
	
	public void add(CoverageEntry coverageEntry) {
		int count = count(coverageEntry);
		if(count == 0)
			firstTimeCoveredGoals.add(coverageEntry);
		else
			repeatedlyCoveredGoals.add(coverageEntry);
		completeCoverage.add(coverageEntry);
	}

	public int count(CoverageEntry coverageEntry) {
		return completeCoverage.count(coverageEntry);
	}

	public void add(Goal goal) {
		goals.add(goal);
	}

	public void checkGoals(PropertyContainer propertyContainer) {
		firstTimeCoveredGoals.clear();
		repeatedlyCoveredGoals.clear();
		for(Goal goal : goals){
			goal.check(propertyContainer, this);
		}
	}

	public Set<CoverageEntry> firstTimeCoveredGoals() {
		return firstTimeCoveredGoals;
	}

	public Set<CoverageEntry> repeatedlyCoveredGoals() {
		return repeatedlyCoveredGoals;
	}
}
