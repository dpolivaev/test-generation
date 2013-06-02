package ruleengine;

class ConstantValues implements Values {
	Object[] values;
    int valueIndex = -1;

	public ConstantValues(Object[] values) {
		this.values = values;
	}

	@Override
	public boolean isNewIterationFinished() {
        return valueIndex == values.length - 1;
	}

	@Override
	public void next() {
		valueIndex++;
	}

	@Override
	public Object currentValue() {
        if (valueIndex == values.length)
            valueIndex = 0;
		return values[valueIndex];
	}
}