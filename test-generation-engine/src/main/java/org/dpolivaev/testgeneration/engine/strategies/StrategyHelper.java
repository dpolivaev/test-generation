package org.dpolivaev.testgeneration.engine.strategies;

import org.dpolivaev.testgeneration.engine.strategies.internal.DescriptionProvider;
import org.dpolivaev.testgeneration.engine.strategies.internal.TestIdProvider;
public class StrategyHelper {
	public static TestIdProvider idProvider() {return new TestIdProvider();}
	public static DescriptionProvider descriptionProvider() {return  new DescriptionProvider(": ", "\n");}
}