package org.dpolivaev.tsgen.coverage.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.dpolivaev.tsgen.coverage.CheckList;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalFunction;
import org.dpolivaev.tsgen.coverage.code.Model;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;


public class RequirementsCoverageGoalBuilder {

	final private Collection<Model> models;

	public RequirementsCoverageGoalBuilder(Collection<Model> list) {
		this.models = list;
	}

	public Goal createGoal(){
		return new Goal("requirement coverage", new GoalFunction() {
			final RequirementCoverage requirementCoverage = new RequirementCoverage();
			@Override
			public Collection<CoverageEntry> check(PropertyContainer propertyContainer) {
				Collection<CoverageEntry> coveredItems = new ArrayList<>(requirementCoverage.check(propertyContainer));
				for(Model model:models)
					for (CoverageEntry reached: model.getCodeCoverageTracker().getReached())
						coveredItems.add(reached);
				return coveredItems;
			}
		}, createCheckList());
	}

	CheckList createCheckList() {
		final CheckList checkList = new CheckList();
		for(Model model:models)
			for (String item: model.getRequiredItems())
				checkList.addExpected(new CoverageEntry(item, CoverageEntry.ANY));
		return checkList;
	}

}
