package ruleengine;

// triggered A <- default B
// default B <- default A
// => triggered A can not be assigned!

// triggered A -> default B

public interface Rule {

	String getTargetedPropertyName();

	boolean hasFinished();

	void combinationStarted(State state);

	void combinationFinished(State state);

	void propertyValueSet(PropertyAssignedEvent event);

}