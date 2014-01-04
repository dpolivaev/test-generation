package org.dpolivaev.tsgen.coverage.code;

import java.util.List;

import org.dpolivaev.tsgen.coverage.CoverageEntry;

public interface Model {
	CodeCoverageTracker getCodeCoverageTracker();
	List<CoverageEntry> getRequiredItems();
}
