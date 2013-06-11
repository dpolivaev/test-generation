package ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

class RuleContainer {
	private Map<String, Rule> rules = new LinkedHashMap<String, Rule>();

	public void addRule(Rule rule) {
        String targetedPropertyName = rule.getTargetedPropertyName();
        Rule existingRule = rules.get(targetedPropertyName);
        if (existingRule != null)
            rules.put(targetedPropertyName, existingRule.combineWith(rule));
        else
            rules.put(rule.getTargetedPropertyName(), rule);
	}

	public Collection<Rule> rules() {
		ArrayList<Rule> arrayList = new ArrayList<Rule>();
		arrayList.addAll(rules.values());
		return arrayList;
	}

	public boolean hasRuleForProperty(String propertyName) {
		return rules.containsKey(propertyName);
	}

}