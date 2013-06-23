package ruleengine;

import java.util.Collection;

interface Values {

	boolean isNewIterationFinished();

	void next();

	Object currentValue();

    Collection<Rule> currentValueRelatedRules();

}