package org.dpolivaev.tsgen.ruleengine;

import org.dpolivaev.tsgen.ruleengine.internal.DescriptionProvider;
import org.dpolivaev.tsgen.ruleengine.internal.TestIdProvider;

public class StrategyHelper {

	public static Strategy id(String propertyName) {
		return TestIdProvider.strategy(propertyName);
	}
	
	public static Strategy description(String propertyName) {
		return DescriptionProvider.strategy(propertyName);
	}
	
}