package org.dpolivaev.tsgen.coverage;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dpolivaev.tsgen.coverage.code.Model;
import org.dpolivaev.tsgen.coverage.internal.RequirementsCoverageGoalBuilder;
import org.dpolivaev.tsgen.coverage.internal.CodeCoverageResetter;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.junit.Test;

public class CodeCoverageTest {
	@Test
	public void codeCoverageResetterClearsCodeCoverage() {
		final CoverageTracker coverageTracker = mock(CoverageTracker.class);
		final CodeCoverageResetter codeCoverageResetter = new CodeCoverageResetter(coverageTracker);
		codeCoverageResetter.handlePropertyCombination(null);
		verify(coverageTracker).clear();
	}
	
	@Test
	public void codeCoverageCollectsPoints(){
		final CoverageTracker coverageTracker = new CoverageTracker();
		coverageTracker.reach("point", "reason");
		final List<CoverageEntry> coverage = coverageTracker.getReached();
		assertThat(coverage, equalTo(asList(new CoverageEntry("point", "reason"))));
	}
	
	@Test
	public void codeCoverageClearsPoints(){
		final CoverageTracker coverageTracker = new CoverageTracker();
		coverageTracker.reach("point", "reason");
		coverageTracker.clear();
		final List<CoverageEntry> coverage = coverageTracker.getReached();
		assertThat(coverage, equalTo(Arrays.<CoverageEntry>asList()));
	}
	
	@Test
	public void emptyCodeCoverageGoal() throws Exception {
		final RequirementsCoverageGoalBuilder codeCoverageGoalBuilder = new RequirementsCoverageGoalBuilder(Collections.<CoverageTracker>emptyList(), Collections.<CoverageEntry>emptyList());
		final Goal goal = codeCoverageGoalBuilder.createGoal();
		assertThat(goal.checkList().requiredItemNumber(), equalTo(0));
	}
	
	@Test
	public void codeCoverageGoalForModel() throws Exception {
		final RequirementsCoverageGoalBuilder codeCoverageGoalBuilder = new RequirementsCoverageGoalBuilder(Collections.<CoverageTracker>emptyList(), asList(new CoverageEntry("item", CoverageEntry.ANY)));
		final Goal goal = codeCoverageGoalBuilder.createGoal();
		assertThat(goal.checkList().requiredItemNumber(), equalTo(1));
	}
	
	@Test
	public void codeCoverageForModel() throws Exception {
		PropertyContainer propertyContainer = mock(PropertyContainer.class);
		when(propertyContainer.availableProperties(anyString())).thenReturn(Collections.<String>emptySet());
		CoverageTracker tracker = new CoverageTracker();
		final RequirementsCoverageGoalBuilder codeCoverageGoalBuilder = new RequirementsCoverageGoalBuilder(asList(tracker),  Collections.<CoverageEntry>emptyList());
		final Goal goal = codeCoverageGoalBuilder.createGoal();
		tracker.reach("item", "reason");
		goal.check(propertyContainer);
		assertThat(goal.checkList().totalAchievedItemNumber(), equalTo(1));
	}
}
