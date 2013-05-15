package ruleengine;

import static ruleengine.TestUtils.set;

import java.util.Collections;
import java.util.Set;

class StatefulRuleBuilder {
    private String targetedPropertyName = null;
    private Object[] values = null;
    private Set<String> triggeringProperties = Collections.emptySet();
    private Condition condition = Condition.TRUE;

    public StatefulRuleBuilder withTriggeringProperties(
        String... triggeringProperties) {
        this.triggeringProperties = set(triggeringProperties);
        return this;
    }

    public StatefulRuleBuilder withValues(String... values) {
        this.values = values;
        return this;
    }

    public StatefulRuleBuilder withTargetedPropterty(
        String targetedPropertyName) {
        this.targetedPropertyName = targetedPropertyName;
        return this;
    }

    public StatefulRule build() {
        return new StatefulRule(triggeringProperties, this.condition,
            this.targetedPropertyName, this.values);
    }

    public StatefulRuleBuilder withCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

}