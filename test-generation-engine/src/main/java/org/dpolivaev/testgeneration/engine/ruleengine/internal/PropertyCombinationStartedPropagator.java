package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import org.dpolivaev.testgeneration.engine.ruleengine.EngineState;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;

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