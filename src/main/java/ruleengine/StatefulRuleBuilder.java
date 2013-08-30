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
    private Order order = Order.defaultOrder;
    private int previousValueCount;

    public StatefulRuleBuilder when(
        String... triggeringProperties) {
    	if(! this.triggeringProperties.isEmpty())
    		throw new IllegalStateException("triggering properties already set to" + triggeringProperties);
    	this.triggeringProperties = set(triggeringProperties);
        return this;
    }

    public StatefulRuleBuilder over(Object... valueObjects) {
        previousValueCount = this.values.size();
        for(Object valueObject : valueObjects)
        	if(valueObject instanceof ValueWithRulesProvider)
        		values.add((ValueWithRulesProvider) valueObject);
        	else if(valueObject instanceof ValueProvider)
        		values.add(valueProvider((ValueProvider)valueObject));
        	else
        		values.add(valueProvider(valueObject));
        return this;
    }


    private ValueWithRulesProvider valueProvider(Object value) {
        return new ConstantValue(value);
    }

    private ValueWithRulesProvider valueProvider(ValueProvider value) {
    	return new ValueWithRules(value, Collections.<Rule> emptyList());
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

    public StatefulRuleBuilder ordered() {
        order = Order.ORDERED;
        return this;
    }

    public StatefulRuleBuilder orderedThenShuffled() {
        order = Order.ORDERED_THEN_SHUFFLED;
        return this;
    }

    public StatefulRuleBuilder shuffled() {
        order = Order.SHUFFLED;
        return this;
    }

    public StatefulRule asRule() {
        ValueProviders ruleValues = ruleValues();
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
        return new DefaultStatefulRule(triggeringProperties, this.condition, this.targetedPropertyName, //
            ruleValues());
    }

    private ValueProviders ruleValues() {
        ValueWithRulesProvider[] valueProviders = this.values.toArray(new ValueWithRulesProvider[values.size()]);
        OrderedValueProviders orderedValueProviders = new OrderedValueProviders(valueProviders);
        ValueProviders ruleValues = order == Order.ORDERED ? new ShuffledValueProviders(orderedValueProviders) : orderedValueProviders;
        return ruleValues;
    }


    public StatefulRuleBuilder _if(Condition condition) {
    	if(this.condition != Condition.TRUE)
    		throw new IllegalStateException("condition already set");
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