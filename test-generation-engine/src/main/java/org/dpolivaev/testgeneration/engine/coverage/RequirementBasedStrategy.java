package org.dpolivaev.testgeneration.engine.coverage;

import java.util.Collection;

import org.dpolivaev.testgeneration.engine.coverage.internal.RequirementChecker;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleEngine;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory;

public class RequirementBasedStrategy{
	final private RequirementChecker requirementChecker;
	final private Strategy strategy;
	RequirementBasedStrategy(RequirementChecker requirementChecker, Strategy strategy) {
		this.requirementChecker = requirementChecker;
		this.strategy = strategy;
	}
	
	public RequirementBasedStrategy(CoverageEntry... items) {
		this(new RequirementChecker(), new Strategy());
		requirementChecker.addItems(items);
	}
	public void registerRequiredItems(WriterFactory runner){
		requirementChecker.registerRequiredItems(runner);
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

	public Strategy getStrategy() {
		return strategy;
	}

	public RequirementBasedStrategy addRequiredItemsFrom(final RequirementBasedStrategy otherStrategy) {
		otherStrategy.requirementChecker.registerRequiredItems(new RequiredCoverageItemCollector() {
			@Override
			public void registerRequiredItems(Collection<CoverageEntry> items) {
				requirementChecker.addItems(items);
			}
		});
		return this;
	}
}
