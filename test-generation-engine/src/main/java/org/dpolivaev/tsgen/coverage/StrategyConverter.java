package org.dpolivaev.tsgen.coverage;

import org.dpolivaev.tsgen.ruleengine.Strategy;

public class StrategyConverter {
	public static Strategy toStrategy(Strategy from) {
		return from;
	}

	public static Strategy toStrategy(RequirementBasedStrategy from) {
		return from.getStrategy();
	}

	public static RequirementBasedStrategy toRequirementBasedStrategy(
			Strategy from) {
		return new RequirementBasedStrategy().with(from);
	}

	public static RequirementBasedStrategy toRequirementBasedStrategy(
			RequirementBasedStrategy from) {
		return from;
	}
}
