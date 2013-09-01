package ruleengine.impl;

import java.util.Collection;

import ruleengine.PropertyContainer;
import ruleengine.Rule;
import ruleengine.ValueProvider;

public interface ValueWithRulesProvider extends ValueProvider {
    Collection<Rule> rules(PropertyContainer propertyContainer);
}