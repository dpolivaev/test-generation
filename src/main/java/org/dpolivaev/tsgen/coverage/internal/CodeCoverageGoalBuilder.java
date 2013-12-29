package org.dpolivaev.tsgen.coverage.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dpolivaev.tsgen.coverage.CheckList;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalFunction;
import org.dpolivaev.tsgen.coverage.code.Model;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;


public class CodeCoverageGoalBuilder {

	final private Collection<Model> models;

	public CodeCoverageGoalBuilder(Collection<Model> list) {
		this.models = list;
	}

	public Goal createGoal(){
		return new Goal("code coverage", new GoalFunction() {
			
			@Override
			public Collection<CoverageEntry> check(PropertyContainer propertyContainer) {
				Collection<CoverageEntry> coveredItems = new ArrayList<>();
				for(Model model:models)
					for (String reached: model.getCodeCoverageTracker().getReached())
						coveredItems.add(new CoverageEntry(model.getName(), reached));
				return coveredItems;
			}
		}, createCheckList());
	}

	CheckList createCheckList() {
		final CheckList checkList = new CheckList();
		for(Model model:models)
			for (String item: model.getExpectedItems())
				checkList.setExpected(new CoverageEntry(model.getName(), item), 1);
		return checkList;
	}

}
