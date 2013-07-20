package ruleengine;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class MapBasedState {
    private Map<String, PropertyAssignment> propertyAssignments = new LinkedHashMap<>();


    public void setPropertyValue(PropertyAssignment propertyAssignment) {
        String targetedPropertyName = propertyAssignment.getTargetedPropertyName();
        if (propertyAssignments.containsKey(targetedPropertyName))
            throw new PropertyAlreadyAssignedException();
        propertyAssignments.put(targetedPropertyName, propertyAssignment);
	}

	public void nextCombination() {
        propertyAssignments.clear();
	}

    public boolean containsProperties(Set<String> names) {
        return propertyAssignments.keySet().containsAll(names);
    }

    public boolean containsProperty(String name) {
        return propertyAssignments.keySet().contains(name);
    }

    public Object get(String name) {
        return propertyAssignments.get(name).value;
	}

    public Map<String, PropertyAssignment> getAssignments() {
        return Collections.unmodifiableMap(propertyAssignments);
    }

    public Collection<Rule> firedRules() {
        return Collections.unmodifiableCollection(Maps.transformValues(propertyAssignments,
            new Function<PropertyAssignment, Rule>() {
                @Override
                public Rule apply(PropertyAssignment assignment) {
                    return assignment.rule;
                }
            }).values());
    }

}