package org.dpolivaev.tsgen.ruleengine;

public interface EngineState extends PropertyContainer{
    Strategy currentStrategy();
    void setPropertyValue(Rule rule, Object value, boolean useNextValue);
}
