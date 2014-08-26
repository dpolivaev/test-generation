package org.dpolivaev.testgeneration.engine.ruleengine;


public interface EngineState extends PropertyContainer{
    Strategy currentStrategy();
    void setPropertyValue(Rule rule, Object value);
	boolean containsCompatibleRule(Rule rule);
	void addTemporaryRule(Rule creatingRule, Rule rule);
}
