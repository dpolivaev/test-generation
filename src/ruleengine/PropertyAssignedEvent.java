package ruleengine;

public class PropertyAssignedEvent {

	private final State state;
	private final Rule workingRule;

	public PropertyAssignedEvent(State state, Rule workingRule) {
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
