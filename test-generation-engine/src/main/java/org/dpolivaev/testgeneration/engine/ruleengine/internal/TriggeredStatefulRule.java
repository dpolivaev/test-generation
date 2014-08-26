package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import java.util.Collection;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.EngineState;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;

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
            setBlocksRequiredPropertiesItself(true);
        if (getCondition().isSatisfied(engineState)) {
            addValue(engineState);
        }
        else
            setBlocksRequiredPropertiesItself(false);
    }

    @Override
	public boolean isTopRule() {
		return getTriggeringProperties().isEmpty();
	}
    
    @Override
    public void propertyValueSet(PropertyAssignedEvent event) {
    	if (isValueAddedToCurrentCombination())
		super.propertyValueSet(event);
    	else if (! isTopRule() 
    			&& getTriggeringProperties().contains(event.getTargetedPropertyName())
    			&& event.containsTriggeredProperties(getTriggeringProperties())) {
    		EngineState engineState = event.getState();
    		if (getCondition().isSatisfied(engineState)) {
    			if(! blocksRequiredPropertiesItself)
    				for(String trigger : getTriggeringProperties()){
    					final Assignment assignment = engineState.getAssignment(trigger);
    					final Rule rule = assignment.rule;
    					if(rule.valueHasChangedNow() && rule.blocksRequiredProperties()) {
    						setBlocksRequiredPropertiesItself(true);
    						break;
    					}
    				}
    			addValue(engineState);
    		}
    	}
    }

    @Override
    public boolean isLazyRule() {
        return false;
    }

	@Override
	public String getRuleKey() {
		return  ruleName;
	}

}