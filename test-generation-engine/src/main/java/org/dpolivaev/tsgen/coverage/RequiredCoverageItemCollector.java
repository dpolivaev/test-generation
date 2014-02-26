package org.dpolivaev.tsgen.coverage;

import java.util.Collection;

public interface RequiredCoverageItemCollector {

	void registerRequiredItems(Collection<CoverageEntry> items);

}
