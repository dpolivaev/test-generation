package ruleengine;

public class PropertyAssignedEvent {

	private final State state;
	private final String targetedPropertyName;

	public PropertyAssignedEvent(State state, String targetedPropertyName) {
		super();
		this.state = state;
		this.targetedPropertyName = targetedPropertyName;
	}

	public State getState() {
		return state;
	}

	public String getTargetedPropertyName() {
		return targetedPropertyName;
	}

}
