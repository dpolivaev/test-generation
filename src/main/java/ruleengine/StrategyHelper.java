package ruleengine;

import ruleengine.internal.TestIdProvider;

public class StrategyHelper {

	public static Strategy id(String propertyName) {
		return TestIdProvider.strategy(propertyName);
	}
	
}