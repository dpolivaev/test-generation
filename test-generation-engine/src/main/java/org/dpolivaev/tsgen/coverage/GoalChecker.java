package org.dpolivaev.tsgen.coverage;

import java.util.ArrayList;
import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;
import org.dpolivaev.tsgen.scriptwriter.internal.ReportWriter;

public class GoalChecker implements PropertyHandler{
	public static final GoalChecker NO_GOALS = new GoalChecker(null){

		@Override
		public void addGoal(Goal goal) {
			throw new UnsupportedOperationException("this object must not be added a goal");
		}
		
	};
	final private Collection<Goal> goals;
	final private ReportWriter reportWriter;

	public GoalChecker(ReportWriter reportWriter) {
		super();
		this.goals = new ArrayList<>();
		this.reportWriter = reportWriter;
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
		reportWriter.createReport(this);

	}
}