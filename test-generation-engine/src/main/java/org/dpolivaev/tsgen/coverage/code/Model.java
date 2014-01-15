package org.dpolivaev.tsgen.coverage.code;

import java.util.List;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;

public interface Model {
	CoverageTracker getCoverageTracker();
	List<CoverageEntry> getRequiredItems();
}
