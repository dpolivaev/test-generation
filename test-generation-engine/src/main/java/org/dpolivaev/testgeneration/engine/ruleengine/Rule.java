package org.dpolivaev.testgeneration.engine.ruleengine;

import java.util.Set;

import org.dpolivaev.testgeneration.engine.ruleengine.internal.PropertyAssignedEvent;

// triggered A <- lazy B
// lazy B <- lazy A
// => triggered A can not be assigned!

// triggered A -> lazy B

public interface Rule {

	String getTargetedPropertyName();

    public Set<String> calculateRequiredProperties(Set<String> dependencies);

    void propertyCombinationStarted(EngineState engineState) throws InvalidCombinationException;

    void propertyCombinationFinished(EngineState engineState);

    void propertyValueSet(PropertyAssignedEvent event) throws InvalidCombinationException;

    boolean blocksRequiredProperties();

    boolean isValueAddedToCurrentCombination();

    void setBlocksRequiredPropertiesItself(boolean block);

    Rule combineWith(Rule rule);

    Rule without(Rule rule);

    void propertyRequired(EngineState engineState);

    boolean isLazyRule();

    boolean forcesIteration();

	void clearDependentRules(EngineState engineState);

	boolean isTopRule();

	void checkRuleCompatibility(Rule rule);

	String getRuleKey();

	public Set<String> getTriggeringProperties();

	boolean valueHasChangedNow();
}