package ruleengine;

import ruleengine.impl.TestIdProvider;

public class StrategyHelper {

	public static Strategy id(String propertyName) {
		return TestIdProvider.strategy(propertyName);
	}

}
