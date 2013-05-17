package ruleengine;

import static ruleengine.TestUtils.set;

import java.util.Collections;
import java.util.Set;

public class StatefulRuleBuilder {
    private String targetedPropertyName = null;
    private Object[] values = null;
    private Set<String> triggeringProperties = Collections.emptySet();
    private Condition condition = Condition.TRUE;

    public StatefulRuleBuilder when(
        String... triggeringProperties) {
        this.triggeringProperties = set(triggeringProperties);
        return this;
    }

    public StatefulRuleBuilder over(String... values) {
        this.values = values;
        return this;
    }

    public StatefulRuleBuilder iterate(
        String targetedPropertyName) {
        this.targetedPropertyName = targetedPropertyName;
        return this;
    }

    public StatefulRule build() {
        return new StatefulRule(triggeringProperties, this.condition,
            this.targetedPropertyName, this.values);
    }

    public StatefulRuleBuilder _if(Condition condition) {
        this.condition = condition;
        return this;
    }

    public static class Factory {
        static public StatefulRuleBuilder when(String... triggeringProperties) {
            return new StatefulRuleBuilder().when(triggeringProperties);
        }

        static public StatefulRuleBuilder iterate(String property) {
            return new StatefulRuleBuilder().iterate(property);
        }

        static public StatefulRuleBuilder _if(Condition condition) {
            return new StatefulRuleBuilder()._if(condition);
        }
    }

}