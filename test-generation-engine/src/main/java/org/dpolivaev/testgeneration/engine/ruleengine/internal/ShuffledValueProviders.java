package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import org.dpolivaev.testgeneration.engine.ruleengine.Order;

public class ShuffledValueProviders implements ValueProviders {

	final private OrderedValueProviders orderedValueProviders;
    final private Permutation permutation;
    private Order order;

    public ShuffledValueProviders(OrderedValueProviders orderedValueProviders, Permutation permutation, Order order) {
        this.orderedValueProviders = orderedValueProviders;
        this.permutation = permutation;
		this.order = order(order);
    }

	private Order order(Order order) {
		if(order == Order.SHUFFLED_KEEP_LAST_ELEMENT_POSITION)
			return Order.SHUFFLED;
		else
			return order;
	}
    
    public ShuffledValueProviders(OrderedValueProviders orderedValueProviders, Permutation permutation) {
    	this(orderedValueProviders, permutation, Order.ORDERED_THEN_SHUFFLED);
    }

    public ShuffledValueProviders(OrderedValueProviders orderedValueProviders, Order order) {
        this(orderedValueProviders, permutation(orderedValueProviders, order), order);
    }

	private static Permutation permutation(
			OrderedValueProviders orderedValueProviders, Order order) {
		int valueNumber = orderedValueProviders.size();
		return new Permutation(order.permutationSize(valueNumber));
	}

    public ShuffledValueProviders(OrderedValueProviders orderedValueProviders) {
        this(orderedValueProviders, permutation(orderedValueProviders, Order.ORDERED_THEN_SHUFFLED));
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
			else if(order == Order.ORDERED_THEN_SHUFFLED || order == Order.ORDERED_THEN_SHUFFLED_KEEP_LAST_ELEMENT_POSITION)
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

	public String toString() {
		return orderedValueProviders.toString();
	}
	
	
}
