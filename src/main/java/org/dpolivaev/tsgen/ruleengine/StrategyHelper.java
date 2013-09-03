package org.dpolivaev.tsgen.ruleengine;

import org.dpolivaev.tsgen.ruleengine.internal.TestIdProvider;

public class StrategyHelper {

	public static Strategy id(String propertyName) {
		return TestIdProvider.strategy(propertyName);
	}
	
}