package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.EngineState;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public abstract class StatefulRule implements Rule {

	protected final String targetedPropertyName;
    protected final ValueProviders valueProviders;
    final private Set<Rule> dependentRules;
    protected final Set<Rule> createdRules;
    private Condition condition;
    private Set<String> triggeringProperties;

    private boolean valueAlreadyAddedToCurrentCombination;
    private boolean blocksRequiredProperties;

    public StatefulRule(Set<String> triggeredBy, Condition condition, String targetedPropertyName, ValueProviders ruleValues) {
        this.triggeringProperties = triggeredBy;
		this.condition = condition;
        this.targetedPropertyName = targetedPropertyName;
        this.valueProviders = ruleValues;
        this.blocksRequiredProperties = false;
        this.valueAlreadyAddedToCurrentCombination = false;
        dependentRules = new HashSet<>();
        createdRules = new HashSet<>();
    }

    @Override
    public String getTargetedPropertyName() {
        return targetedPropertyName;
    }

    public Set<String> getTriggeringProperties() {
        return triggeringProperties;
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
        if(value != SpecialValue.DISABLED_RULE){
        	if (useNextValue) {
        		addRules(engineState);
        	}
        	engineState.setPropertyValue(this, value, useNextValue);
        }
    }

	protected void addRules(EngineState engineState) {
	    Collection<RuleBuilder> rules = valueProviders.currentProvider().rules(engineState);
	    for (RuleBuilder ruleCreator : rules) {
		ruleCreator.addTriggeringProperty(targetedPropertyName);
		Rule rule = ruleCreator.create();
		engineState.addRule(rule);
	        createdRules.add(rule);
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
        for (Rule removedRule : createdRules)
            engineState.currentStrategy().removeRule(removedRule);
        createdRules.clear();
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
    	if(getTargetedPropertyName() == null)
    		return rule;
    	else
    		return new AlternatingRule(this, rule);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(getClass().getSimpleName()).append(" [");
        appendTriggeringPropertyList(stringBuilder);
        stringBuilder.append(targetedPropertyName).append("]").append(valueProviders);
        return stringBuilder.toString();
    }

    @Override
    public Rule without(Rule rule) {
    	if(this != rule)
    		throw new IllegalArgumentException();
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

    public Rule toTriggeredRule(){
    	return new TriggeredStatefulRule(new HashSet<String>(), condition, targetedPropertyName, valueProviders);
    }
    
    @Override
    public void checkRuleCompatibility(Rule rule){
    	if (isDefaultRule() != rule.isDefaultRule())
    		throw new IllegalArgumentException("triggering and not triggering rules can not be combined");
    	String targetedPropertyName = getTargetedPropertyName();
    	String otherTargetedPropertyName = rule.getTargetedPropertyName();
		if (targetedPropertyName != null && otherTargetedPropertyName != null && !targetedPropertyName.equals(otherTargetedPropertyName))
    		throw new IllegalArgumentException("rules with different targeted property names can not be combined");
    }

    protected void appendTriggeringPropertyList(StringBuilder stringBuilder) {
        if (!triggeringProperties.isEmpty())
            stringBuilder.append(triggeringProperties).append(" -> ");
    }

    protected boolean areTriggeringPropertiesSet(EngineState engineState) {
		for (String name:triggeringProperties)
			if(! engineState.containsTriggeringPropertyValue(name))
				return false;
		return true;
	}
}
