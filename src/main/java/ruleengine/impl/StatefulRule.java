package ruleengine.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ruleengine.Condition;
import ruleengine.EngineState;
import ruleengine.Rule;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public abstract class StatefulRule implements Rule {

    final private String targetedPropertyName;
    final private ValueProviders valueProviders;
    final private Set<Rule> dependentRules;
    final private Condition condition;

    private boolean valueAlreadyAddedToCurrentCombination;
    private boolean blocksRequiredProperties;

    public StatefulRule(Condition condition, String targetedPropertyName, ValueProviders ruleValues) {
        this.condition = condition;
        this.targetedPropertyName = targetedPropertyName;
        this.valueProviders = ruleValues;
        this.blocksRequiredProperties = false;
        this.valueAlreadyAddedToCurrentCombination = false;
        dependentRules = new HashSet<>();
    }

    @Override
    public String getTargetedPropertyName() {
        return targetedPropertyName;
    }

    @Override
    public Set<String> getTriggeringProperties() {
        return Collections.<String> emptySet();
    }

    @Override
    public Set<String> requiredProperties(Set<String> dependencies) {
        Set<String> requiredProperties;
        if (dependencies.isEmpty()) {
            requiredProperties = getTriggeringProperties();
        }
        else {
            requiredProperties = new HashSet<>(getTriggeringProperties());
            requiredProperties.addAll(dependencies);
        }
        return requiredProperties;
    }

    @Override
    public boolean blocksRequiredProperties() {
        return blocksRequiredProperties;
    }

    protected void addValueWithRules(EngineState engineState) {
        boolean useNextValue = dependentRules.isEmpty();
        if (useNextValue) {
            valueProviders.next();
        }
        Object value = valueProviders.currentProvider().value(engineState);
        this.valueAlreadyAddedToCurrentCombination = true;
        if (useNextValue) {
            addRules(engineState);
        }
        engineState.setPropertyValue(this, value, useNextValue);
    }

    private void addRules(EngineState engineState) {
        Collection<Rule> rules = valueProviders.currentProvider().rules(engineState);
        for (Rule rule : rules) {
            if (!rule.isDefaultRule())
                rule.getTriggeringProperties().add(targetedPropertyName);
            engineState.currentStrategy().addRule(rule);
        }
    }

    @Override
    public void propertyValueSet(PropertyAssignedEvent event) {
        if (isValueAddedToCurrentCombination())
            addDependencies(event);
    }

    protected void addDependencies(PropertyAssignedEvent event) {
        if (event.getRequiredProperties().contains(targetedPropertyName)) {
            Rule rule = event.getWorkingRule();
            dependentRules.add(rule);
        }
    }

    private boolean isBlockedBy(Iterable<Rule> rules) {
        for (Rule rule : rules)
            if (rule.blocksRequiredProperties())
                return true;
        return false;
    }

    @Override
    public void propertyCombinationFinished(EngineState engineState) {
        if (isValueAddedToCurrentCombination()) {
            for (Rule rule : dependentRules)
                rule.propertyCombinationFinished(engineState);
            if (!isBlockedBy(dependentRules)) {
                removeValueRelatedRules(engineState);
                dependentRules.clear();
                if (valueProviders.allValuesUsed())
                    setBlocksRequiredProperties(false);
            }
            this.valueAlreadyAddedToCurrentCombination = false;
        }
    }

    private void removeValueRelatedRules(EngineState engineState) {
        Collection<Rule> relatedRules = valueProviders.currentProvider().rules(engineState);
        for (Rule rule : relatedRules) {
            engineState.currentStrategy().removeRule(rule);
        }
    }

    @Override
    public void setBlocksRequiredProperties() {
        setBlocksRequiredProperties(!false);
    }

    @Override
    public boolean isValueAddedToCurrentCombination() {
        return valueAlreadyAddedToCurrentCombination;
    }

    @Override
    public Rule combineWith(Rule rule) {
        return new AlternatingRule(this, rule);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StatefulRule [");
        appendTriggeringPropertyList(stringBuilder);
        stringBuilder.append(targetedPropertyName).append("]");
        return stringBuilder.toString();
    }

    protected void appendTriggeringPropertyList(StringBuilder stringBuilder) {
    }

    @Override
    public Rule without(Rule rule) {
        return null;
    }

    @Override
    public void propertyCombinationStarted(EngineState engineState) {
    }

    @Override
    public void propertyRequired(EngineState engineState) {
    }

    protected Condition getCondition() {
        return condition;
    }

    protected void setBlocksRequiredProperties(boolean blocksRequiredProperties) {
        this.blocksRequiredProperties = blocksRequiredProperties;
    }

    
    @Override
    public boolean forcesIteration() {
        return valueProviders.containsMultipleValues();
    }
}
