package ruleengine;

import static ruleengine.TestUtils.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class StatefulRuleBuilder {
    private String targetedPropertyName = null;
    final private Collection<ValueWithRulesProvider> values = new ArrayList<>();
    private Set<String> triggeringProperties = Collections.emptySet();
    private Condition condition = Condition.TRUE;
    private boolean shuffled = false;

    public StatefulRuleBuilder when(
        String... triggeringProperties) {
        this.triggeringProperties = set(triggeringProperties);
        return this;
    }

    public StatefulRuleBuilder over(Object... values) {
        this.values.addAll(values(values));
        return this;
    }

    private Collection<ValueWithRulesProvider> values(Object[] values) {
        Collection<ValueWithRulesProvider> valueWithRulesProviders = new ArrayList<>(values.length);
        for (Object value : values)
            valueWithRulesProviders.add(new ConstantValue(value));
        return valueWithRulesProviders;
    }

    public StatefulRuleBuilder with(Object value, Rule... rules) {
        this.values.add(valueWithRules(value, rules));
        return this;
    }

    private ValueWithRulesProvider valueWithRules(Object value, Rule[] rules) {
        return new ValueWithRules(new ConstantValue(value), Arrays.asList(rules));
    }

    public StatefulRuleBuilder iterate(
        String targetedPropertyName) {
        this.targetedPropertyName = targetedPropertyName;
        return this;
    }

    public StatefulRuleBuilder shuffled() {
        shuffled = true;
        return this;
    }

    public StatefulRule asRule() {
        ValueWithRulesProvider[] valueProviders = this.values.toArray(new ValueWithRulesProvider[values.size()]);
        OrderedValues orderedValues = new OrderedValues(valueProviders);
        Values ruleValues = shuffled ? new ShuffledValues(orderedValues) : orderedValues;
        return triggeringProperties.isEmpty() ? //
        new TopStatefulRule(this.condition, this.targetedPropertyName, ruleValues)
            : new TriggeredStatefulRule(triggeringProperties, this.condition, this.targetedPropertyName, //
                ruleValues);
    }

    public StatefulRuleBuilder _if(Condition condition) {
        this.condition = condition;
        return this;
    }

    public static class Factory {
        static public StatefulRuleBuilder when(String... triggeringProperties) {
            return new StatefulRuleBuilder().when(triggeringProperties);
        }

        static public StatefulRuleBuilder iterate(String property) {
            return new StatefulRuleBuilder().iterate(property);
        }

        static public StatefulRuleBuilder _if(Condition condition) {
            return new StatefulRuleBuilder()._if(condition);
        }
    }


}