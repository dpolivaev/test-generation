package ruleengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class CombinedRule implements Rule {


    private final List<Rule> rules = new ArrayList<>();
    private Rule activeRule;

    public CombinedRule(Rule oldRule, Rule newRule) {
        this.activeRule = newRule;
        rules.add(oldRule);
        rules.add(newRule);
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
    public void propertyCombinationStarted(State state) {
        for(int i = rules.size() - 1; i >= 0; i--){
            activeRule = rules.get(i);
            activeRule.propertyCombinationStarted(state);
            if (activeRule.isActive()) {
                break;
            }
        }
    }

    @Override
    public void propertyCombinationFinished(State state) {
        activeRule.propertyCombinationFinished(state);
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
        rules.add(rule);
        return this;
    }
    
}
