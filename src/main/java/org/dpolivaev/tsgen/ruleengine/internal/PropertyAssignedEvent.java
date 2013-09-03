package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.EngineState;
import org.dpolivaev.tsgen.ruleengine.Rule;

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
        this.requiredProperties = workingRule.requiredProperties(dependencies);
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

	public boolean containsPropertyValues(Set<String> triggeringProperties) {
		return engineState.containsPropertyValues(triggeringProperties);
	}

}
