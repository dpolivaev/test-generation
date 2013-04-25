package ruleengine;

public class TrueCondition implements Condition {
	@Override
	public boolean calculate() {
		return true;
	}
}