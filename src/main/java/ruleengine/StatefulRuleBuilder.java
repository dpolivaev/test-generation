package ruleengine;

import static ruleengine.TestUtils.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class StatefulRuleBuilder {
    private String targetedPropertyName = null;
    final private List<ValueWithRulesProvider> values = new ArrayList<>();
    private Set<String> triggeringProperties = Collections.emptySet();
    private Condition condition = Condition.TRUE;
    private boolean shuffled = false;
    private int previousValueCount;

    public StatefulRuleBuilder when(
        String... triggeringProperties) {
        this.triggeringProperties = set(triggeringProperties);
        return this;
    }

    public StatefulRuleBuilder over(Object... valueObjects) {
        previousValueCount = this.values.size();
        this.values.addAll(Arrays.asList(valueProviders(valueObjects)));
        return this;
    }

    public StatefulRuleBuilder over(ValueProvider... valueProviders) {
        previousValueCount = this.values.size();
        this.values.addAll(Arrays.asList(valueProviders(valueProviders)));
        return this;
    }

    public StatefulRuleBuilder over(ValueWithRulesProvider... valueWithRulesProviders) {
        this.values.addAll(Arrays.asList(valueWithRulesProviders));
        previousValueCount = this.values.size();
        return this;
    }

    private ValueWithRulesProvider[] valueProviders(Object[] values) {
        ValueWithRulesProvider[] valueWithRulesProviders = new ValueWithRulesProvider[values.length];
        int i = 0;
        for (Object value : values)
            valueWithRulesProviders[i++] = new ConstantValue(value);
        return valueWithRulesProviders;
    }

    private ValueWithRulesProvider[] valueProviders(ValueProvider[] values) {
        ValueWithRulesProvider[] valueWithRulesProviders = new ValueWithRulesProvider[values.length];
        int i = 0;
        for (ValueProvider value : values)
            valueWithRulesProviders[i++] = new ValueWithRules(value, Collections.<Rule> emptyList());
        return valueWithRulesProviders;
    }

    public StatefulRuleBuilder with(Rule... rules) {
        return with(Arrays.asList(rules));
    }

    public StatefulRuleBuilder with(StatefulRuleBuilder... ruleBuilders) {
        Collection<Rule> rules = new ArrayList<>(ruleBuilders.length);
        for (StatefulRuleBuilder builder : ruleBuilders)
            rules.add(builder.asTriggeredRule());
        return with(rules);
    }

    private StatefulRuleBuilder with(Collection<Rule> rules) {
        ListIterator<ValueWithRulesProvider> listIterator = values.listIterator(previousValueCount);
        while (listIterator.hasNext()) {
            ValueWithRulesProvider valueProvider = listIterator.next();
            listIterator.set(new ValueWithRules(valueProvider, rules));
        }
        return this;
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
        Values ruleValues = ruleValues();
        if (triggeringProperties.isEmpty())
            return new TopStatefulRule(this.condition, this.targetedPropertyName, ruleValues);
        else
            return new TriggeredStatefulRule(triggeringProperties, this.condition, this.targetedPropertyName, //
                ruleValues);
    }

    public StatefulRule asTriggeredRule() {
        if (triggeringProperties.isEmpty())
            this.triggeringProperties = set();
        return new TriggeredStatefulRule(triggeringProperties, this.condition, this.targetedPropertyName, //
            ruleValues());
    }

    public StatefulRule asDefaultRule() {
        return new DefaultStatefulRule(this.condition, this.targetedPropertyName, //
            ruleValues());
    }

    private Values ruleValues() {
        ValueWithRulesProvider[] valueProviders = this.values.toArray(new ValueWithRulesProvider[values.size()]);
        OrderedValues orderedValues = new OrderedValues(valueProviders);
        Values ruleValues = shuffled ? new ShuffledValues(orderedValues) : orderedValues;
        return ruleValues;
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