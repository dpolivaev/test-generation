package ruleengine;

interface ValueProviders {

	boolean allValuesUsed();

	void next();

    ValueWithRulesProvider currentProvider();

    boolean containsMultipleValues();
}