package ruleengine;

interface Values {

	boolean isNewIterationStarted();

	void next();

	Object currentValue();

}