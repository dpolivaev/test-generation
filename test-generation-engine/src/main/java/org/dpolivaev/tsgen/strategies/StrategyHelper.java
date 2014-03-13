package org.dpolivaev.tsgen.strategies;

import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.strategies.internal.DescriptionProvider;
import org.dpolivaev.tsgen.strategies.internal.TestIdProvider;

public class StrategyHelper {

	public static Strategy id(OutputConfiguration outputConfiguration, String propertyName) {
		return TestIdProvider.strategy(outputConfiguration, propertyName);
	}
	
	public static Strategy description(OutputConfiguration outputConfiguration, String propertyName) {
		return DescriptionProvider.strategy(outputConfiguration, propertyName);
	}
	
}