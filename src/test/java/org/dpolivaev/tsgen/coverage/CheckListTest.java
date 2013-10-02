package org.dpolivaev.tsgen.coverage;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.Arrays;

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
	public void afterOneEntryIsAdded_coverageCountIs1(){
		checkList.addReached(asList(COVERAGE_ENTRY));
		assertThat(checkList.countReached(COVERAGE_ENTRY), equalTo(1));
	}

	@Test
	public void addsItem() {
		checkList.addReached(asList(COVERAGE_ENTRY));
		
		assertThat(checkList.countReached(COVERAGE_ENTRY), equalTo(1));
	}

	@Test
	public void newCheckListHasNoRequriedItems(){
		assertThat(checkList.requiredItemNumber(), equalTo(0));
	}
	
	@Test
	public void checkListWithSingleRequiredItem(){
		checkList.setExpected(COVERAGE_ENTRY, 2);
		assertThat(checkList.requiredItemNumber(), equalTo(1));
	}
	
	@Test
	public void checkListWithTwoRequiredItems(){
		checkList.setExpected(COVERAGE_ENTRY, 1);
		CoverageEntry COVERAGE_ENTRY2 = new CoverageEntry("goal", "2");
		checkList.setExpected(COVERAGE_ENTRY2, 1);
		assertThat(checkList.requiredItemNumber(), equalTo(2));
	}

	@Test
	public void newCheckListHasNoFinishedItems(){
		assertThat(checkList.achievedItemNumber(), equalTo(0));
	}
	
	@Test
	public void checkListWithSingleFinishedItem(){
		checkList.setExpected(COVERAGE_ENTRY, 1);
		checkList.addReached(asList(COVERAGE_ENTRY));
		assertThat(checkList.achievedItemNumber(), equalTo(1));
	}
	@Test
	public void checkOpenListSingleFinishedItem(){
		checkList.addReached(asList(COVERAGE_ENTRY));
		assertThat(checkList.achievedItemNumber(), equalTo(1));
	}
	
}
