package org.dpolivaev.tsgen.coverage;

import java.util.ArrayList;
import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;

public class GoalChecker implements PropertyHandler{
	public static final GoalChecker NO_GOALS = new GoalChecker(){

		@Override
		public void addGoal(Goal goal) {
			throw new UnsupportedOperationException("this object must not be added a goal");
		}
		
	};
	private Collection<Goal> goals = new ArrayList<>();

	@Override
	public void handlePropertyCombination(PropertyContainer propertyContainer) {
        for(Goal goal : this.goals)
        	goal.check(propertyContainer);
	}
	
	public void addGoal(Goal goal) {
		goals.add(goal);
	}

	public Collection<Goal> goals() {
		return goals;
	}
}