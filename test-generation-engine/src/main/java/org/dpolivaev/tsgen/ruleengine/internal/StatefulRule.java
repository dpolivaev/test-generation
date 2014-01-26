package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.EngineState;
import org.dpolivaev.tsgen.ruleengine.Rule;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;

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
    public Set<String> calculateRequiredProperties(Set<String> dependencies) {
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
        Object value;
        for (;;){
        	value = valueProviders.currentProvider().value(engineState);
        	if(value == SpecialValue.SKIP && useNextValue){
                if (valueProviders.allValuesUsed())
                    setBlocksRequiredProperties(false);
                valueProviders.next();
        	}
        	else
        		break;
        }
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
                clearDependentRules(engineState);
                if (valueProviders.allValuesUsed())
                    setBlocksRequiredProperties(false);
            }
            this.valueAlreadyAddedToCurrentCombination = false;
        }
    }

    public void clearDependentRules(EngineState engineState) {
        for (Rule dependentRule : dependentRules){
        	dependentRule.clearDependentRules(engineState);
        }
        Collection<Rule> valueRelatedRules = valueProviders.currentProvider().rules(engineState);
        for (Rule removedRule : valueRelatedRules)
            engineState.currentStrategy().removeRule(removedRule);
        dependentRules.clear();
    }

    @Override
    public void setBlocksRequiredProperties(boolean block) {
        this.blocksRequiredProperties = block;
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
        StringBuilder stringBuilder = new StringBuilder(getClass().getSimpleName()).append(" [");
        appendTriggeringPropertyList(stringBuilder);
        stringBuilder.append(targetedPropertyName).append("]").append(valueProviders);
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

    @Override
    public boolean forcesIteration() {
        return valueProviders.containsMultipleValues();
    }
}
