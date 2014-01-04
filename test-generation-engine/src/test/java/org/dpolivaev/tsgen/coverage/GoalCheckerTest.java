package org.dpolivaev.tsgen.coverage;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GoalCheckerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected=UnsupportedOperationException.class)
	public void goalsCanNotBeAddedToNoGoals() {
		GoalChecker.NO_GOALS.addGoal(null);
	}

	   
    @Test
	public void goalCheckerExecutesGoalChecks() throws Exception {
		Goal goal = Mockito.mock(Goal.class);
		GoalChecker goalChecker = new GoalChecker(null);
		goalChecker.addGoal(goal);
		final PropertyContainer propertyContainer = Mockito.mock(PropertyContainer.class);
		goalChecker.handlePropertyCombination(propertyContainer);
		
		Mockito.verify(goal).check(propertyContainer);
	}
}
