package org.dpolivaev.tsgen.ruleengine.internal;

import org.dpolivaev.tsgen.ruleengine.Order;

public class ShuffledValueProviders implements ValueProviders {

	final private OrderedValueProviders orderedValueProviders;
    final private Permutation permutation;
    private Order order;

    public ShuffledValueProviders(OrderedValueProviders orderedValueProviders, Permutation permutation, Order order) {
        this.orderedValueProviders = orderedValueProviders;
        this.permutation = permutation;
		this.order = order;
    }
    
    public ShuffledValueProviders(OrderedValueProviders orderedValueProviders, Permutation permutation) {
    	this(orderedValueProviders, permutation, Order.ORDERED_THEN_SHUFFLED);
    }

    public ShuffledValueProviders(OrderedValueProviders orderedValueProviders, Order order) {
        this(orderedValueProviders, new Permutation(orderedValueProviders.size()), order);
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
        if (orderedValueProviders.valueIndex() == 0) {
			if (order == Order.SHUFFLED)
				permutation.shuffle();
			else if(order == Order.ORDERED_THEN_SHUFFLED)
				order = Order.SHUFFLED;
		}
    }

    @Override
    public boolean containsMultipleValues() {
        return orderedValueProviders.containsMultipleValues();
    }

	public Order getOrder() {
		return order;
	}
}
