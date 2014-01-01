package org.dpolivaev.tsgen.coverage.code;

import java.util.ArrayList;
import java.util.List;

import org.dpolivaev.tsgen.coverage.CoverageEntry;

public class CodeCoverageTracker{

	final private List<CoverageEntry> points;

	public CodeCoverageTracker() {
		points = new ArrayList<>();
	}


	public void clear() {
		points.clear();
	}

	public void reach(String name, String reason) {
		points.add(new CoverageEntry(name, reason));
	}


	public List<CoverageEntry> getReached() {
		return points;
	}

}
