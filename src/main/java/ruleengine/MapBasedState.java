package ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MapBasedState {
	private Map<String, Object> properties = new LinkedHashMap<String, Object>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    private Collection<Rule> firedRules = new ArrayList<>();

	public void setPropertyValue(Rule rule, Object value) {
		String targetedPropertyName = rule.getTargetedPropertyName();
        if (properties.containsKey(targetedPropertyName))
            throw new PropertyAlreadyAssignedException();
        properties.put(targetedPropertyName, value);
        firedRules.add(rule);
	}

	public void nextCombination() {
        properties.clear();
        firedRules.clear();

	}

    public boolean containsPropertyValues(Set<String> names) {
        return properties.keySet().containsAll(names);
    }

    public boolean containsPropertyValue(String name) {
        return properties.keySet().contains(name);
    }

    public Object get(String name) {
		return properties.get(name);
	}

    public Collection<Rule> firedRules() {
        return firedRules;
    }

}