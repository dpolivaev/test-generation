package org.dpolivaev.tsgen.coverage;

import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class CheckListTest {

	private static final CoverageEntry COVERAGE_ENTRY = new CoverageEntry("goal", "reason");

	@Test
	public void addsItem() {
		final CheckList checkList = new CheckList();
		checkList.addReached(COVERAGE_ENTRY);
		
		assertThat(checkList.countReached(COVERAGE_ENTRY), CoreMatchers.equalTo(1));
	}

	@Test
	public void putsItem() {
		final CheckList checkList = new CheckList();
		checkList.setReached(COVERAGE_ENTRY, 3);
		
		assertThat(checkList.countReached(COVERAGE_ENTRY), CoreMatchers.equalTo(3));
	}

}
