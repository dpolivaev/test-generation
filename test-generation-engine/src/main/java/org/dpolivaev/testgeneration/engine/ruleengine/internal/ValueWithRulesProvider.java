package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import java.util.Collection;

import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;

public interface ValueWithRulesProvider extends ValueProvider {
    Collection<RuleBuilder> rules(PropertyContainer propertyContainer);
}