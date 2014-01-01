package org.dpolivaev.tsgen.ruleengine;

import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.internal.PropertyAssignedEvent;

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

    boolean blocksRequiredProperties();

    boolean isValueAddedToCurrentCombination();

    void setBlocksRequiredProperties(boolean block);

    Rule combineWith(Rule rule);

    Rule without(Rule rule);

    void propertyRequired(EngineState engineState);

    boolean isDefaultRule();

    boolean forcesIteration();

	void clearDependentRules(EngineState engineState);
}