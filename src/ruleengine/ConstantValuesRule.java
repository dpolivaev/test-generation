package ruleengine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class ConstantValuesRule implements Rule {

	private final String targetedPropertyName;
	private final Object[] values;
	private boolean finished;
	private int valueIndex;
	private final Set<String> triggeringProperties;
	private final Set<Rule> dependencies;

	public Set<String> getTriggeringProperties() {
		return triggeringProperties;
	}

	private boolean valueAddedToCombination;

	@SuppressWarnings("unchecked")
	public ConstantValuesRule(String targetedPropertyName, Object... values) {
		this(Collections.EMPTY_SET, targetedPropertyName, values);
	}

	public ConstantValuesRule(Set<String> triggeredBy,
			String targetedPropertyName, Object... values) {
		this.triggeringProperties = triggeredBy;
		this.targetedPropertyName = targetedPropertyName;
		this.values = values;
		this.finished = false;
		this.valueIndex = 0;
		this.valueAddedToCombination = false;
		dependencies = new HashSet<>();
	}

	@Override
	public String getTargetedPropertyName() {
		return targetedPropertyName;
	}

	@Override
	public boolean hasFinished() {
		return finished;
	}

	private Object nextValue() {
		Object value = values[valueIndex];
		return value;
	}

	@Override
	public void combinationStarted(State state) {
		if (triggeringProperties.isEmpty()) {
			addValue(state);
		}
	}

	private void addValue(State state) {
		Object nextValue = nextValue();
		state.setPropertyValue(this, nextValue);
		valueAddedToCombination = true;
	}

	@Override
	public void propertyValueSet(PropertyAssignedEvent event) {
		if (triggeringProperties.contains(event.getTargetedPropertyName())
				&& event.getState()
						.containsPropertyValues(triggeringProperties))
			addValue(event.getState());
		if (event.getRequiredProperties().contains(targetedPropertyName)) {
			dependencies.add(event.getWorkingRule());
		}
	}

	@Override
	public void combinationFinished(State state) {
		if (valueAddedToCombination) {
			if (RuleEngine.allRulesHaveFinished(dependencies)) {
				dependencies.clear();
				valueIndex++;
				if (valueIndex == values.length) {
					finished = true;
					valueIndex = 0;
				}
			}
			valueAddedToCombination = false;
		}
	}
}
