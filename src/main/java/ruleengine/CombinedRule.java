package ruleengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class CombinedRule implements Rule {
    private final List<Rule> rules = new ArrayList<>();
    private Rule activeRule = null;

    public CombinedRule(Rule oldRule, Rule newRule) {
        rules.add(oldRule);
        combineWith(newRule);
    }

    @Override
    public String getTargetedPropertyName() {
        return rules.get(0).getTargetedPropertyName();
    }

    @Override
    public Set<String> getTriggeringProperties() {
        return rules.get(0).getTriggeringProperties();
    }

    @Override
    public boolean hasFinished() {
        return activeRule != null && activeRule.hasFinished();
    }

    private void propagateUntilActiveRuleFound(RuleEventPropagator propertyCombinationStarter) {
        for (int i = rules.size() - 1; i >= 0; i--) {
            Rule activeRule = rules.get(i);
            propertyCombinationStarter.propagateEvent(activeRule);
            if (activeRule.isActive()) {
                setActiveRule(activeRule);
                break;
            }
        }
    }

    @Override
    public void propertyCombinationStarted(final EngineState engineState) {
        if (getTriggeringProperties().isEmpty()) {
            propagateUntilActiveRuleFound(new PropertyCombinationStartedPropagator(engineState));
        }
    }

    @Override
    public void propertyValueSet(final PropertyAssignedEvent event) {
        propagateUntilActiveRuleFound(new PropertyValueSetPropagator(event));
    }

    @Override
    public void propertyRequired(EngineState engineState) {
        if (engineState.containsPropertyValues(getTriggeringProperties())) {
            propagateUntilActiveRuleFound(new PropertyRequiredPropagator(engineState));
        }
    }

    private void setActiveRule(Rule activeRule) {
        this.activeRule = activeRule;
    }

    @Override
    public void propertyCombinationFinished(EngineState engineState) {
        activeRule.propertyCombinationFinished(engineState);
    }

    @Override
    public void setNotFinished() {
        activeRule.setNotFinished();
    }

    @Override
    public boolean isActive() {
        return activeRule != null && activeRule.isActive();
    }

    @Override
    public Rule combineWith(Rule rule) {
        checkRuleCompatibility(rule);
        rules.add(rule);
        return this;
    }

    private void checkRuleCompatibility(Rule rule) {
        if (!getTargetedPropertyName().equals(rule.getTargetedPropertyName()))
            throw new IllegalArgumentException("different targeted property name " + rule.getTargetedPropertyName());
        if (!getTriggeringProperties().equals(rule.getTriggeringProperties()))
            throw new IllegalArgumentException("different targeted property name " + rule.getTargetedPropertyName());

    }

    @Override
    public Rule without(Rule rule) {
        rules.remove(rule);
        if (rules.size() == 1)
            return rules.get(0);
        return this;
    }

}
