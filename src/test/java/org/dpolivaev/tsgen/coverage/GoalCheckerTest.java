package org.dpolivaev.tsgen.coverage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GoalCheckerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected=UnsupportedOperationException.class)
	public void goalsCanNotBeAddedToNoGoals() {
		GoalChecker.NO_GOALS.addGoal(null);
	}

}
