package org.dpolivaev.tsgen.strategies;

import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.strategies.internal.DescriptionProvider;
import org.dpolivaev.tsgen.strategies.internal.TestIdProvider;
public class StrategyHelper {
	public static ValueProvider idProvider = new TestIdProvider("=", " ");
	public static ValueProvider descriptionProvider = new DescriptionProvider(": ", "\n");
}