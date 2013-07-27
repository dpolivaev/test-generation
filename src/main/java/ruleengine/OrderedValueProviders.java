package ruleengine;

import java.util.Collection;

class OrderedValueProviders implements ValueProviders {
    private ValueWithRulesProvider[] valueWithRulesProviders;
    private int valueIndex = -1;

    public OrderedValueProviders(ValueWithRulesProvider[] values) {
        this.valueWithRulesProviders = values;
    }

    @Override
	public boolean allValuesUsed() {
        return valueIndex == valueWithRulesProviders.length - 1;
	}

	@Override
	public void next() {
		valueIndex++;
        if (valueIndex == valueWithRulesProviders.length)
            valueIndex = 0;
	}

    @Override
    public ValueWithRulesProvider currentProvider() {
        return provider(valueIndex);
    }

    public ValueWithRulesProvider provider(int index) {
        return valueWithRulesProviders[index];
    }

    public int valueIndex() {
        return valueIndex;
    }

    public int size() {
        return valueWithRulesProviders.length;
    }
}