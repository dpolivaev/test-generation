package ruleengine.impl;

import java.util.Set;

import ruleengine.Condition;
import ruleengine.EngineState;
import ruleengine.ValueProviders;

public class TriggeredStatefulRule extends StatefulRule {
    final private Set<String> triggeringProperties;
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
    public void propertyValueSet(PropertyAssignedEvent event) {
        if (isValueAddedToCurrentCombination())
            addDependencies(event);
        else if (triggeringProperties.contains(event.getTargetedPropertyName()) && event.containsPropertyValues(triggeringProperties)) {
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