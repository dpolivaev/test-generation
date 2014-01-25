package org.dpolivaev.tsgen.scriptwriter;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.internal.RequirementChecker;

public class RequirementBasedStrategy {
	final private RequirementChecker requirementChecker;
	final private Strategy strategy;
	RequirementBasedStrategy(RequirementChecker requirementChecker, Strategy strategy) {
		this.requirementChecker = requirementChecker;
		this.strategy = strategy;
	}
	
	public RequirementBasedStrategy(CoverageEntry[] items) {
		this(new RequirementChecker(), new Strategy());
		requirementChecker.addItems(items);
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
