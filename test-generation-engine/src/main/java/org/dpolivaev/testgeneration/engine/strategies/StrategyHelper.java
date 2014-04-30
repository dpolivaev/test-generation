package org.dpolivaev.testgeneration.engine.strategies;

import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.strategies.internal.DescriptionProvider;
import org.dpolivaev.testgeneration.engine.strategies.internal.TestIdProvider;
public class StrategyHelper {
	public static ValueProvider idProvider = new TestIdProvider("=", " ");
	public static ValueProvider descriptionProvider = new DescriptionProvider(": ", "\n");
}