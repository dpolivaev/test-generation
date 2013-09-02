package ruleengine.internal;

import java.util.Set;

import ruleengine.Rule;

public class TriggeredRuleKey{
    final public Set<String> triggeringProperties;
    final public String targetedProperty;

    public TriggeredRuleKey(Set<String> triggeringProperties, String targetedProperty) {
        super();
        this.triggeringProperties = triggeringProperties;
        this.targetedProperty = targetedProperty;
    }

    public static TriggeredRuleKey from(Rule rule) {
        return new TriggeredRuleKey(rule.getTriggeringProperties(), rule.getTargetedPropertyName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + targetedProperty.hashCode();
        result = prime * result + triggeringProperties.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TriggeredRuleKey other = (TriggeredRuleKey) obj;
        if (!targetedProperty.equals(other.targetedProperty))
            return false;
        if (!triggeringProperties.equals(other.triggeringProperties))
            return false;
        return true;
    }

}