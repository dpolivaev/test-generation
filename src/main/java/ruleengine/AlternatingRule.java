package ruleengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class AlternatingRule implements Rule {
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
    public Set<String> requiredProperties(Set<String> dependencies) {
        return firstRule().requiredProperties(dependencies);
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
        if (isDefaultRule() != rule.isDefaultRule())
            throw new IllegalArgumentException("triggering and not triggering rules can not be combined");
        if (!getTargetedPropertyName().equals(rule.getTargetedPropertyName()))
            throw new IllegalArgumentException("rules with different targeted property names can not be combined");
        if (!getTriggeringProperties().equals(rule.getTriggeringProperties()))
            throw new IllegalArgumentException("rules with different triggering property names can not be combined");

    }

    @Override
    public Rule without(Rule rule) {
        rules.remove(rule);
        if (rules.size() == 1)
            return firstRule();
        return this;
    }

    @Override
    public boolean isDefaultRule() {
        return firstRule().isDefaultRule();
    }

}
