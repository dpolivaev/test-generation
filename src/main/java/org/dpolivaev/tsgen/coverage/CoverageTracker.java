package org.dpolivaev.tsgen.coverage;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class CoverageTracker {
	final private Set<CoverageEntry> firstTimeCoveredGoals = new LinkedHashSet<>();
	final private Set<CoverageEntry> repeatedlyCoveredGoals = new LinkedHashSet<>();
	final private CheckList completeCoverage= new CheckList(); 
		
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

	public void startRound() {
		firstTimeCoveredGoals.clear();
		repeatedlyCoveredGoals.clear();
	}

	public Set<CoverageEntry> firstTimeCoveredGoals() {
		return firstTimeCoveredGoals;
	}

	public Set<CoverageEntry> repeatedlyCoveredGoals() {
		return repeatedlyCoveredGoals;
	}

	public void addAll(Collection<CoverageEntry> newCoverageEntries) {
		for(CoverageEntry coverageEntry:newCoverageEntries)
			add(coverageEntry);
	}
}
