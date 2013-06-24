package ruleengine;

import java.util.Collection;

interface Values {

	boolean allValuesUsed();

	void next();

	Object currentValue();

    Collection<Rule> currentValueRelatedRules();
}