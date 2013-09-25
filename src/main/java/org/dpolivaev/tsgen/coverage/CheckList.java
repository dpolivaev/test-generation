package org.dpolivaev.tsgen.coverage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CheckList{
	final private Map<CoverageEntry, Integer> items = new HashMap<>();
		
	public CheckList add(CoverageEntry coverageEntry) {
		int count = count(coverageEntry) + 1;
		put(coverageEntry, count);
		return this;
	}

	public CheckList put(CoverageEntry coverageEntry, int count) {
		items.put(coverageEntry, count);
		return this;
	}

	public int count(CoverageEntry coverageEntry) {
		final Integer oldCount = items.get(coverageEntry);
		if(oldCount == null)
			return 0;
		else
			return oldCount;
	}

	public Set<CoverageEntry> items() {
		return items.keySet();
	}
}
