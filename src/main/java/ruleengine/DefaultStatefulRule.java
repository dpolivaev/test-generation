package ruleengine;

public class DefaultStatefulRule extends StatefulRule {
    public DefaultStatefulRule(Condition condition, String targetedPropertyName, Values ruleValues) {
        super(condition, targetedPropertyName, ruleValues);
    }

    @Override
    public void propertyRequired(EngineState engineState) {
        if (getCondition().isSatisfied()) {
            addValueWithRules(engineState);
        }
    }

    @Override
    public boolean isDefaultRule() {
        return true;
    }

}