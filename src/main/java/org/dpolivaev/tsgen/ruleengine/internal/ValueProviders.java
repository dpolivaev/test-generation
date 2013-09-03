package org.dpolivaev.tsgen.ruleengine.internal;

public interface ValueProviders {

	boolean allValuesUsed();

	void next();

    ValueWithRulesProvider currentProvider();

    boolean containsMultipleValues();
}