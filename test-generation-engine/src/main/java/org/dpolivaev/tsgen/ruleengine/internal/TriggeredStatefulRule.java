package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.EngineState;

public class TriggeredStatefulRule extends StatefulRule {
    private Set<String> triggeringProperties;
    public TriggeredStatefulRule(Set<String> triggeredBy, Condition condition, String targetedPropertyName,
        ValueProviders ruleValues) {
        super(condition, targetedPropertyName, ruleValues);
        this.triggeringProperties = triggeredBy;
    }

    @Override
    public Set<String> getTriggeringProperties() {
        return triggeringProperties;
    }

    @Override
    public void propertyCombinationStarted(EngineState engineState) {
    	if(! isTopRule())
    		return;
        if(engineState.getCombinationCounter() == 1)
            setBlocksRequiredProperties(true);
        if (getCondition().isSatisfied(engineState)) {
            addValueWithRules(engineState);
        }
        else
            setBlocksRequiredProperties(false);
    }

	private boolean isTopRule() {
		return getTriggeringProperties().isEmpty();
	}
    
    @Override
    public void propertyValueSet(PropertyAssignedEvent event) {
    	if (isValueAddedToCurrentCombination())
    		addDependencies(event);
    	else if (! isTopRule() 
    			&& triggeringProperties.contains(event.getTargetedPropertyName()) 
    			&& event.containsPropertyValues(triggeringProperties)) {
    		EngineState engineState = event.getState();
    		if (getCondition().isSatisfied(engineState)) {
    			addValueWithRules(engineState);
    			if (event.isValueChanged())
    				setBlocksRequiredProperties(true);
    		}
    	}
    }

    @Override
    protected void appendTriggeringPropertyList(StringBuilder stringBuilder) {
        if (!triggeringProperties.isEmpty())
            stringBuilder.append(triggeringProperties).append(" -> ");
    }

    @Override
    public boolean isDefaultRule() {
        return false;
    }
}