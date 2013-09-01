package ruleengine.impl;

import ruleengine.Rule;

public interface RuleEventPropagator {
    void propagateEvent(Rule rule);
}
