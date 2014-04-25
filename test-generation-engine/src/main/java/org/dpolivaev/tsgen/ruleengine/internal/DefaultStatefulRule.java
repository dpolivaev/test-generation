package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.EngineState;

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
	public String getTriggeredRuleKey() {
		return null;
	}

}