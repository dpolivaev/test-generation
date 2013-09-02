package ruleengine.internal;

import ruleengine.Condition;
import ruleengine.EngineState;

public class TopStatefulRule extends StatefulRule {
    public TopStatefulRule(Condition condition, String targetedPropertyName, ValueProviders ruleValues) {
        super(condition, targetedPropertyName, ruleValues);
        setBlocksRequiredProperties(true);
    }

    @Override
    public void propertyCombinationStarted(EngineState engineState) {
        if(engineState.getCombinationCounter() == 1)
            setBlocksRequiredProperties(true);
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