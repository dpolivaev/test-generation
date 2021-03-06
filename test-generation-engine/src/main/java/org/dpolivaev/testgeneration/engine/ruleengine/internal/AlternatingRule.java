package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.ruleengine.EngineState;
import org.dpolivaev.testgeneration.engine.ruleengine.InconsistentRuleException;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;

public class AlternatingRule implements Rule {
    private final List<Rule> rules;
    private Rule activeRule = null;

    public AlternatingRule(Rule oldRule, Rule newRule) {
    	if(oldRule.getTargetedPropertyName() == null)
    		throw new IllegalArgumentException();
    	rules = new ArrayList<>();
        rules.add(oldRule);
        combineWith(newRule);
    }

    @Override
    public String getTargetedPropertyName() {
        return firstRule().getTargetedPropertyName();
    }

    private Rule firstRule() {
        return rules.get(0);
    }

    @Override
    public Set<String> calculateRequiredProperties(Set<String> dependencies) {
        return firstRule().calculateRequiredProperties(dependencies);
    }

    @Override
    public boolean blocksRequiredProperties() {
        return activeRule != null && activeRule.blocksRequiredProperties();
    }

    private Rule activeRule(RuleEventPropagator propertyCombinationStarter) {
        for (int i = rules.size() - 1; i >= 0; i--) {
            Rule rule = rules.get(i);
            propertyCombinationStarter.propagateEvent(rule);
            if (rule.isValueAddedToCurrentCombination()) {
                return rule;
            }
        }
        return null;
    }

    private void propagateUntilActiveRuleFound(RuleEventPropagator propertyCombinationStarter) {
    	Rule newActiveRule = activeRule(propertyCombinationStarter);
    	if(newActiveRule != null){
    		final boolean activeRuleCanBeReplaced = activeRule == null 
			    	|| ! (activeRule.blocksRequiredProperties() || activeRule.isValueAddedToCurrentCombination());
			final boolean consistentWithCurrentState = activeRuleCanBeReplaced || activeRule.equals(newActiveRule);
			if (!consistentWithCurrentState)
				throw new InconsistentRuleException("Inconsistent rules for property " + getTargetedPropertyName());
    		activeRule = newActiveRule;
    	}
    }

    @Override
    public void propertyCombinationStarted(final EngineState engineState) {
        propagateUntilActiveRuleFound(new PropertyCombinationStartedPropagator(engineState));
    }

    @Override
    public void propertyValueSet(final PropertyAssignedEvent event) {
        propagateUntilActiveRuleFound(new PropertyValueSetPropagator(event));
    }

    @Override
    public void propertyRequired(EngineState engineState) {
        propagateUntilActiveRuleFound(new PropertyRequiredPropagator(engineState));
    }

    @Override
    public void propertyCombinationFinished(EngineState engineState) {
    	if(activeRule != null)
    		activeRule.propertyCombinationFinished(engineState);
    }

    @Override
    public void setBlocksRequiredPropertiesItself(boolean block) {
        activeRule.setBlocksRequiredPropertiesItself(block);
    }

    @Override
    public boolean isValueAddedToCurrentCombination() {
        return activeRule != null && activeRule.isValueAddedToCurrentCombination();
    }

    @Override
    public Rule combineWith(Rule rule) {
        firstRule().checkRuleCompatibility(rule);
        rules.add(rule);
        return this;
    }

    @Override
    public Rule without(Rule rule) {
    	if(this == rule)
    		return null;
    	if(activeRule == rule)
    		activeRule = null;
        if (!rules.remove(rule))
        	throw new IllegalArgumentException();
        if (rules.size() == 1)
            return firstRule();
        return this;
    }

    @Override
    public boolean isLazyRule() {
        return firstRule().isLazyRule();
    }

    @Override
    public boolean forcesIteration() {
        return activeRule.forcesIteration();
    }

	@Override
	public void clearDependentRules(EngineState engineState) {
		activeRule.clearDependentRules(engineState);
		
	}

	@Override
	public boolean isTopRule() {
		return firstRule().isTopRule();
	}

	@Override
	public void checkRuleCompatibility(Rule rule) {
		firstRule().checkRuleCompatibility(rule);
	}

	@Override
	public String getRuleKey() {
		return firstRule().getRuleKey();
	}

	@Override
	public Set<String> getTriggeringProperties() {
		return activeRule.getTriggeringProperties();
	}

	public boolean valueHasChangedNow() {
		return activeRule.valueHasChangedNow();
	}

}
