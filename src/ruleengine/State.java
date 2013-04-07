package ruleengine;

import java.util.Set;

interface State {

	void setPropertyValue(Rule rule, Object value);

	boolean containsPropertyValues(Set<String> names);

}