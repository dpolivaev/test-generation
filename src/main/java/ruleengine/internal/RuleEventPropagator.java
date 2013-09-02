package ruleengine.internal;

import ruleengine.Rule;

public interface RuleEventPropagator {
    void propagateEvent(Rule rule);
}
