package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.Collection;
import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.EngineState;
import org.dpolivaev.tsgen.ruleengine.Rule;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;

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

	protected void addRules(EngineState engineState) {
	    Collection<RuleBuilder> rules = valueProviders.currentProvider().rules(engineState);
	    for (RuleBuilder ruleCreator : rules) {
		ruleCreator.addTriggeringProperty(targetedPropertyName);
		Rule rule = ruleCreator.create();
		if(rule.isDefaultRule())
			engineState.addRule(rule);
		else {
			throw new IllegalArgumentException();
		}
	        createdRules.add(rule);
	    }
	}

}