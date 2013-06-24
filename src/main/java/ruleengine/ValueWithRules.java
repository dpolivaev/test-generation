package ruleengine;

import java.util.Collection;

public class ValueWithRules implements ValueWithRulesProvider {
    private final Collection<Rule> rules;
    private final ValueWithRulesProvider value;

    public ValueWithRules(ValueWithRulesProvider value, Collection<Rule> rules) {
        super();
        this.rules = rules;
        this.value = value;
    }

    @Override
    public Object value() {
        return value.value();
    }

    @Override
    public Collection<Rule> rules() {
        return rules;
    }

}
