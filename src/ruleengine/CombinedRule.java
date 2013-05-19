package ruleengine;

import java.util.Set;

class CombinedRule implements Rule {


    private final Rule oldRule;
    private Rule activeRule;

    public CombinedRule(Rule oldRule, Rule newRule) {
        this.oldRule = oldRule;
        this.activeRule = newRule;
    }

    @Override
    public String getTargetedPropertyName() {
        return activeRule.getTargetedPropertyName();
    }

    @Override
    public Set<String> getTriggeringProperties() {
        return activeRule.getTriggeringProperties();
    }

    @Override
    public boolean hasFinished() {
        return activeRule.hasFinished();
    }

    @Override
    public void combinationStarted(State state) {
        activeRule.combinationStarted(state);
        if (!isActive()) {
            activeRule = oldRule;
            combinationStarted(state);
        }
    }

    @Override
    public void combinationFinished(State state) {
        activeRule.combinationFinished(state);
    }

    @Override
    public void propertyValueSet(PropertyAssignedEvent event) {
        activeRule.propertyValueSet(event);
    }

    @Override
    public void reset() {
        activeRule.reset();
    }

    @Override
    public boolean isActive() {
        return activeRule.isActive();
    }

    @Override
    public Rule combineWith(Rule rule) {
        return null;
    }
    
}
