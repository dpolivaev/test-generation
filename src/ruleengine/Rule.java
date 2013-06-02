package ruleengine;

import java.util.Set;

// triggered A <- default B
// default B <- default A
// => triggered A can not be assigned!

// triggered A -> default B

public interface Rule {

	String getTargetedPropertyName();

	Set<String> getTriggeringProperties();

	void propertyCombinationStarted(State state);

	void propertyCombinationFinished(State state);

	void propertyValueSet(PropertyAssignedEvent event);

    boolean hasFinished();

    boolean isActive();

    void reset();

    Rule combineWith(Rule rule);

}