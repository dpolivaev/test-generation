package org.dpolivaev.tsgen.coverage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CheckList{
	final private Map<CoverageEntry, Integer> items = new HashMap<>();
		
	final private Collection<Goal> goals = new ArrayList<>();
	
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

	public void add(Goal goal) {
		goals.add(goal);
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
