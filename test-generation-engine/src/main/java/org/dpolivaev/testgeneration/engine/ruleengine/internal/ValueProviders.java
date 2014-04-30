package org.dpolivaev.testgeneration.engine.ruleengine.internal;

public interface ValueProviders {

	boolean allValuesUsed();

	void next();

    ValueWithRulesProvider currentProvider();

    boolean containsMultipleValues();
}