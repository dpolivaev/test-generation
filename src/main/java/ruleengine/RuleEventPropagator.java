package ruleengine;

interface RuleEventPropagator {
    void propagateEvent(Rule rule);
}

class PropertyCombinationStartedPropagator implements RuleEventPropagator {
    private final EngineState engineState;

    PropertyCombinationStartedPropagator(EngineState engineState) {
        this.engineState = engineState;
    }

    @Override
    public void propagateEvent(Rule rule) {
        rule.propertyCombinationStarted(engineState);

    }
}

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

class PropertyValueSetPropagator implements RuleEventPropagator {
    private final PropertyAssignedEvent event;

    PropertyValueSetPropagator(PropertyAssignedEvent event) {
        this.event = event;
    }

    @Override
    public void propagateEvent(Rule rule) {
        rule.propertyValueSet(event);

    }
}
