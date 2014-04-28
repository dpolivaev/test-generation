package org.dpolivaev.tsgen.ruleengine;


public interface EngineState extends PropertyContainer{
    Strategy currentStrategy();
    void setPropertyValue(Rule rule, Object value, boolean useNextValue);
	void addRule(Rule existingRule, Rule newRule);
	boolean containsCompatibleRule(Rule rule);
	void addCompatibleRule(Rule rule);
}
