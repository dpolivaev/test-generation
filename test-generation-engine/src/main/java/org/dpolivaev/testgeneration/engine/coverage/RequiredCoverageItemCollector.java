package org.dpolivaev.testgeneration.engine.coverage;

import java.util.Collection;

public interface RequiredCoverageItemCollector {

	void registerRequiredItems(Collection<CoverageEntry> items);

}
