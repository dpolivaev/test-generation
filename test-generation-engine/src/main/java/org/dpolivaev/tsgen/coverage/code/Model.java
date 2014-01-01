package org.dpolivaev.tsgen.coverage.code;

import java.util.List;

public interface Model {
	String getName();
	CodeCoverageTracker getCodeCoverageTracker();
	List<String> getRequiredItems();
}
