package org.dpolivaev.tsgen.ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;



public class Strategy {
    private Map<String, Rule> defaultRules = new LinkedHashMap<>();
    private Map<String, Rule> topRules = new LinkedHashMap<>();
    private Map<String, Rule> triggeredRules = new LinkedHashMap<>();
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

    private <T> void addRule(Map<T, Rule> rules, T key, Rule rule) {
        Rule existingRule = rules.get(key);
        if (existingRule != null)
            rules.put(key, existingRule.combineWith(rule));
        else
            rules.put(key, rule);
    }

    private Collection<Rule> copy(Map<?, Rule> original) {
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

    private <T> void removeRule(Map<T, Rule> rules, T key, Rule rule) {
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