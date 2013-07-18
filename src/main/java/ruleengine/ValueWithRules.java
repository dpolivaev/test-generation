package ruleengine;

import java.util.Collection;

public class ValueWithRules implements ValueWithRulesProvider {
    private final Collection<Rule> rules;
    private final ValueProvider valueProvider;

    public ValueWithRules(ValueProvider valueProvider, Collection<Rule> rules) {
        super();
        this.rules = rules;
        this.valueProvider = valueProvider;
    }

    @Override
    public Object value() {
        return valueProvider.value();
    }

    @Override
    public Collection<Rule> rules() {
        return rules;
    }

}
