package org.dpolivaev.tsgen.coverage.code;

import java.util.ArrayList;
import java.util.List;

public class CodeCoverageTracker{

	final private List<String> points;

	public CodeCoverageTracker() {
		points = new ArrayList<>();
	}


	public void clear() {
		points.clear();
	}

	public void reach(String point) {
		points.add(point);
	}


	public List<String> getReached() {
		return points;
	}

}
