package ruleengine.impl;

import java.util.Collection;

import ruleengine.PropertyContainer;
import ruleengine.Rule;
import ruleengine.ValueProvider;
import ruleengine.ValueWithRulesProvider;

public class ValueWithRules implements ValueWithRulesProvider {
    private final Collection<Rule> rules;
    private final ValueProvider valueProvider;

    public ValueWithRules(ValueProvider valueProvider, Collection<Rule> rules) {
        super();
        this.rules = rules;
        this.valueProvider = valueProvider(valueProvider);
    }

    private ValueProvider valueProvider(ValueProvider valueProvider) {
        if (valueProvider instanceof ValueWithRules)
            return ((ValueWithRules) valueProvider).valueProvider;
        else
            return valueProvider;
    }

    @Override
    public Object value(PropertyContainer propertyContainer) {
        return valueProvider.value(propertyContainer);
    }

    @Override
    public Collection<Rule> rules(PropertyContainer propertyContainer) {
        return rules;
    }

}
