package ruleengine;

public class DefaultStatefulRule extends TopStatefulRule {
    public DefaultStatefulRule(Condition condition, String targetedPropertyName, Values ruleValues) {
        super(condition, targetedPropertyName, ruleValues);
    }

    @Override
    public void propertyCombinationStarted(EngineState engineState) {
        setFinished(true);
    }

    @Override
    public void propertyRequired(EngineState engineState) {
        super.propertyCombinationStarted(engineState);
    }

    @Override
    public boolean isDefaultRule() {
        return true;
    }

}