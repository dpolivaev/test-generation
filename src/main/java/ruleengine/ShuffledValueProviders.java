package ruleengine;

public class ShuffledValueProviders implements ValueProviders {

    final OrderedValueProviders orderedValueProviders;
    final Permutation permutation;

    public ShuffledValueProviders(OrderedValueProviders orderedValueProviders, Permutation permutation) {
        this.orderedValueProviders = orderedValueProviders;
        this.permutation = permutation;
    }

    public ShuffledValueProviders(OrderedValueProviders orderedValueProviders) {
        this(orderedValueProviders, new Permutation(orderedValueProviders.size()));
    }

    @Override
    public boolean allValuesUsed() {
        return orderedValueProviders.allValuesUsed();
    }

    @Override
    public ValueWithRulesProvider currentProvider() {
        return orderedValueProviders.provider(shuffledIndex());
    }

    private int shuffledIndex() {
        return permutation.at(orderedValueProviders.valueIndex());
    }

    @Override
    public void next() {
        orderedValueProviders.next();
        if (orderedValueProviders.valueIndex() == 0)
            permutation.shuffle();
    }

    @Override
    public boolean containsMultipleValues() {
        return orderedValueProviders.containsMultipleValues();
    }
}
