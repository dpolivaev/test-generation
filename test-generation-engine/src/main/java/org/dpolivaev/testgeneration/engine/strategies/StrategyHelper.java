package org.dpolivaev.testgeneration.engine.strategies;

import org.dpolivaev.testgeneration.engine.strategies.internal.DescriptionProvider;
import org.dpolivaev.testgeneration.engine.strategies.internal.TestNameProvider;
public class StrategyHelper {
	public static TestNameProvider testcaseName() {return new TestNameProvider();}
	public static DescriptionProvider testcaseDescription() {return  new DescriptionProvider(": ", "\n");}
}