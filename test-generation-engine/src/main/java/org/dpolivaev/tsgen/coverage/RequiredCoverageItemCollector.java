package org.dpolivaev.tsgen.coverage;

import java.util.Collection;

public interface RequiredCoverageItemCollector {

	void addRequiredItems(Collection<CoverageEntry> items);

}
