package ruleengine;

import java.util.Set;

public class PropertyAssignedEvent {

	private final State state;
	private final Rule workingRule;

	public PropertyAssignedEvent(State state, Rule workingRule,
			Set<String> requiredProperties) {
		super();
		this.state = state;
		this.workingRule = workingRule;
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

}
