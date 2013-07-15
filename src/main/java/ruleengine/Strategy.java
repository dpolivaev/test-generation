package ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Strategy {
    private Map<String, Rule> rules = new LinkedHashMap<>();
    private Map<String, Rule> defaultRules = new LinkedHashMap<>();
    private Map<String, Rule> topRules = new LinkedHashMap<>();
    private Map<String, Collection<Rule>> triggeredRules = new LinkedHashMap<>();

    public void addRule(StatefulRuleBuilder builder) {
        addRule(builder.asRule());
    }

	public void addRule(Rule rule) {
        String targetedPropertyName = rule.getTargetedPropertyName();
        Rule existingRule = rules.get(targetedPropertyName);
        if (existingRule != null)
            rules.put(targetedPropertyName, existingRule.combineWith(rule));
        else
            rules.put(targetedPropertyName, rule);
	}

	public Collection<Rule> rules() {
		ArrayList<Rule> arrayList = new ArrayList<Rule>();
		arrayList.addAll(rules.values());
		return arrayList;
	}

    public Rule getRulesForProperty(String propertyName) {
        return rules.get(propertyName);
    }

    public void removeRule(Rule rule) {
        String targetedPropertyName = rule.getTargetedPropertyName();
        Rule reducedRule = rules.get(targetedPropertyName).without(rule);
        if (reducedRule == null)
            rules.remove(targetedPropertyName);
        else
            rules.put(targetedPropertyName, reducedRule);
    }

}