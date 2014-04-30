package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import java.util.Set;

import org.dpolivaev.testgeneration.engine.ruleengine.EngineState;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;

public class PropertyAssignedEvent {

    private final EngineState engineState;
	private final Rule workingRule;
	private final Set<String> requiredProperties;
    private final boolean valueChanged;

    public boolean isValueChanged() {
        return valueChanged;
    }

    public PropertyAssignedEvent(EngineState engineState, Rule workingRule,
 Set<String> dependencies,
        boolean valueChanged) {
		super();
		this.engineState = engineState;
		this.workingRule = workingRule;
        this.valueChanged = valueChanged;
        this.requiredProperties = workingRule.calculateRequiredProperties(dependencies);
	}

	public Set<String> getRequiredProperties() {
		return requiredProperties;
	}

	public Rule getWorkingRule() {
		return workingRule;
	}

    public EngineState getState() {
		return engineState;
	}

	public String getTargetedPropertyName() {
		return workingRule.getTargetedPropertyName();
	}

    public boolean containsTriggeredProperties(Set<String> triggeringProperties) {
	for(String property : triggeringProperties){
		if(!engineState.containsTriggeringPropertyValue(property))
			return false;
	}
	return true;
    }

}
