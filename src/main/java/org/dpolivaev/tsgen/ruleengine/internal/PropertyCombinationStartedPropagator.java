package org.dpolivaev.tsgen.ruleengine.internal;

import org.dpolivaev.tsgen.ruleengine.EngineState;
import org.dpolivaev.tsgen.ruleengine.Rule;

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