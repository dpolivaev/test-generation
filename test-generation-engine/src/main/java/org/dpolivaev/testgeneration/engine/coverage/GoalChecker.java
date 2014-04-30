package org.dpolivaev.testgeneration.engine.coverage;

import java.util.ArrayList;
import java.util.Collection;

import org.dpolivaev.testgeneration.engine.ruleengine.PropertyCombinationHandler;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;

public class GoalChecker extends PropertyCombinationHandler{
	public static final GoalChecker NO_GOALS = new GoalChecker(){

		@Override
		public void addGoal(Goal goal) {
			throw new UnsupportedOperationException("this object must not be added a goal");
		}
		
	};
	final private Collection<Goal> goals;

	public GoalChecker() {
		super();
		this.goals = new ArrayList<>();
	}

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

	@Override
	public void generationFinished() {
	}
}