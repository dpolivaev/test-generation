package ruleengine;

public class TopStatefulRule extends StatefulRule {
    public TopStatefulRule(Condition condition, String targetedPropertyName, Values ruleValues) {
        super(condition, targetedPropertyName, ruleValues);
    }

    @Override
    public void propertyCombinationStarted(EngineState engineState) {
        if (getCondition().isSatisfied())
            addValueWithRules(engineState);
        else
            setFinished(true);
    }

    @Override
    public boolean canTriggerOtherRules() {
        return true;
    }
}