package org.dpolivaev.tsgen.coverage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.internal.ConstantValue;
import org.dpolivaev.tsgen.utils.internal.Utils;
import org.junit.Before;
import org.junit.Test;

public class CoverageTest {

	private static final Goal GOAL = new Goal("goal", new ConstantValue("value"));
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
	
	@Test
	public void afterCheckingRegisteredGoals_returnedValuesAreAdded() throws Exception {
		coverageTracker.add(GOAL);
		coverageTracker.checkGoals((PropertyContainer) null);
		assertThat(coverageTracker.count(COVERAGE_ENTRY), equalTo(1));
	}
	
	@Test
	public void afterCheckingGoal_returnedValueIsAdded() throws Exception {
		GOAL.check(null, coverageTracker);
		assertThat(coverageTracker.count(COVERAGE_ENTRY), equalTo(1));
	}

	@Test
	public void afterCheckingRegisteredGoals_firstTimeAddedCoverageEntriesAreAvailable() throws Exception {
		coverageTracker.add(GOAL);
		coverageTracker.checkGoals((PropertyContainer) null);
		assertThat(coverageTracker.firstTimeCoveredGoals(), equalTo(Utils.set(COVERAGE_ENTRY)));
	}

	@Test
	public void afterCheckingRegisteredGoalsAgain_firstTimeAddedCoverageEntriesAreEmpty() throws Exception {
		coverageTracker.add(GOAL);
		coverageTracker.checkGoals((PropertyContainer) null);
		coverageTracker.checkGoals((PropertyContainer) null);
		assertThat(coverageTracker.firstTimeCoveredGoals(), equalTo(Utils.<CoverageEntry>set()));
	}
	

	@Test
	public void afterCheckingRegisteredGoalsAgain_nextTimeAddedCoverageEntriesAreEmpty() throws Exception {
		coverageTracker.add(GOAL);
		coverageTracker.checkGoals((PropertyContainer) null);
		coverageTracker.checkGoals((PropertyContainer) null);
		assertThat(coverageTracker.repeatedlyCoveredGoals(), equalTo(Utils.<CoverageEntry>set(COVERAGE_ENTRY)));
	}
}
