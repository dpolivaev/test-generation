package org.dpolivaev.tsgen.coverage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.utils.internal.Utils;
import org.junit.Before;
import org.junit.Test;

public class InfiniteGoalTest {
	private static final CoverageEntry COVERAGE_ENTRY = new CoverageEntry("item", "value");
	private static final GoalFunction GOAL_FUNCTION = new GoalFunction() {
		
		@Override
		public Collection<CoverageEntry> check(PropertyContainer propertyContainer) {
			return Arrays.asList(COVERAGE_ENTRY);
		}
	};
			
	private Goal goal;

	@Before
	public void setup(){
		goal = new Goal("goal", GOAL_FUNCTION);
	}
	

	@Test
	public void afterCheckingRegisteredGoals_returnedValuesAreAdded() throws Exception {
		goal.check((PropertyContainer) null);
		CheckList checkList = goal.checkList();
		assertThat(checkList.countReached(COVERAGE_ENTRY), equalTo(1));
	}
	
	@Test
	public void afterCheckingRegisteredGoals_firstTimeAddedCoverageEntriesAreAvailable() throws Exception {
		goal.check((PropertyContainer) null);
		CheckList checkList = goal.checkList();
		assertThat(checkList.firstTimeCoveredEntries(), equalTo(Utils.set(COVERAGE_ENTRY)));
	}

	@Test
	public void afterCheckingRegisteredGoalsAgain_firstTimeAddedCoverageEntriesAreEmpty() throws Exception {
		goal.check((PropertyContainer) null);
		goal.check((PropertyContainer) null);
		CheckList checkList = goal.checkList();
		assertThat(checkList.firstTimeCoveredEntries(), equalTo(Utils.<CoverageEntry>set()));
	}
	

	@Test
	public void afterCheckingRegisteredGoalsAgain_nextTimeAddedCoverageEntriesAreEmpty() throws Exception {
		goal.check((PropertyContainer) null);
		goal.check((PropertyContainer) null);
		CheckList checkList = goal.checkList();
		assertThat(checkList.repeatedlyCoveredEntries(), equalTo(Utils.<CoverageEntry>set(COVERAGE_ENTRY)));
	}
}
