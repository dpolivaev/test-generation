package org.dpolivaev.tsgen.coverage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CoverageTest {

	private static final CoverageEntry COVERAGE_ENTRY = new CoverageEntry("goal", "value");

	private CoverageTracker coverageTracker;
	
	@Before
	public void setup(){
		coverageTracker = new CoverageTracker();
	}
	

	@Test
	public void coverageEntryEquality() {
		final CoverageEntry coverageEntry1 = new CoverageEntry("req", "reason");
		final CoverageEntry coverageEntry2 = new CoverageEntry("req", "reason");
		assertThat(coverageEntry1, equalTo(coverageEntry2));
		assertThat(coverageEntry1.hashCode(), equalTo(coverageEntry2.hashCode()));
	}
	
	@Test
	public void afterOneEntryIsAdded_coverageCountIs1() throws Exception {
		coverageTracker.add(COVERAGE_ENTRY);
		assertThat(coverageTracker.count(COVERAGE_ENTRY), equalTo(1));
	}
	
}
