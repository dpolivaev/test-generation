package org.dpolivaev.tsgen.coverage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;

public class CoverageTest {

	@Test
	public void coverageEntryEquality() {
		final CoverageEntry coverageEntry1 = new CoverageEntry("req", "description");
		final CoverageEntry coverageEntry2 = new CoverageEntry("req", "description");
		assertThat(coverageEntry1, equalTo(coverageEntry2));
		assertThat(coverageEntry1.hashCode(), equalTo(coverageEntry2.hashCode()));
	}
	
	@Test
	public void afterOneEntryIsAdded_coverageCountIs1() throws Exception {
		final CoverageTracker coverageTracker = new CoverageTracker();
		final CoverageEntry coverageEntry = new CoverageEntry("req", "description");
		coverageTracker.add(coverageEntry);
		assertThat(coverageTracker.count(coverageEntry), equalTo(1));
	}

}
