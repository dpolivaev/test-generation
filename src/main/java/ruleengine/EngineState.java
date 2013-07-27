package ruleengine;

public interface EngineState extends PropertyContainer{
    Strategy currentStrategy();
    void setPropertyValue(Rule rule, Object value, boolean useNextValue);
}
