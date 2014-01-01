package org.dpolivaev.tsgen.strategies;

import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.strategies.internal.DescriptionProvider;
import org.dpolivaev.tsgen.strategies.internal.TestIdProvider;

public class StrategyHelper {

	public static Strategy id(String propertyName) {
		return TestIdProvider.strategy(propertyName);
	}
	
	public static Strategy description(String propertyName) {
		return DescriptionProvider.strategy(propertyName);
	}
	
}