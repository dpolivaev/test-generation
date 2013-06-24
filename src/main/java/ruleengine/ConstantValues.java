package ruleengine;

import java.util.Collection;
import java.util.Collections;

class ConstantValues implements Values {
    public static class ValueWithRules{
        public final Collection<Rule> rules;
        public final Object value;

        public ValueWithRules(Object value, Collection<Rule> rules) {
            super();
            this.value = value;
            this.rules = rules;
        }

    }

    private ValueWithRules[] values;
    private int valueIndex = -1;

    public ConstantValues(Object[] values) {
        this.values = new ValueWithRules[values.length];
        for (int i = 0; i < values.length; i++) {
            this.values[i] = valueWithRules(values[i]);
        }
    }

    private ValueWithRules valueWithRules(Object value) {
        return value instanceof ValueWithRules ? (ValueWithRules) value //
            : new ValueWithRules(value, Collections.<Rule> emptyList());
    }

	@Override
	public boolean allValuesUsed() {
        return valueIndex == values.length - 1;
	}

	@Override
	public void next() {
		valueIndex++;
        if (valueIndex == values.length)
            valueIndex = 0;
	}

	@Override
	public Object currentValue() {
        return values[valueIndex].value;
	}

    @Override
    public Collection<Rule> currentValueRelatedRules() {
        return values[valueIndex].rules;
    }
}