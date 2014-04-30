package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import java.util.Collection;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.EngineState;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;

public class DefaultStatefulRule extends StatefulRule {

	public DefaultStatefulRule(Set<String> triggeredBy, Condition condition, String targetedPropertyName, ValueProviders ruleValues) {
        super(triggeredBy, condition, targetedPropertyName, ruleValues);
    }

    @Override
    public void propertyRequired(EngineState engineState) {
        if (isSatisfied(engineState)) {
            addValueWithRules(engineState);
        }
    }

	private boolean isSatisfied(EngineState engineState) {
		return areTriggeringPropertiesSet(engineState) && getCondition().isSatisfied(engineState);
	}

	@Override
    public boolean isDefaultRule() {
        return true;
    }

    @Override
    public boolean forcesIteration() {
        return false;
    }

	@Override
	public boolean isTopRule() {
		return false;
	}

	@Override
	public String getRuleKey() {
		return getTargetedPropertyName();
	}

}