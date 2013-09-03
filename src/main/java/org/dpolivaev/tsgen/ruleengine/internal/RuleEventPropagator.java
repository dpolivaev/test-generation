package org.dpolivaev.tsgen.ruleengine.internal;

import org.dpolivaev.tsgen.ruleengine.Rule;

public interface RuleEventPropagator {
    void propagateEvent(Rule rule);
}
