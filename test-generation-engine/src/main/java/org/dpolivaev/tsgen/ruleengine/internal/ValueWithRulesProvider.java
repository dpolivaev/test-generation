package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

public interface ValueWithRulesProvider extends ValueProvider {
    Collection<RuleBuilder> rules(PropertyContainer propertyContainer);
}