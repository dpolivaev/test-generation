package ruleengine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
abstract class StatefulRule implements Rule {

    final private String targetedPropertyName;
    final private Values values;
    final private Set<Rule> dependentRules;
    final private Condition condition;

    private boolean valueAlreadyAddedToCurrentCombination;
    private boolean blocksRequiredProperties;

    public StatefulRule(Condition condition, String targetedPropertyName, Values ruleValues) {
        this.condition = condition;
        this.targetedPropertyName = targetedPropertyName;
        this.values = ruleValues;
        this.blocksRequiredProperties = false;
        this.setValueAlreadyAddedToCurrentCombination(false);
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
        setValueAlreadyAddedToCurrentCombination(true);
        boolean useNextValue = dependentRules.isEmpty();
        if (useNextValue) {
            values.next();
            addRules(engineState);
        }
        setValue(engineState, useNextValue);
    }

    private void setValue(EngineState engineState, boolean useNextValue) {
        Object value = values.currentValue();
        engineState.setPropertyValue(this, value, useNextValue);
    }

    private void addRules(EngineState engineState) {
        Collection<Rule> rules = values.currentValueRelatedRules();
        for (Rule rule : rules)
            engineState.currentStrategy().addRule(rule);
    }

    @Override
    public void propertyValueSet(PropertyAssignedEvent event) {
        if (isValueAlreadyAddedToCurrentCombination())
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
        if (isValueAlreadyAddedToCurrentCombination()) {
            for (Rule rule : dependentRules)
                rule.propertyCombinationFinished(engineState);
            if (!isBlockedBy(dependentRules)) {
                removeValueRelatedRules(engineState);
                dependentRules.clear();
                if (values.allValuesUsed())
                    setBlocksRequiredProperties(false);
            }
            setValueAlreadyAddedToCurrentCombination(false);
        }
    }

    private void removeValueRelatedRules(EngineState engineState) {
        Collection<Rule> relatedRules = values.currentValueRelatedRules();
        for (Rule rule : relatedRules) {
            engineState.currentStrategy().removeRule(rule);
        }
    }

    @Override
    public void setBlocksRequiredProperties() {
        setBlocksRequiredProperties(!false);
    }

    @Override
    public boolean isActive() {
        return isValueAlreadyAddedToCurrentCombination();
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

    protected boolean isValueAlreadyAddedToCurrentCombination() {
        return valueAlreadyAddedToCurrentCombination;
    }

    private void setValueAlreadyAddedToCurrentCombination(boolean valueAlreadyAddedToCurrentCombination) {
        this.valueAlreadyAddedToCurrentCombination = valueAlreadyAddedToCurrentCombination;
    }

    protected Condition getCondition() {
        return condition;
    }

    protected void setBlocksRequiredProperties(boolean blocksRequiredProperties) {
        this.blocksRequiredProperties = blocksRequiredProperties;
    }

}
