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
	private int requiredItemNumber;
	private int achievedRequiredItemNumber;
	private int achievedItemNumber;
		
	public void addReached(final Collection<CoverageEntry> newCoverageEntries) {
		startRound();
		for(CoverageEntry coverageEntry:newCoverageEntries){
			if(coverageEntry.getReason() == CoverageEntry.ANY)
				throw new IllegalArgumentException("no reason in " + coverageEntry);
			final CoverageEntry entryForAnyReason = coverageEntry.forAnyReason();
			if(coveredEntries.containsKey(entryForAnyReason)){
				replaceExpected(entryForAnyReason, coverageEntry);
			}
			addReached(coverageEntry);
		}
	}

	private void replaceExpected(final CoverageEntry oldEntry, CoverageEntry newEntry) {
		removeExpected(oldEntry);
		addExpected(newEntry);
	}

	private void removeExpected(final CoverageEntry entry) {
		coveredEntries.remove(entry);
		requiredItemNumber--;
	}
	
	private CheckList addReached(CoverageEntry coverageEntry) {
		int count = countReached(coverageEntry);
		if(count == 0)
			firstTimeCoveredEntries.add(coverageEntry);
		else
			repeatedlyCoveredEntries.add(coverageEntry);
		setReached(coverageEntry, count + 1);
		return this;
	}


	public CheckList addExpected(CoverageEntry coverageEntry) {
		if(! coveredEntries.containsKey(coverageEntry)) {
			coveredEntries.put(coverageEntry, new CoverageStatus(0));
			requiredItemNumber++;
		}
		return this;
	}
	
	private CheckList setReached(CoverageEntry coverageEntry, int newReachedCount) {
		final CoverageStatus oldCoverageStatus = coveredEntries.get(coverageEntry);
		if(oldCoverageStatus == null)
			coveredEntries.put(coverageEntry, new CoverageStatus(newReachedCount));
		else
			coveredEntries.put(coverageEntry, new CoverageStatus(newReachedCount));
		final boolean containsReason = coverageEntry.getReason() != CoverageEntry.ANY;
		if(oldCoverageStatus != null && oldCoverageStatus.reached == 0 && newReachedCount >= 1) {
			if(containsReason)
				achievedItemNumber++;
			achievedRequiredItemNumber++;
		} 
		else if (oldCoverageStatus == null && newReachedCount == 1 && containsReason)
			achievedItemNumber++;
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

	private void startRound() {
		firstTimeCoveredEntries.clear();
		repeatedlyCoveredEntries.clear();
	}

	public Set<CoverageEntry> firstTimeCoveredEntries() {
		return firstTimeCoveredEntries;
	}

	public Set<CoverageEntry> repeatedlyCoveredEntries() {
		return repeatedlyCoveredEntries;
	}

	public int size() {
		return coveredEntries.size();
	}

	public int requiredItemNumber() {
		return requiredItemNumber;
	}

	public int achievedItemNumber() {
		return achievedItemNumber;
	}

	public int achievedRequiredItemNumber() {
		return achievedRequiredItemNumber;
	}


}
