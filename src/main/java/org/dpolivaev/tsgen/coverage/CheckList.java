package org.dpolivaev.tsgen.coverage;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CheckList{
	final private Set<CoverageEntry> firstTimeCoveredEntries = new LinkedHashSet<>();
	final private Set<CoverageEntry> repeatedlyCoveredEntries = new LinkedHashSet<>();
		
	final private Map<CoverageEntry, CoverageStatus> coveredEntries = new HashMap<>();
		
	public CheckList addReached(CoverageEntry coverageEntry) {
		int count = countReached(coverageEntry);
		if(count == 0)
			firstTimeCoveredEntries.add(coverageEntry);
		else
			repeatedlyCoveredEntries.add(coverageEntry);
		setReached(coverageEntry, count + 1);
		return this;
	}


	public CheckList setExpected(CoverageEntry coverageEntry, int expected) {
		final CoverageStatus coverageStatus = coveredEntries.get(coverageEntry);
		if(coverageStatus == null)
			coveredEntries.put(coverageEntry, new CoverageStatus(expected, 0));
		else
			coveredEntries.put(coverageEntry, new CoverageStatus(expected, coverageStatus.reached));
		return this;
	}
	
	public CheckList setReached(CoverageEntry coverageEntry, int count) {
		final CoverageStatus coverageStatus = coveredEntries.get(coverageEntry);
		if(coverageStatus == null)
			coveredEntries.put(coverageEntry, new CoverageStatus(0, count));
		else
			coveredEntries.put(coverageEntry, new CoverageStatus(coverageStatus.required, count));
		return this;
	}

	public int countReached(CoverageEntry coverageEntry) {
		final CoverageStatus coverageStatus = coveredEntries.get(coverageEntry);
		if(coverageStatus == null)
			return 0;
		else
			return coverageStatus.reached;
	}

	public Set<Entry<CoverageEntry, CoverageStatus>> coveredEntries() {
		return coveredEntries.entrySet();
	}

	public boolean isRequired(CoverageEntry coverageEntry) {
		return coveredEntries.containsKey(coverageEntry);
	}
	
	public void startRound() {
		firstTimeCoveredEntries.clear();
		repeatedlyCoveredEntries.clear();
	}

	public Set<CoverageEntry> firstTimeCoveredEntries() {
		return firstTimeCoveredEntries;
	}

	public Set<CoverageEntry> repeatedlyCoveredEntries() {
		return repeatedlyCoveredEntries;
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
