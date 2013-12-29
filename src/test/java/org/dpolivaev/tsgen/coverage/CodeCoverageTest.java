package org.dpolivaev.tsgen.coverage;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dpolivaev.tsgen.coverage.code.CodeCoverageTracker;
import org.dpolivaev.tsgen.coverage.code.Model;
import org.dpolivaev.tsgen.coverage.internal.CodeCoverageGoalBuilder;
import org.dpolivaev.tsgen.coverage.internal.CodeCoverageResetter;
import org.junit.Test;

public class CodeCoverageTest {
	private Model mockModel(List<String> items) {
		Model model = mock(Model.class);
		when(model.getName()).thenReturn("model");
		final CodeCoverageTracker codeCoverageTracker = new CodeCoverageTracker();
		when(model.getCodeCoverageTracker()).thenReturn(codeCoverageTracker);
		final List<String> itemList = items;
		when(model.getExpectedItems()).thenReturn(itemList);
		return model;
	}


	@Test
	public void codeCoverageResetterClearsCodeCoverage() {
		final CodeCoverageTracker codeCoverageTracker = mock(CodeCoverageTracker.class);
		final CodeCoverageResetter codeCoverageResetter = new CodeCoverageResetter(codeCoverageTracker);
		codeCoverageResetter.handlePropertyCombination(null);
		verify(codeCoverageTracker).clear();
	}
	
	@Test
	public void codeCoverageCollectsPoints(){
		final CodeCoverageTracker codeCoverageTracker = new CodeCoverageTracker();
		codeCoverageTracker.reach("point");
		final List<String> coverage = codeCoverageTracker.getReached();
		assertThat(coverage, equalTo(asList("point")));
	}
	
	@Test
	public void codeCoverageClearsPoints(){
		final CodeCoverageTracker codeCoverageTracker = new CodeCoverageTracker();
		codeCoverageTracker.reach("point");
		codeCoverageTracker.clear();
		final List<String> coverage = codeCoverageTracker.getReached();
		assertThat(coverage, equalTo(Arrays.<String>asList()));
	}
	
	@Test
	public void emptyCodeCoverageGoal() throws Exception {
		final CodeCoverageGoalBuilder codeCoverageGoalBuilder = new CodeCoverageGoalBuilder(Collections.<Model>emptyList());
		final Goal goal = codeCoverageGoalBuilder.createGoal();
		assertThat(goal.checkList().requiredItemNumber(), equalTo(0));
	}
	
	@Test
	public void codeCoverageGoalForModel() throws Exception {
		Model model = mockModel(asList("item"));
		final CodeCoverageGoalBuilder codeCoverageGoalBuilder = new CodeCoverageGoalBuilder(asList(model));
		final Goal goal = codeCoverageGoalBuilder.createGoal();
		assertThat(goal.checkList().requiredItemNumber(), equalTo(1));
	}
	
	@Test
	public void codeCoverageForModel() throws Exception {
		Model model = mockModel(asList("item"));
		final CodeCoverageGoalBuilder codeCoverageGoalBuilder = new CodeCoverageGoalBuilder(asList(model));
		final Goal goal = codeCoverageGoalBuilder.createGoal();
		model.getCodeCoverageTracker().reach("item");
		goal.check(null);
		assertThat(goal.checkList().achievedItemNumber(), equalTo(1));
	}
}
