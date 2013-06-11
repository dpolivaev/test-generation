package ruleengine;

import java.util.Set;

public class PropertyAssignedEvent {

	private final State state;
	private final Rule workingRule;
	private final Set<String> requiredProperties;

	public PropertyAssignedEvent(State state, Rule workingRule,
			Set<String> requiredProperties) {
		super();
		this.state = state;
		this.workingRule = workingRule;
		this.requiredProperties = requiredProperties;
	}

	public Set<String> getRequiredProperties() {
		return requiredProperties;
	}

	public Rule getWorkingRule() {
		return workingRule;
	}

	public State getState() {
		return state;
	}

	public String getTargetedPropertyName() {
		return workingRule.getTargetedPropertyName();
	}

	public boolean containsPropertyValues(Set<String> triggeringProperties) {
		return state.containsPropertyValues(triggeringProperties);
	}

}
