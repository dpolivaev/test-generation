package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.Collections;
import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.EngineState;

public class DefaultStatefulRule extends StatefulRule {
    private Set<String> triggeringProperties;

	public DefaultStatefulRule(Set<String> triggeredBy, Condition condition, String targetedPropertyName, ValueProviders ruleValues) {
        super(condition, targetedPropertyName, ruleValues);
		this.triggeringProperties = triggeredBy;
    }

	public DefaultStatefulRule(Condition condition, String targetedPropertyName, ValueProviders ruleValues) {
		this(Collections.<String>emptySet(), condition, targetedPropertyName, ruleValues);
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

    private boolean areTriggeringPropertiesSet(EngineState engineState) {
		for (String name:triggeringProperties)
			if(! engineState.containsTriggeringPropertyValue(name))
				return false;
		return true;
	}

	@Override
    public boolean isDefaultRule() {
        return true;
    }

    @Override
    public boolean forcesIteration() {
        return false;
    }

}