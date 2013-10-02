package org.dpolivaev.tsgen.coverage;

import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

public class Goal {
	final private String name;
	final private GoalFunction goalFunction;
	final private CheckList checkList;
	
	public Goal(String name, GoalFunction goalFunction) {
		this(name, goalFunction, new CheckList());
	}

	public Goal(String name, GoalFunction goalFunction, CheckList checkList) {
		this.name = name;
		this.goalFunction = goalFunction;
		this.checkList = checkList;
	}

	public void check(PropertyContainer propertyContainer) {
		final Collection<CoverageEntry> newCoverageEntries = goalFunction.check(propertyContainer);
		checkList.addReached(newCoverageEntries);
		
	}

	public String name() {
		return name;
	}

	public CheckList checkList() {
		return checkList;
	}

}
