package ruleengine;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class Assignments {
    private Map<String, Assignment> assignments = new LinkedHashMap<>();


    public void setPropertyValue(Assignment assignment) {
        String targetedPropertyName = assignment.getTargetedPropertyName();
        if (assignments.containsKey(targetedPropertyName))
            throw new PropertyAlreadyAssignedException();
        assignments.put(targetedPropertyName, assignment);
	}

	public void clear() {
        assignments.clear();
	}

    public boolean containsProperties(Set<String> names) {
        return assignments.keySet().containsAll(names);
    }

    public boolean containsProperty(String name) {
        return assignments.keySet().contains(name);
    }

    public Object get(String name) {
        return assignments.get(name).value;
	}

    public Map<String, Assignment> getAssignments() {
        return Collections.unmodifiableMap(assignments);
    }

    public Collection<Rule> firedRules() {
        return Collections.unmodifiableCollection(Maps.transformValues(assignments,
            new Function<Assignment, Rule>() {
                @Override
                public Rule apply(Assignment assignment) {
                    return assignment.rule;
                }
            }).values());
    }

    public Set<Entry<String, Assignment>> entrySet() {
        return assignments.entrySet();
    }

}