package org.dpolivaev.tsgen.coverage;

import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class CheckListTest {

	private static final CoverageEntry COVERAGE_ENTRY = new CoverageEntry("goal", "reason");

	@Test
	public void addsItem() {
		final CheckList checkList = new CheckList();
		checkList.add(COVERAGE_ENTRY);
		
		assertThat(checkList.count(COVERAGE_ENTRY), CoreMatchers.equalTo(1));
	}

	@Test
	public void putsItem() {
		final CheckList checkList = new CheckList();
		checkList.put(COVERAGE_ENTRY, 3);
		
		assertThat(checkList.count(COVERAGE_ENTRY), CoreMatchers.equalTo(3));
	}

	@Test
	public void sameListContainsCheckList() {
		final CheckList checkList1 = new CheckList();
		checkList1.put(COVERAGE_ENTRY, 3);
		final CheckList checkList2 = new CheckList();
		checkList2.put(COVERAGE_ENTRY, 3);
		
		assertThat(checkList1.contains(checkList2), CoreMatchers.equalTo(true));
	}
	
	@Test
	public void emtpyListDoesNotContainCheckList() {
		final CheckList checkList1 = new CheckList();
		final CheckList checkList2 = new CheckList();
		checkList2.add(COVERAGE_ENTRY);
		
		assertThat(checkList1.contains(checkList2), CoreMatchers.equalTo(false));
	}
}
