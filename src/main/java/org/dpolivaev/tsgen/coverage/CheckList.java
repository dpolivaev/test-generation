package org.dpolivaev.tsgen.coverage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CheckList{
	final private Map<CoverageEntry, CoverageStatus> items = new HashMap<>();
		
	public CheckList addReached(CoverageEntry coverageEntry) {
		int count = countReached(coverageEntry) + 1;
		setReached(coverageEntry, count);
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
}
