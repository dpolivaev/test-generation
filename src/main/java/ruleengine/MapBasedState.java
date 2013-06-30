package ruleengine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class MapBasedState implements PropertyMap {
	private StringBuilder assignedPropertiesAsString = new StringBuilder();
	private int count = 0;
	private Map<String, Object> properties = new LinkedHashMap<String, Object>();

	@Override
	public void setPropertyValue(Rule rule, Object value) {
		properties.put(rule.getTargetedPropertyName(), value);
		if (assignedPropertiesAsString.length() > 0)
			assignedPropertiesAsString.append('\t');
		this.assignedPropertiesAsString.append(rule.getTargetedPropertyName())
			.append("=").append(value);
	}

	public void nextCombination() {
		this.count++;
        properties.clear();
		assignedPropertiesAsString.setLength(0);

	}

	public String getAssignedPropertiesAsString() {
		return count + " : " + assignedPropertiesAsString + '\n';
	}

    @Override
    public boolean containsPropertyValues(Set<String> names) {
        return properties.keySet().containsAll(names);
    }

    public boolean containsPropertyValue(String name) {
        return properties.keySet().contains(name);
    }

	@Override
    public Object get(String name) {
		return properties.get(name);
	}

}