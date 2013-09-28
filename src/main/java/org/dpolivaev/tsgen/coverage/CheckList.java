package org.dpolivaev.tsgen.coverage;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CheckList{
	final private Set<CoverageEntry> firstTimeCoveredGoals = new LinkedHashSet<>();
	final private Set<CoverageEntry> repeatedlyCoveredGoals = new LinkedHashSet<>();
		
	final private Map<CoverageEntry, CoverageStatus> items = new HashMap<>();
		
	public CheckList addReached(CoverageEntry coverageEntry) {
		int count = countReached(coverageEntry);
		if(count == 0)
			firstTimeCoveredGoals.add(coverageEntry);
		else
			repeatedlyCoveredGoals.add(coverageEntry);
		setReached(coverageEntry, count + 1);
		return this;
	}


	public CheckList setExpected(CoverageEntry coverageEntry, int expected) {
		final CoverageStatus coverageStatus = items.get(coverageEntry);
		if(coverageStatus == null)
			items.put(coverageEntry, new CoverageStatus(expected, 0));
		else
			items.put(coverageEntry, new CoverageStatus(expected, coverageStatus.reached));
		return this;
	}
	
	public CheckList setReached(CoverageEntry coverageEntry, int count) {
		final CoverageStatus coverageStatus = items.get(coverageEntry);
		if(coverageStatus == null)
			items.put(coverageEntry, new CoverageStatus(0, count));
		else
			items.put(coverageEntry, new CoverageStatus(coverageStatus.required, count));
		return this;
	}

	public int countReached(CoverageEntry coverageEntry) {
		final CoverageStatus coverageStatus = items.get(coverageEntry);
		if(coverageStatus == null)
			return 0;
		else
			return coverageStatus.reached;
	}

	public Set<CoverageEntry> items() {
		return items.keySet();
	}

	public boolean isRequired(CoverageEntry coverageEntry) {
		return items.containsKey(coverageEntry);
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
			addReached(coverageEntry);
	}

	public void addRequiredEntries(Collection<CoverageEntry> newCoverageEntries) {
		for(CoverageEntry coverageEntry:newCoverageEntries)
			if(isRequired(coverageEntry))
				addReached(coverageEntry);
	}
	
}
