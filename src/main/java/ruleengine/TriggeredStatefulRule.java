package ruleengine;

import java.util.Set;

public class TriggeredStatefulRule extends StatefulRule {
    final private Set<String> triggeringProperties;
    public TriggeredStatefulRule(Set<String> triggeredBy, Condition condition, String targetedPropertyName,
        Values ruleValues) {
        super(condition, targetedPropertyName, ruleValues);
        this.triggeringProperties = triggeredBy;
    }

    @Override
    public Set<String> getTriggeringProperties() {
        return triggeringProperties;
    }

    @Override
    public void propertyValueSet(PropertyAssignedEvent event) {
        if (isValueAlreadyAddedToCurrentCombination())
            addDependencies(event);
        else if (triggeringProperties.contains(event.getTargetedPropertyName())
            && event.containsPropertyValues(triggeringProperties) && getCondition().isSatisfied())
            addValueWithRules(event.getState());
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