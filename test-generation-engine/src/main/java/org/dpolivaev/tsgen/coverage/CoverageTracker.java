package org.dpolivaev.tsgen.coverage;

import java.util.ArrayList;
import java.util.List;

public class CoverageTracker{

	final private List<CoverageEntry> points;
	private boolean traceActive;

	public CoverageTracker() {
		points = new ArrayList<>();
	}


	public void clear() {
		points.clear();
	}

	public void reach(String name, String reason) {
		if(traceActive)
			points.add(new CoverageEntry(name, reason));
	}


	public List<CoverageEntry> getReached() {
		return points;
	}


	public void startTrace() {
		this.traceActive = true;
	}


	public void stopTrace() {
		this.traceActive = false;
	}

}
