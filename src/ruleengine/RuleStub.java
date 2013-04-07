package ruleengine;

final class RuleStub implements Rule {
	private final String targetedPropertyName;

	RuleStub(String targetedPropertyName) {
		this.targetedPropertyName = targetedPropertyName;
	}

	@Override
	public String getTargetedPropertyName() {
		return targetedPropertyName;
	}

	@Override
	public boolean hasFinished() {
		return false;
	}

	@Override
	public void nextCombination(State state) {
	}

	@Override
	public void finishCombination(State state) {
	}

	@Override
	public void propertyValueSet(PropertyAssignedEvent event) {
	}
}