package ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Strategy {
    private Map<String, Rule> defaultRules = new LinkedHashMap<>();
    private Map<String, Rule> topRules = new LinkedHashMap<>();
    private Map<String, Rule> triggeredRules = new LinkedHashMap<>();

    public void addRule(StatefulRuleBuilder builder) {
        addRule(builder.asRule());
    }

	public void addRule(Rule rule) {
        if (rule.isDefaultRule()) {
            addRule(defaultRules, rule);
        }
        else {
            if (rule.getTriggeringProperties().isEmpty())
                addRule(topRules, rule);
            else {
                addRule(triggeredRules, rule);
            }
        }
    }

    private void addRule(Map<String, Rule> rules, Rule rule) {
        String targetedPropertyName = rule.getTargetedPropertyName();
        Rule existingRule = rules.get(targetedPropertyName);
        if (existingRule != null)
            rules.put(targetedPropertyName, existingRule.combineWith(rule));
        else
            rules.put(targetedPropertyName, rule);
    }

    private Collection<Rule> copy(Map<String, Rule> original) {
        ArrayList<Rule> arrayList = new ArrayList<Rule>();
        arrayList.addAll(original.values());
		return arrayList;
    }

    public Rule getDefaultRulesForProperty(String propertyName) {
        return defaultRules.get(propertyName);
    }

    public void removeRule(Rule rule) {
        if (rule.isDefaultRule()) {
            removeRule(defaultRules, rule);
        }
        else {
            if (rule.getTriggeringProperties().isEmpty())
                removeRule(topRules, rule);
            else {
                removeRule(triggeredRules, rule);
            }
        }
    }

    public void removeRule(Map<String, Rule> rules, Rule rule) {
        String targetedPropertyName = rule.getTargetedPropertyName();
        Rule reducedRule = rules.get(targetedPropertyName).without(rule);
        if (reducedRule == null)
            rules.remove(targetedPropertyName);
        else
            rules.put(targetedPropertyName, reducedRule);
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
}