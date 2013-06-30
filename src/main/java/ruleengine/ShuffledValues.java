package ruleengine;

import java.util.Collection;

public class ShuffledValues implements Values {

    final OrderedValues orderedValues;
    final Permutation permutation;

    public ShuffledValues(OrderedValues orderedValues, Permutation permutation) {
        this.orderedValues = orderedValues;
        this.permutation = permutation;
    }

    public ShuffledValues(OrderedValues orderedValues) {
        this(orderedValues, new Permutation(orderedValues.size()));
    }

    @Override
    public boolean allValuesUsed() {
        return orderedValues.allValuesUsed();
    }

    @Override
    public Object currentValue() {
        return orderedValues.value(shuffledIndex());
    }

    private int shuffledIndex() {
        return permutation.at(orderedValues.valueIndex());
    }

    @Override
    public Collection<Rule> currentValueRelatedRules() {
        return orderedValues.valueRelatedRules(shuffledIndex());
    }

    @Override
    public void next() {
        orderedValues.next();
        if (orderedValues.valueIndex() == 0)
            permutation.shuffle();
    }
}
