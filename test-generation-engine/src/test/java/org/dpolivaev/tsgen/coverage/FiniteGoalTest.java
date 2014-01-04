package org.dpolivaev.tsgen.coverage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.junit.Before;
import org.junit.Test;

public class FiniteGoalTest {
	@Before
	public void setup(){
	}

	@Test
	public void ignoresEntriesNotFoundInTheCheckList() {
		final CheckList checkList = new CheckList().addExpected(new CoverageEntry("item", "wanted value"));
		final GoalFunction goalFunction = new GoalFunction() {
			
			@Override
			public Collection<CoverageEntry> check(PropertyContainer propertyContainer) {
				return Arrays.asList(new CoverageEntry("item", "value"));
			}
		};
		Goal goal = new Goal("goal", goalFunction, checkList);
		goal.check((PropertyContainer) null);
		assertThat(checkList.achievedRequiredItemNumber(), equalTo(0));
	}

}
