package ruleengine;

import java.util.*;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class StatefulRule implements Rule {

	private final String targetedPropertyName;

	final private Values values;
	private boolean finished;
	private final Set<String> triggeringProperties;
	private final Set<Rule> dependencies;

	@Override
	public Set<String> getTriggeringProperties() {
		return triggeringProperties;
	}

	private boolean valueAddedToCombination;

	private final Condition condition;

	public StatefulRule(Set<String> triggeredBy, Condition condition,
		String targetedPropertyName, Object[] values) {
		this.triggeringProperties = triggeredBy;
		this.condition = condition;
		this.targetedPropertyName = targetedPropertyName;
		this.values = new ConstantValues(values);
		this.finished = false;
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
		Object value = values.currentValue();
		return value;
	}

	@Override
	public void propertyCombinationStarted(State state) {
        if (triggeringProperties.isEmpty()) {
            if (condition.calculate())
                addValue(state);
            else
                finished = true;
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
            && event.containsPropertyValues(triggeringProperties)) {
            if (condition.calculate()) {
                addValue(event.getState());
                return;
            }
        }
        if (event.getRequiredProperties().contains(targetedPropertyName)) {
            Rule rule = event.getWorkingRule();
            rule.setNotFinished();
            dependencies.add(rule);
		}
	}

	@Override
	public void propertyCombinationFinished(State state) {
		if (valueAddedToCombination) {
			for (Rule rule : dependencies)
				rule.propertyCombinationFinished(state);
			if (RuleEngine.allRulesHaveFinished(dependencies)) {
                for (Rule rule : dependencies) {
                    if (rule.getTriggeringProperties().contains(targetedPropertyName))
                        rule.setNotFinished();
                }
				dependencies.clear();
				values.next();
				if (values.isNewIterationStarted()) {
					finished = true;
				}
			}
			valueAddedToCombination = false;
		}
	}

	@Override
	public void setNotFinished() {
		finished = false;
	}

    @Override
    public boolean isActive() {
        return valueAddedToCombination;
    }

    @Override
    public Rule combineWith(Rule rule) {
        return new CombinedRule(this, rule);
    }

    @Override
    public String toString() {
        return "StatefulRule [" + triggeringProperties + " -> " + targetedPropertyName + "]";
    }

}
