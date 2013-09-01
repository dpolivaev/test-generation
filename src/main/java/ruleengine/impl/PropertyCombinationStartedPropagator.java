package ruleengine.impl;

import ruleengine.EngineState;
import ruleengine.Rule;

public class PropertyCombinationStartedPropagator implements RuleEventPropagator {
    private final EngineState engineState;

    public PropertyCombinationStartedPropagator(EngineState engineState) {
        this.engineState = engineState;
    }

    @Override
    public void propagateEvent(Rule rule) {
        rule.propertyCombinationStarted(engineState);

    }
}