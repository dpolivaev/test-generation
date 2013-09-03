package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.Rule;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

public interface ValueWithRulesProvider extends ValueProvider {
    Collection<Rule> rules(PropertyContainer propertyContainer);
}