package ruleengine;

import java.util.Set;

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
	public void combinationStarted(State state) {
	}

	@Override
	public void combinationFinished(State state) {
	}

	@Override
	public void propertyValueSet(PropertyAssignedEvent event) {
	}

	@Override
	public Set<String> getTriggeringProperties() {
		return TestUtils.set();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}