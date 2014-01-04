package org.dpolivaev.tsgen.coverage;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CheckListTest {

	private static final CoverageEntry COVERAGE_ENTRY = new CoverageEntry("goal", "reason");
	private static final CoverageEntry ANY_REASON_COVERAGE_ENTRY = new CoverageEntry("goal", CoverageEntry.ANY);

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
		checkList.addExpected(COVERAGE_ENTRY);
		assertThat(checkList.requiredItemNumber(), equalTo(1));
	}
	
	@Test
	public void checkListWithTwoRequiredItems(){
		checkList.addExpected(COVERAGE_ENTRY);
		CoverageEntry COVERAGE_ENTRY2 = new CoverageEntry("goal", "2");
		checkList.addExpected(COVERAGE_ENTRY2);
		assertThat(checkList.requiredItemNumber(), equalTo(2));
	}

	@Test
	public void newCheckListHasNoFinishedItems(){
		assertThat(checkList.achievedRequiredItemNumber(), equalTo(0));
	}
	
	@Test
	public void checkListWithSingleFinishedItem(){
		checkList.addExpected(COVERAGE_ENTRY);
		checkList.addReached(asList(COVERAGE_ENTRY));
		assertThat(checkList.achievedRequiredItemNumber(), equalTo(1));
	}
	@Test
	public void checkOpenListSingleFinishedItemAchievedRequiredItemNumber(){
		checkList.addReached(asList(COVERAGE_ENTRY));
		assertThat(checkList.achievedRequiredItemNumber(), equalTo(0));
	}
	
	@Test
	public void checkOpenListSingleFinishedItemAchievedItemNumber(){
		checkList.addReached(asList(COVERAGE_ENTRY));
		assertThat(checkList.achievedItemNumber(), equalTo(1));
	}

	@Test
	public void checkListWithAnyReason(){
		checkList.addExpected(ANY_REASON_COVERAGE_ENTRY);
		checkList.addReached(asList(COVERAGE_ENTRY));
		assertThat(checkList.achievedRequiredItemNumber(), equalTo(1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void reachedWithNoReason(){
		checkList.addReached(asList(ANY_REASON_COVERAGE_ENTRY));	
	}
}
