package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import org.dpolivaev.testgeneration.engine.ruleengine.Rule;

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