package org.dpolivaev.tsgen.coverage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CheckList{
	final private Map<CoverageEntry, Integer> items = new HashMap<>();
		
	public void add(CoverageEntry coverageEntry) {
		int count = count(coverageEntry) + 1;
		put(coverageEntry, count);
	}

	public void put(CoverageEntry coverageEntry, int count) {
		items.put(coverageEntry, count);
	}

	public int count(CoverageEntry coverageEntry) {
		final Integer oldCount = items.get(coverageEntry);
		if(oldCount == null)
			return 0;
		else
			return oldCount;
	}

	public boolean contains(CheckList that) {
		for(CoverageEntry entry : that.items()){
			if(count(entry) < that.count(entry))
				return false;
		}
		return true;
	}

	public Set<CoverageEntry> items() {
		return items.keySet();
	}
}
