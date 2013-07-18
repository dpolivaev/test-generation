package ruleengine;

import java.util.Set;

// triggered A <- default B
// default B <- default A
// => triggered A can not be assigned!

// triggered A -> default B

public interface Rule {

	String getTargetedPropertyName();

	Set<String> getTriggeringProperties();

    public Set<String> requiredProperties(Set<String> dependencies);

    void propertyCombinationStarted(EngineState engineState) throws InvalidCombinationException;

    void propertyCombinationFinished(EngineState engineState);

    void propertyValueSet(PropertyAssignedEvent event) throws InvalidCombinationException;

    boolean hasFinished();

    boolean isActive();

    void setNotFinished();

    Rule combineWith(Rule rule);

    Rule without(Rule rule);

    void propertyRequired(EngineState engineState);

    boolean isDefaultRule();
}