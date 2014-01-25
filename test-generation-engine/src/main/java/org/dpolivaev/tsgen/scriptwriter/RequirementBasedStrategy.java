package org.dpolivaev.tsgen.scriptwriter;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;

public class RequirementBasedStrategy {
	final private RequirementChecker requirementChecker;
	final private Strategy strategy;
	public RequirementBasedStrategy(RequirementChecker requirementChecker, Strategy strategy) {
		this.requirementChecker = requirementChecker;
		this.strategy = strategy;
	}
	
	public RequirementBasedStrategy(CoverageEntry[] items) {
		this(new RequirementChecker());
		requirementChecker.addItems(items);
	}

	public RequirementBasedStrategy(RequirementChecker requirementChecker) {
		this(requirementChecker, new Strategy());
	}
	
	public void addRequiredItems(WriterFactory runner){
		requirementChecker.addRequiredItems(runner);
	}
	
	public void run(RuleEngine ruleEngine){
		ruleEngine.run(strategy);
	}
	
	public RequirementBasedStrategy with(
			RequirementBasedStrategy anotherRequirementBasedStrategy) {
		return new RequirementBasedStrategy(requirementChecker.with(anotherRequirementBasedStrategy.requirementChecker), strategy.with(anotherRequirementBasedStrategy.strategy));
	}
	public RequirementBasedStrategy with(Strategy anotherStrategy) {
		return new RequirementBasedStrategy(requirementChecker, strategy.with(anotherStrategy));
	}
}
