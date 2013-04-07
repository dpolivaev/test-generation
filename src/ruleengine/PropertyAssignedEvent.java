package ruleengine;

public class PropertyAssignedEvent {

	private final State state;
	private final String targetedPropertyName;
	private final Rule workingRule;

	public PropertyAssignedEvent(State state, Rule workingRule,
			String targetedPropertyName) {
		super();
		this.state = state;
		this.workingRule = workingRule;
		this.targetedPropertyName = targetedPropertyName;
	}

	public Rule getWorkingRule() {
		return workingRule;
	}

	public State getState() {
		return state;
	}

	public String getTargetedPropertyName() {
		return targetedPropertyName;
	}

}
