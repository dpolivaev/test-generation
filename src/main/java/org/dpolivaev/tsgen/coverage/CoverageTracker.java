package org.dpolivaev.tsgen.coverage;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class CoverageTracker {
	final private Set<CoverageEntry> firstTimeCoveredGoals = new LinkedHashSet<>();
	final private Set<CoverageEntry> repeatedlyCoveredGoals = new LinkedHashSet<>();
	final private CheckList checkList; 
		
	public CoverageTracker(CheckList checkList) {
		this.checkList = checkList != null ? checkList : new CheckList();
	}

	public CoverageTracker() {
		this(null);
	}

	public void add(CoverageEntry coverageEntry) {
		int count = count(coverageEntry);
		if(count == 0)
			firstTimeCoveredGoals.add(coverageEntry);
		else
			repeatedlyCoveredGoals.add(coverageEntry);
		checkList.addReached(coverageEntry);
	}

	public int count(CoverageEntry coverageEntry) {
		return checkList.countReached(coverageEntry);
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

	public void addAllEntries(Collection<CoverageEntry> newCoverageEntries) {
		for(CoverageEntry coverageEntry:newCoverageEntries)
			add(coverageEntry);
	}

	public void addRequiredEntries(Collection<CoverageEntry> newCoverageEntries) {
		for(CoverageEntry coverageEntry:newCoverageEntries)
			if(checkList.isRequired(coverageEntry))
				add(coverageEntry);
		
	}
}
