package ruleengine;

class ConstantValues implements Values {
	Object[] values;
	int valueIndex = 0;

	public ConstantValues(Object[] values) {
		this.values = values;
	}

	@Override
	public boolean isNewIterationStarted() {
		return valueIndex == 0;
	}

	@Override
	public void next() {
		valueIndex++;
		if (valueIndex == values.length)
			valueIndex = 0;
	}

	@Override
	public Object currentValue() {
		return values[valueIndex];
	}
}