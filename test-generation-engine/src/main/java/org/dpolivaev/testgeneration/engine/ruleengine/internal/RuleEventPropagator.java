package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import org.dpolivaev.testgeneration.engine.ruleengine.Rule;

public interface RuleEventPropagator {
    void propagateEvent(Rule rule);
}
