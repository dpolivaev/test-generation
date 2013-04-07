package ruleengine;

public enum RuleState {
	ITERATING(false), FINISHED(true);
	private final boolean finished;

	private RuleState(boolean finished) {
		this.finished = finished;
	}

	public boolean hasFinished() {
		return finished;
	}
}
