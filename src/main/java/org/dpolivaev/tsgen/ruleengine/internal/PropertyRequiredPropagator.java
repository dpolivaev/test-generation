package org.dpolivaev.tsgen.ruleengine.internal;

import org.dpolivaev.tsgen.ruleengine.EngineState;
import org.dpolivaev.tsgen.ruleengine.Rule;

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