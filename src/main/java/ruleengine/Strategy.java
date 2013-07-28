package ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Strategy {
    private Map<String, Rule> defaultRules = new LinkedHashMap<>();
    private Map<String, Rule> topRules = new LinkedHashMap<>();
    private Map<TriggeredRuleKey, Rule> triggeredRules = new LinkedHashMap<>();

    public void addRule(StatefulRuleBuilder builder) {
        addRule(builder.asRule());
    }

    public void addDefaultRule(StatefulRuleBuilder builder) {
        addRule(builder.asDefaultRule());
    }

	public void addRule(Rule rule) {
        if (rule.isDefaultRule()) {
            addRule(defaultRules, rule.getTargetedPropertyName(), rule);
        }
        else {
            if (rule.getTriggeringProperties().isEmpty())
                addRule(topRules, rule.getTargetedPropertyName(), rule);
            else {
                addRule(triggeredRules, TriggeredRuleKey.from(rule), rule);
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
            throw new UnknownPropertyException(propertyName);
        return rule;
    }

    public void removeRule(Rule rule) {
        if (rule.isDefaultRule()) {
            removeRule(defaultRules, rule.getTargetedPropertyName(), rule);
        }
        else {
            if (rule.getTriggeringProperties().isEmpty())
                removeRule(topRules, rule.getTargetedPropertyName(), rule);
            else {
                removeRule(triggeredRules, TriggeredRuleKey.from(rule), rule);
            }
        }
    }

    private <T> void removeRule(Map<T, Rule> rules, T key, Rule rule) {
        Rule reducedRule = rules.get(key).without(rule);
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
        combinedStrategy.topRules.putAll(topRules);
        combinedStrategy.defaultRules.putAll(defaultRules);
        combinedStrategy.triggeredRules.putAll(triggeredRules);
        for(Rule rule : anotherStrategy.topRules.values())
            combinedStrategy.addRule(rule);
        for(Rule rule : anotherStrategy.defaultRules.values())
            combinedStrategy.addRule(rule);
        for(Rule rule : anotherStrategy.triggeredRules.values())
            combinedStrategy.addRule(rule);
        return combinedStrategy;
    }
}