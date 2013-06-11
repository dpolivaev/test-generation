package ruleengine;

interface Values {

	boolean isNewIterationFinished();

	void next();

	Object currentValue();

}