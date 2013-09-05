package org.dpolivaev.tsgen.coverage;

import java.util.HashMap;
import java.util.Map;

public class CoverageTracker {
	final private Map<CoverageEntry, Integer> coverage = new HashMap<>();
	
	public void add(CoverageEntry coverageEntry) {
		coverage.put(coverageEntry, count(coverageEntry) + 1);
	}

	public int count(CoverageEntry coverageEntry) {
		final Integer oldCount = coverage.get(coverageEntry);
		if(oldCount == null)
			return 0;
		else
			return oldCount;
	}

}
