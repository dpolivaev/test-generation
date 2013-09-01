package ruleengine;

public interface ValueProviders {

	boolean allValuesUsed();

	void next();

    ValueWithRulesProvider currentProvider();

    boolean containsMultipleValues();
}