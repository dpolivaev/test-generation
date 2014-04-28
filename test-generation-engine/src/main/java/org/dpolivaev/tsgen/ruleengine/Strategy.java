package org.dpolivaev.tsgen.ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.dpolivaev.tsgen.utils.internal.LinkedMap;



public class Strategy {
    private LinkedMap<String, Rule> defaultRules = new LinkedMap<>();
    private LinkedMap<String, Rule> topRules = new LinkedMap<>();
    private LinkedMap<String, Rule> triggeredRules = new LinkedMap<>();
    private Collection<RuleBuilder> ruleBuilders = new ArrayList<RuleBuilder>();
    
    public void initialize(){
    	defaultRules.clear();
    	topRules.clear();
    	triggeredRules.clear();
    	for(RuleBuilder ruleBuilder : ruleBuilders)
    		addRule(ruleBuilder.create());
    }

    public void addRule(RuleBuilder builder) {
    	ruleBuilders.add(builder);
    }

    public void addDefaultRule(RuleBuilder builder) {
    	ruleBuilders.add(builder.asDefaultRule());
    }

	public void addRule(Rule rule) {
        if (rule.isDefaultRule()) {
            addRule(defaultRules, rule.getTargetedPropertyName(), rule);
        }
        else {
            if (rule.isTopRule())
                addRule(topRules, rule.getTargetedPropertyName(), rule);
            else {
                addRule(triggeredRules, rule.getTriggeredRuleKey(), rule);
            }
        }
    }

    private <T> void addRule(LinkedMap<T, Rule> rules, T key, Rule rule) {
        Rule existingRule = rules.get(key);
        if (existingRule != null)
            rules.put(key, existingRule.combineWith(rule));
        else
            rules.put(key, rule);
    }

    private Collection<Rule> copy(LinkedMap<?, Rule> original) {
        ArrayList<Rule> arrayList = new ArrayList<Rule>();
        arrayList.addAll(original.values());
		return arrayList;
    }

    public Rule getDefaultRulesForProperty(String propertyName) {
        Rule rule = defaultRules.get(propertyName);
        if(rule == null)
            return RuleBuilder.Factory.iterate(propertyName).over(SpecialValue.UNDEFINED).asDefaultRule().create();
        return rule;
    }

    public void removeRule(Rule rule) {
        if (rule.isDefaultRule()) {
            removeRule(defaultRules, rule.getTargetedPropertyName(), rule);
        }
        else {
            if (rule.isTopRule())
                removeRule(topRules, rule.getTargetedPropertyName(), rule);
            else {
                removeRule(triggeredRules, rule.getTriggeredRuleKey(), rule);
            }
        }
    }

    private <T> void removeRule(LinkedMap<T, Rule> rules, T key, Rule rule) {
        Rule containedRule = rules.get(key);
        if(containedRule == null)
        	return;
		Rule reducedRule = containedRule.without(rule);
        if (reducedRule == null)
            rules.remove(key);
        else
            rules.put(key, reducedRule);
    }

    public Collection<Rule> topRules() {
        return copy(topRules);
    }

    public Collection<Rule> defaultRules() {
        return copy(defaultRules);
    }

    public Collection<Rule> triggeredRules() {
        return copy(triggeredRules);
    }

    public Strategy with(Strategy anotherStrategy) {
        Strategy combinedStrategy = new Strategy();
        combinedStrategy.addRules(ruleBuilders);
        combinedStrategy.addRules(anotherStrategy.ruleBuilders);
        return combinedStrategy;
    }

    public void addRules(Collection<RuleBuilder> rules) {
        for(RuleBuilder rule : rules)
            addRule(rule);
	}

	public Set<String> availableDefaultProperties() {
        return defaultRules.keySet();
    }

	public Collection<RuleBuilder> giveBuilders() {
		Collection<RuleBuilder> ruleBuilders = this.ruleBuilders;
		this.ruleBuilders = new ArrayList<RuleBuilder>();
		return ruleBuilders;
	}
    
    
}