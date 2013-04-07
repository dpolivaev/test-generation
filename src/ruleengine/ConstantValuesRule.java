package ruleengine;

import java.util.Collections;
import java.util.Set;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class ConstantValuesRule implements Rule {

	private final String targetedPropertyName;
	private final Object[] values;
	private boolean finished;
	private int valueIndex;
	private final Set<String> triggeredBy;

	@SuppressWarnings("unchecked")
	public ConstantValuesRule(String targetedPropertyName, Object... values) {
		this(Collections.EMPTY_SET, targetedPropertyName, values);
	}

	public ConstantValuesRule(Set<String> triggeredBy,
			String targetedPropertyName, Object... values) {
		this.triggeredBy = triggeredBy;
		this.targetedPropertyName = targetedPropertyName;
		this.values = values;
		this.finished = false;
		this.valueIndex = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ruleengine.Rule#getTargetedPropertyName()
	 */
	@Override
	public String getTargetedPropertyName() {
		return targetedPropertyName;
	}

	@Override
	public boolean hasFinished() {
		return finished;
	}

	private Object nextValue() {
		Object value = values[valueIndex++];
		return value;
	}

	@Override
	public void nextIteration(State state) {
		if (triggeredBy.isEmpty()) {
			iterate(state);
		}
	}

	private void iterate(State state) {
		Object nextValue = nextValue();
		state.setPropertyValue(this, nextValue);
	}

	@Override
	public void propertyValueSet(PropertyAssignedEvent event) {
		if (triggeredBy.contains(event.getTargetedPropertyName())
				&& event.getState().containsPropertyValues(triggeredBy))
			iterate(event.getState());
	}

	@Override
	public void finishIteration(State state) {
		finished = finished || valueIndex == values.length;
		if (valueIndex == values.length)
			valueIndex = 0;
	}
}
