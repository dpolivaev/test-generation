package ruleengine;

import java.util.Set;

public class PropertyAssignedEvent {

    private final EngineState engineState;
	private final Rule workingRule;
	private final Set<String> requiredProperties;

    public PropertyAssignedEvent(EngineState engineState, Rule workingRule,
 Set<String> dependencies) {
		super();
		this.engineState = engineState;
		this.workingRule = workingRule;
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
