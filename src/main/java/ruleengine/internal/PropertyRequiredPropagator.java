package ruleengine.internal;

import ruleengine.EngineState;
import ruleengine.Rule;

class PropertyRequiredPropagator implements RuleEventPropagator {
    private final EngineState engineState;

    PropertyRequiredPropagator(EngineState engineState) {
        this.engineState = engineState;
    }

    @Override
    public void propagateEvent(Rule rule) {
        rule.propertyRequired(engineState);

    }
}