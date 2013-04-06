package ruleengine;

import java.util.Set;

interface PropertyHolder {

	void setPropertyValue(String name, Object value);

	boolean containsPropertyValue(String name);

	boolean containsPropertyValues(Set<String> names);

}