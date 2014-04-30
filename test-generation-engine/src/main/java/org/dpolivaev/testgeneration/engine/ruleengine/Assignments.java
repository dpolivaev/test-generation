package org.dpolivaev.testgeneration.engine.ruleengine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.utils.internal.Utils;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class Assignments implements PropertyContainer {
    private Map<String, Assignment> assignments = new LinkedHashMap<>();
    private int combinationCounter;


    public void add(Assignment assignment) {
        String targetedPropertyName = assignment.getTargetedPropertyName();
        if (assignments.containsKey(targetedPropertyName))
            throw new PropertyAlreadyAssignedException(targetedPropertyName);
        assignments.put(targetedPropertyName, assignment);
	}

	public boolean containsProperties(Set<String> names) {
        return assignments.keySet().containsAll(names);
    }

    public boolean containsProperty(String name) {
        return assignments.keySet().contains(name);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        if(! assignments.containsKey(name))
            return (T)SpecialValue.UNDEFINED;
        return (T)assignments.get(name).value;
	}

	@Override
	public Assignment getAssignment(String name) {
		return assignments.get(name);
	}

    public Collection<Assignment> getAssignments() {
        return Collections.unmodifiableCollection(assignments.values());
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

    public Set<String> assignedProperties() {
        return assignments.keySet();
    }
    
    public boolean containsPropertyValues(Set<String> names) {
        return containsProperties(names);
    }
    
    public boolean containsPropertyValue(String name) {
        return containsProperty(name);
    }

    public Set<String> availableProperties(String startWith) {
        HashSet<String> availableProperties = new HashSet<>(); 
        Utils.addMatchingStrings(availableProperties, startWith, assignedProperties());
        return availableProperties;
    }

    public int getCombinationCounter() {
        return combinationCounter;
    }

    public void startNewCombination() {
        this.combinationCounter++;
        assignments.clear();
    }

    public void resetCounter() {
        this.combinationCounter = 0;
    }

	@Override
	public boolean containsTriggeringPropertyValue(String name) {
		Assignment assignment = assignments.get(name);
		return assignment != null && assignment.rule.isDefaultRule() == false;
	}
}