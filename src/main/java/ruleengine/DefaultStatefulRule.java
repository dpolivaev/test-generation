package ruleengine;

public class DefaultStatefulRule extends StatefulRule {
    public DefaultStatefulRule(Condition condition, String targetedPropertyName, ValueProviders ruleValues) {
        super(condition, targetedPropertyName, ruleValues);
    }

    @Override
    public void propertyRequired(EngineState engineState) {
        if (getCondition().isSatisfied(engineState)) {
            addValueWithRules(engineState);
        }
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