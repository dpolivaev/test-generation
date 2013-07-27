package ruleengine;

public class TrueCondition implements Condition {
	@Override
	public boolean isSatisfied(PropertyContainer propertyContainer) {
		return true;
	}
}