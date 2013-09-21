package org.dpolivaev.tsgen.coverage;

import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

public class Goal {
	final private String name;
	final private GoalFunction goalFunction;
	private CoverageTracker coverageTracker;

	public Goal(String name, GoalFunction goalFunction) {
		this.name = name;
		this.goalFunction = goalFunction;
		this.coverageTracker = new CoverageTracker();
	}

	public void check(PropertyContainer propertyContainer) {
		final Collection<CoverageEntry> newCoverageEntries = goalFunction.check(propertyContainer);
		coverageTracker.startRound();
		coverageTracker.addAll(newCoverageEntries);
		
	}

	public CoverageTracker coverageTracker() {
		return coverageTracker;
	}

	public String name() {
		return name;
	}

}
