package ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MapBasedState {
	private StringBuilder assignedPropertiesAsString = new StringBuilder();
	private int count = 0;
	private Map<String, Object> properties = new LinkedHashMap<String, Object>();
    private Collection<Rule> firedRules = new ArrayList<>();

	public void setPropertyValue(Rule rule, Object value) {
		String targetedPropertyName = rule.getTargetedPropertyName();
        if (properties.containsKey(targetedPropertyName))
            throw new PropertyAlreadyAssignedException();
        properties.put(targetedPropertyName, value);
        firedRules.add(rule);
		if (assignedPropertiesAsString.length() > 0)
			assignedPropertiesAsString.append('\t');
		this.assignedPropertiesAsString.append(rule.getTargetedPropertyName())
			.append("=").append(value);
	}

	public void nextCombination() {
		this.count++;
        properties.clear();
        firedRules.clear();
		assignedPropertiesAsString.setLength(0);

	}

	public String getAssignedPropertiesAsString() {
		return count + " : " + assignedPropertiesAsString + '\n';
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