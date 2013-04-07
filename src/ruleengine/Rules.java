package ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

class Rules {
	private Map<String, Rule> rules = new LinkedHashMap<String, Rule>();

	public void addRule(Rule rule) {
		rules.put(rule.getTargetedPropertyName(), rule);
	}

	public Collection<Rule> values() {
		ArrayList<Rule> arrayList = new ArrayList<Rule>();
		arrayList.addAll(rules.values());
		return arrayList;
	}

	public boolean hasRuleForProperty(String propertyName) {
		return rules.containsKey(propertyName);
	}

}