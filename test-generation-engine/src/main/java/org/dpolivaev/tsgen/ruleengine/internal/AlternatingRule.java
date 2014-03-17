package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.EngineState;
import org.dpolivaev.tsgen.ruleengine.InconsistentRuleException;
import org.dpolivaev.tsgen.ruleengine.Rule;

public class AlternatingRule implements Rule {
    private final List<Rule> rules = new ArrayList<>();
    private Rule activeRule = null;

    public AlternatingRule(Rule oldRule, Rule newRule) {
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
    public Set<String> getTriggeringProperties() {
        return firstRule().getTriggeringProperties();
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
    public void setBlocksRequiredProperties(boolean block) {
        activeRule.setBlocksRequiredProperties(block);
    }

    @Override
    public boolean isValueAddedToCurrentCombination() {
        return activeRule != null && activeRule.isValueAddedToCurrentCombination();
    }

    @Override
    public Rule combineWith(Rule rule) {
        checkRuleCompatibility(rule);
        rules.add(rule);
        return this;
    }

    private void checkRuleCompatibility(Rule rule) {
        if (isDefaultRule() != rule.isDefaultRule())
            throw new IllegalArgumentException("triggering and not triggering rules can not be combined");
        if (!getTargetedPropertyName().equals(rule.getTargetedPropertyName()))
            throw new IllegalArgumentException("rules with different targeted property names can not be combined");
        if (!getTriggeringProperties().equals(rule.getTriggeringProperties()))
            throw new IllegalArgumentException("rules with different triggering property names can not be combined");

    }

    @Override
    public Rule without(Rule rule) {
    	if(activeRule == rule)
    		activeRule = null;
        rules.remove(rule);
        if (rules.size() == 1)
            return firstRule();
        return this;
    }

    @Override
    public boolean isDefaultRule() {
        return firstRule().isDefaultRule();
    }

    @Override
    public boolean forcesIteration() {
        return activeRule.forcesIteration();
    }

	@Override
	public void clearDependentRules(EngineState engineState) {
		activeRule.clearDependentRules(engineState);
		
	}

}
