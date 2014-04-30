package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import org.dpolivaev.testgeneration.engine.ruleengine.EngineState;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;

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