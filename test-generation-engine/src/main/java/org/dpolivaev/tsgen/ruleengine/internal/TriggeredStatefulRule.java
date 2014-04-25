package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.EngineState;

public class TriggeredStatefulRule extends StatefulRule {
	final private String ruleName;
    public TriggeredStatefulRule(Set<String> triggeredBy, Condition condition, String targetedPropertyName,
            ValueProviders ruleValues) {
       this(null, triggeredBy, condition, targetedPropertyName, ruleValues);
    }
    
    public TriggeredStatefulRule(String ruleName, Set<String> triggeredBy, Condition condition, String targetedPropertyName,
        ValueProviders ruleValues) {
        super(triggeredBy, condition, targetedPropertyName, ruleValues);
        this.ruleName = ruleName != null ? ruleName : " " + defaultRuleName();
    }

	private int defaultRuleName() {
		return System.identityHashCode(this);
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

    @Override
	public boolean isTopRule() {
		return getTriggeringProperties().isEmpty();
	}
    
    @Override
    public void propertyValueSet(PropertyAssignedEvent event) {
    	if (isValueAddedToCurrentCombination())
    		addDependencies(event);
    	else if (! isTopRule() 
			&& getTriggeringProperties().contains(event.getTargetedPropertyName())
			&& event.containsTriggeredProperties(getTriggeringProperties())) {
    		EngineState engineState = event.getState();
    		if (getCondition().isSatisfied(engineState)) {
    			addValueWithRules(engineState);
    			if (event.isValueChanged())
    				setBlocksRequiredProperties(true);
    		}
    	}
    }

    @Override
    public boolean isDefaultRule() {
        return false;
    }

	@Override
	public String getTriggeredRuleKey() {
		return  ruleName;
	}
}