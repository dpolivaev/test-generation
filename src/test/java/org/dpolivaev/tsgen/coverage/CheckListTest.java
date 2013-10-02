package org.dpolivaev.tsgen.coverage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

public class CheckListTest {

	private static final CoverageEntry COVERAGE_ENTRY = new CoverageEntry("goal", "reason");

	private CheckList checkList;
	
	@Before
	public void setup(){
		checkList = new CheckList();
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
		checkList.addReached(COVERAGE_ENTRY);
		assertThat(checkList.countReached(COVERAGE_ENTRY), equalTo(1));
	}

	@Test
	public void addsItem() {
		checkList.addReached(COVERAGE_ENTRY);
		
		assertThat(checkList.countReached(COVERAGE_ENTRY), CoreMatchers.equalTo(1));
	}

	@Test
	public void putsItem() {
		checkList.setReached(COVERAGE_ENTRY, 3);
		
		assertThat(checkList.countReached(COVERAGE_ENTRY), CoreMatchers.equalTo(3));
	}

}
