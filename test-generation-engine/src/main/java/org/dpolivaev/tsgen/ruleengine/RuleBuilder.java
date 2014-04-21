package org.dpolivaev.tsgen.ruleengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.internal.ConjunctCondition;
import org.dpolivaev.tsgen.ruleengine.internal.ConstantValue;
import org.dpolivaev.tsgen.ruleengine.internal.DefaultStatefulRule;
import org.dpolivaev.tsgen.ruleengine.internal.OrderedValueProviders;
import org.dpolivaev.tsgen.ruleengine.internal.ShuffledValueProviders;
import org.dpolivaev.tsgen.ruleengine.internal.TriggeredStatefulRule;
import org.dpolivaev.tsgen.ruleengine.internal.ValueProviders;
import org.dpolivaev.tsgen.ruleengine.internal.ValueWithRules;
import org.dpolivaev.tsgen.ruleengine.internal.ValueWithRulesProvider;
import org.dpolivaev.tsgen.utils.internal.Utils;


public class RuleBuilder{
    private String targetedPropertyName = null;
    final private List<ValueWithRulesProvider> values = new ArrayList<>();
    private List<RuleBuilder> rules;
    private Set<String> triggeringProperties = Collections.emptySet();
    private Condition condition = Condition.TRUE;
    private Order order = Order.defaultOrder;
    private int previousValueCount;
	private boolean asDefaultRule;

    public RuleBuilder when(
        String... triggeringProperties) {
    	if(! this.triggeringProperties.isEmpty())
    		throw new IllegalStateException("triggering properties already set to" + triggeringProperties);
    	this.triggeringProperties = Utils.set(triggeringProperties);
        return this;
    }

	public RuleBuilder over(Object... valueObjects) {
		rules = null;
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
    	return new ValueWithRules(value, Collections.<RuleBuilder> emptyList());
    }

    public RuleBuilder with(RuleBuilder... rules) {
        return with(Arrays.asList(rules));
    }

    public RuleBuilder with(Collection<? extends RuleBuilder> rules) {
	if(this.rules == null){
		this.rules = new ArrayList<>(rules);
            ListIterator<ValueWithRulesProvider> listIterator = values.listIterator(previousValueCount);
            while (listIterator.hasNext()) {
                ValueWithRulesProvider valueProvider = listIterator.next();
                listIterator.set(new ValueWithRules(valueProvider, this.rules));
            }
	}
	else
		this.rules.addAll(rules);
        return this;
    }
    
    public RuleBuilder with(Strategy strategy){
    	new StrategyMerger().withTrigger(triggeringProperties).withCondition(condition).moveRuleFrom(strategy).to(this);
    	return this;
    }

    public RuleBuilder iterate(
        String targetedPropertyName) {
        this.targetedPropertyName = targetedPropertyName;
        return this;
    }

    public RuleBuilder ordered() {
        order = Order.ORDERED;
        return this;
    }

    public RuleBuilder orderedThenShuffled() {
        order = Order.ORDERED_THEN_SHUFFLED;
        return this;
    }

    public RuleBuilder shuffled() {
        order = Order.SHUFFLED;
        return this;
    }

    public RuleBuilder asDefaultRule() {
    	asDefaultRule = true;
    	return this;
    }
    
    public RuleBuilder asTriggeredRule() {
    	asDefaultRule = false;
    	return this;
    }
    
    public Rule create(){
    	if(asDefaultRule)
    		return new DefaultStatefulRule(triggeringProperties, this.condition, this.targetedPropertyName, //
    				ruleValues());
        if (triggeringProperties.isEmpty())
            this.triggeringProperties = Utils.set();
        return new TriggeredStatefulRule(triggeringProperties, this.condition, this.targetedPropertyName, //
            ruleValues());
    	
    }
    
    public Collection<RuleBuilder> asRules(){
    	return rules;
    }

    ValueProviders ruleValues() {
        ValueWithRulesProvider[] valueProviders = this.values.toArray(new ValueWithRulesProvider[values.size()]);
        OrderedValueProviders orderedValueProviders = new OrderedValueProviders(valueProviders);
        ValueProviders ruleValues = order == Order.ORDERED || ! orderedValueProviders.containsMultipleValues() ? orderedValueProviders : new ShuffledValueProviders(orderedValueProviders, order);
        return ruleValues;
    }


    public RuleBuilder _if(Condition condition) {
    	if(this.condition != Condition.TRUE)
    		throw new IllegalStateException("condition already set");
        this.condition = condition;
        return this;
    }

    public static class Factory {
        static public RuleBuilder when(String... triggeringProperties) {
            return new RuleBuilder().when(triggeringProperties);
        }

        static public RuleBuilder with(Strategy strategy) {
            return new RuleBuilder().with(strategy);
        }

        static public RuleBuilder iterate(String property) {
            return new RuleBuilder().iterate(property);
        }

        static public RuleBuilder skip() {
            return new RuleBuilder().skip();
        }

        static public RuleBuilder _if(Condition condition) {
            return new RuleBuilder()._if(condition);
        }
    }

	public RuleBuilder skip() {
		return iterate(" skip ").over(new ValueProvider() {
			
			@Override
			public Object value(PropertyContainer propertyContainer) {
				throw new InvalidCombinationException();
			}
		});
	}
	

	public RuleBuilder disable() {
		return over(SpecialValue.DISABLED_RULE);
	}
	public void addCondition(final Condition newCondition) {
    	if(! newCondition.equals(Condition.TRUE)){
    		if(condition.equals(Condition.TRUE))
    			this.condition = newCondition;
    		else
    			this.condition =  new ConjunctCondition(newCondition, condition);
    	}
	}

	public void addTriggeringProperty(String trigger) {
		if(triggeringProperties.isEmpty())
			triggeringProperties = new HashSet<String>();
		triggeringProperties.add(trigger);
	}

}