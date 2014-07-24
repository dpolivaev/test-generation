package org.dpolivaev.testgeneration.engine.ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.utils.internal.LinkedMap;



public class Strategy {
    private LinkedMap<String, Rule> lazyRules = new LinkedMap<>();
    private LinkedMap<String, Rule> triggeredRules = new LinkedMap<>();
    private Collection<RuleBuilder> ruleBuilders = new ArrayList<RuleBuilder>();
    
    public void initialize(PropertyContainer propertyContainer){
    	lazyRules.clear();
    	triggeredRules.clear();
    	for(RuleBuilder ruleBuilder : ruleBuilders)
		addRule(ruleBuilder.create(propertyContainer));
    }

    public void addRule(RuleBuilder builder) {
    	ruleBuilders.add(builder);
    }

    public void addLazyRule(RuleBuilder builder) {
    	ruleBuilders.add(builder.asLazyRule());
    }

	public void addRule(Rule rule) {
		final LinkedMap<String, Rule> rules = rulesLike(rule);
		addRule(rules, rule);
    }

	private LinkedMap<String, Rule> rulesLike(Rule newRule) {
		final LinkedMap<String, Rule> rules;
        if (newRule.isLazyRule())
			rules = lazyRules;
		else
			rules = triggeredRules;
		return rules;
	}

    private void addRule(LinkedMap<String, Rule> rules, Rule rule) {
        final String key = rule.getRuleKey();
		Rule existingRule = rules.get(key);
        if (existingRule != null)
            rules.put(key, existingRule.combineWith(rule));
        else
            rules.add(key, rule);
    }

    public Rule getLazyRulesForProperty(String propertyName) {
        Rule rule = lazyRules.get(propertyName);
        if(rule == null)
            return RuleBuilder.Factory.iterate(propertyName).over(SpecialValue.UNDEFINED).asLazyRule().create();
        return rule;
    }

    public void removeRule(Rule rule) {
		removeRule(rulesLike(rule), rule.getRuleKey(), rule);
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

    public Iterable<Rule> lazyRules() {
        return lazyRules.iterable();
    }

    public Iterable<Rule> triggeredRules() {
        return triggeredRules.iterable();
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

	public Set<String> availableLazyProperties() {
        return lazyRules.keySet();
    }

	public Collection<RuleBuilder> giveBuilders() {
		Collection<RuleBuilder> ruleBuilders = this.ruleBuilders;
		this.ruleBuilders = new ArrayList<RuleBuilder>();
		return ruleBuilders;
	}

	public boolean containsCompatibleRule(Rule rule) {
		return rulesLike(rule).containsKey(rule.getRuleKey());
	}

	public void addTemporaryRule(Rule creatingRule, Rule rule) {
		if(creatingRule == null || rule.isLazyRule() || triggeredRules.containsKey(rule.getRuleKey()))
			addRule(rule);
		else {
			triggeredRules.insertBefore(creatingRule.getRuleKey(), rule.getRuleKey(), rule);
		}
	}
}