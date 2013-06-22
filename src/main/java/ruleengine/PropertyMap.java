package ruleengine;

import java.util.Set;

interface PropertyMap {

	void setPropertyValue(Rule rule, Object value);

	boolean containsPropertyValues(Set<String> names);

    Object get(String name);

}