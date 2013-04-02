package ruleengine;


// triggered A <- default B
// default B <- default A
// => triggered A can not be assigned!

// triggered A -> default B

public interface Rule {

	String getTargetedPropertyName();
	boolean hasFinished();
	Object nextValue();
	void nextIteration(PropertySet propertySet);
	void propertyValueSet(PropertySet propertySet, String targetedPropertyName);
	
}