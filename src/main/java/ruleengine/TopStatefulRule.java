package ruleengine;

public class TopStatefulRule extends StatefulRule {
    public TopStatefulRule(Condition condition, String targetedPropertyName, ValueProviders ruleValues) {
        super(condition, targetedPropertyName, ruleValues);
        setBlocksRequiredProperties(true);
    }

    @Override
    public void propertyCombinationStarted(EngineState engineState) {
        if (getCondition().isSatisfied(engineState)) {
            addValueWithRules(engineState);
        }
        else
            setBlocksRequiredProperties(false);
    }

    @Override
    public boolean isDefaultRule() {
        return false;
    }
}