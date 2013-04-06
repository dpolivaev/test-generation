package ruleengine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class State implements PropertyHolder {
	private StringBuilder assignedPropertiesAsString = new StringBuilder();
	private int count = 0;
	private Map<String, Object> properties = new LinkedHashMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see ruleengine.PropertySet#setPropertyValue(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setPropertyValue(String name, Object value) {
		properties.put(name, value);
		if (assignedPropertiesAsString.length() > 0)
			assignedPropertiesAsString.append('\t');
		this.assignedPropertiesAsString.append(name).append("=").append(value);
	}

	public void nextIteration() {
		this.count++;
		assignedPropertiesAsString.setLength(0);

	}

	public String getAssignedPropertiesAsString() {
		return count + " : " + assignedPropertiesAsString + '\n';
	}

	@Override
	public boolean containsPropertyValue(String name) {
		return properties.containsKey(name);
	}

	@Override
	public boolean containsPropertyValues(Set<String> names) {
		return properties.keySet().containsAll(names);
	}

}