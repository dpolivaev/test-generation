package org.dpolivaev.testgeneration.engine.ruleengine;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.ruleengine.internal.ConjunctCondition;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.ConstantValue;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.LazyStatefulRule;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.OrderedValueProviders;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.ShuffledValueProviders;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.TriggeredStatefulRule;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.ValueProviders;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.ValueWithRules;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.ValueWithRulesProvider;
import org.dpolivaev.testgeneration.engine.utils.internal.Utils;


public class RuleBuilder{
    private String targetedPropertyName = null;
    final private List<ValueWithRulesProvider> values = new ArrayList<>();
    private List<RuleBuilder> rules;
    private Set<String> triggeringProperties = Collections.emptySet();
    private Set<ValueProvider> triggeringPropertyNameProviders = Collections.emptySet();
    private Condition condition = Condition.TRUE;
    private Order order = Order.defaultOrder;
    private int previousValueCount;
	private boolean asLazyRule;
	private String name;
	private ValueProvider nameProvider;
	private String oneTimeTriggeringPropertyName;

    public RuleBuilder when(
        String... triggeringProperties) {
    	if(! this.triggeringProperties.isEmpty())
    		throw new IllegalStateException("triggering properties already set to" + triggeringProperties);
    	this.triggeringProperties = Utils.set(triggeringProperties);
        return this;
    }

	public RuleBuilder when(ValueProvider... nameProviders) {
	if(! this.triggeringPropertyNameProviders.isEmpty())
		throw new IllegalStateException("triggering property name providers already set");
	this.triggeringPropertyNameProviders = Utils.set(nameProviders);
		return this;
	}

	public RuleBuilder over(Object... valueObjects) {
		return over(asList(valueObjects));
	}
	
	public RuleBuilder over(Iterable<Object> valueObjects) {
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

    public RuleBuilder orderedThenShuffledFixLast() {
        order = Order.ORDERED_THEN_SHUFFLED_FIX_LAST;
        return this;
    }

    public RuleBuilder shuffledFixLast() {
        order = Order.SHUFFLED_FIX_LAST;
        return this;
    }

    public RuleBuilder asLazyRule() {
    	asLazyRule = true;
    	return this;
    }
    
    public RuleBuilder asTriggeredRule() {
    	asLazyRule = false;
    	return this;
    }
    
    public Rule create(){
    	if(asLazyRule)
    		return new LazyStatefulRule(triggeringProperties, this.condition, this.targetedPropertyName, //
    				ruleValues());
        if (triggeringProperties.isEmpty())
            this.triggeringProperties = Utils.set();
        final Set<String> ruleTriggeringProperties;
        if(oneTimeTriggeringPropertyName != null && ! triggeringProperties.contains(oneTimeTriggeringPropertyName)){
        	ruleTriggeringProperties = new HashSet<>(triggeringProperties);
        	ruleTriggeringProperties.add(oneTimeTriggeringPropertyName);
        }
        else
        	ruleTriggeringProperties = triggeringProperties;
        oneTimeTriggeringPropertyName = null;
		return new TriggeredStatefulRule(name, ruleTriggeringProperties, this.condition, this.targetedPropertyName, //
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

        static public RuleBuilder when(ValueProvider... nameProviders) {
            return new RuleBuilder().when(nameProviders);
        }

        static public RuleBuilder with(Strategy strategy) {
            return new RuleBuilder().with(strategy);
        }

        static public RuleBuilder iterate(String property) {
            return new RuleBuilder().iterate(property);
        }

        static public RuleBuilder iterate(ValueProvider property) {
            return new RuleBuilder().iterate(property);
        }

        static public RuleBuilder rule(String name) {
            return new RuleBuilder().rule(name);
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
				return SpecialValue.SKIP_COMBINATION;
			}
		});
	}
	

	public RuleBuilder rule(String name) {
		this.name = name;
		return this;
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

	public RuleBuilder iterate(ValueProvider nameProvider) {
		this.nameProvider = nameProvider;
		return this;
	}

	public Rule create(PropertyContainer propertyContainer) {
		if(nameProvider != null)
			targetedPropertyName = nameProvider.value(propertyContainer).toString();
		for(ValueProvider valueProvider : triggeringPropertyNameProviders)
			addTriggeringProperty(valueProvider.value(propertyContainer).toString());
		return create();
	}

	public void setOneTimeTriggeringProperty(String oneTimeTriggeringPropertyName) {
		this.oneTimeTriggeringPropertyName = oneTimeTriggeringPropertyName;
	}

}