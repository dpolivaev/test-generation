package org.dpolivaev.testgeneration.engine.coverage.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.dpolivaev.testgeneration.engine.coverage.CheckList;
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker;
import org.dpolivaev.testgeneration.engine.coverage.Goal;
import org.dpolivaev.testgeneration.engine.coverage.GoalFunction;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;


public class RequirementsCoverageGoalBuilder {

	final private Collection<CoverageTracker> trackers;
	final private Collection<CoverageEntry> requiredEntries;

	public RequirementsCoverageGoalBuilder(Collection<CoverageTracker> trackers, Collection<CoverageEntry> requiredEntries) {
		this.trackers = trackers;
		this.requiredEntries = requiredEntries;
	}

	public Goal createGoal(){
		return new Goal("requirement coverage", new GoalFunction() {
			final RequirementCoverage requirementCoverage = new RequirementCoverage();
			@Override
			public Collection<CoverageEntry> check(PropertyContainer propertyContainer) {
				Collection<CoverageEntry> coveredItems = new ArrayList<>(requirementCoverage.check(propertyContainer));
				for(CoverageTracker tracker:trackers)
					for (CoverageEntry reached: tracker.getReached())
						coveredItems.add(reached);
				return coveredItems;
			}
		}, createCheckList());
	}

	CheckList createCheckList() {
		final CheckList checkList = new CheckList();
		for (CoverageEntry item: requiredEntries)
			checkList.addExpected(item);
		return checkList;
	}

}
