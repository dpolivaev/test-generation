package org.dpolivaev.tsgen.coverage;

import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

public class Goal {
	final private String name;
	final private GoalFunction goalFunction;
	final private boolean finite;
	final private CheckList checkList;

	public Goal(String name, GoalFunction goalFunction) {
		this(name, goalFunction, null);
	}

	public Goal(String name, GoalFunction goalFunction, CheckList checkList) {
		this.name = name;
		this.goalFunction = goalFunction;
		this.finite = checkList != null;
		this.checkList = checkList != null ? checkList : new CheckList();
	}

	public void check(PropertyContainer propertyContainer) {
		final Collection<CoverageEntry> newCoverageEntries = goalFunction.check(propertyContainer);
		checkList.startRound();
		if(finite)
			checkList.addRequiredEntries(newCoverageEntries);
		else
			checkList.addAllEntries(newCoverageEntries);
		
	}

	public String name() {
		return name;
	}

	public CheckList checkList() {
		return checkList;
	}

}
