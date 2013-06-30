package ruleengine;

import java.util.Collection;

class OrderedValues implements Values {
    private ValueWithRulesProvider[] valueWithRulesProviders;
    private int valueIndex = -1;

    public OrderedValues(ValueWithRulesProvider[] values) {
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
	public Object currentValue() {
        return value(valueIndex);
	}

    public Object value(int index) {
        return valueWithRulesProviders[index].value();
    }

    @Override
    public Collection<Rule> currentValueRelatedRules() {
        return valueRelatedRules(valueIndex);
    }

    public Collection<Rule> valueRelatedRules(int index) {
        return valueWithRulesProviders[index].rules();
    }

    public int valueIndex() {
        return valueIndex;
    }

    public int size() {
        return valueWithRulesProviders.length;
    }
}