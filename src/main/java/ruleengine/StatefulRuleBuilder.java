package ruleengine;

import static ruleengine.TestUtils.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class StatefulRuleBuilder {
    private String targetedPropertyName = null;
    final private Collection<Object> values = new ArrayList<>();
    private Set<String> triggeringProperties = Collections.emptySet();
    private Condition condition = Condition.TRUE;

    public StatefulRuleBuilder when(
        String... triggeringProperties) {
        this.triggeringProperties = set(triggeringProperties);
        return this;
    }

    public StatefulRuleBuilder over(Object... values) {
        this.values.addAll(Arrays.asList(values));
        return this;
    }

    public StatefulRuleBuilder with(Object value, Rule... rules) {
        this.values.add(new ConstantValues.ValueWithRules(value, Arrays.asList(rules)));
        return this;
    }

    public StatefulRuleBuilder iterate(
        String targetedPropertyName) {
        this.targetedPropertyName = targetedPropertyName;
        return this;
    }

    public StatefulRule asRule() {
        return new StatefulRule(triggeringProperties, this.condition, this.targetedPropertyName, this.values.toArray());
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