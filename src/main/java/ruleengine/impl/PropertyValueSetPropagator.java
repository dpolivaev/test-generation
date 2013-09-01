package ruleengine.impl;

import ruleengine.Rule;

public class PropertyValueSetPropagator implements RuleEventPropagator {
    private final PropertyAssignedEvent event;

    public PropertyValueSetPropagator(PropertyAssignedEvent event) {
        this.event = event;
    }

    @Override
    public void propagateEvent(Rule rule) {
        rule.propertyValueSet(event);

    }
}