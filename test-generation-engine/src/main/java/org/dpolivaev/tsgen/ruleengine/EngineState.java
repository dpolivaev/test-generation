package org.dpolivaev.tsgen.ruleengine;


public interface EngineState extends PropertyContainer{
    Strategy currentStrategy();
    void setPropertyValue(Rule rule, Object value, boolean useNextValue);
	boolean containsCompatibleRule(Rule rule);
	void addRule(Rule rule);
}
