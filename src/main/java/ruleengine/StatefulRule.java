package ruleengine;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class StatefulRule implements Rule {

    final private String targetedPropertyName;
	final private Values values;
    final private Set<String> triggeringProperties;
    final private Set<Rule> dependentRules;
    final private Condition condition;

	private boolean valueAlreadyAddedToCurrentCombination;
    private boolean finished;

	public StatefulRule(Set<String> triggeredBy, Condition condition,
		String targetedPropertyName, Object[] values) {
		this.triggeringProperties = triggeredBy;
		this.condition = condition;
		this.targetedPropertyName = targetedPropertyName;
		this.values = new ConstantValues(values);
		this.finished = false;
		this.valueAlreadyAddedToCurrentCombination = false;
		dependentRules = new HashSet<>();
	}

	@Override
	public String getTargetedPropertyName() {
		return targetedPropertyName;
	}

    @Override
    public Set<String> getTriggeringProperties() {
        return triggeringProperties;
    }

	@Override
	public boolean hasFinished() {
		return finished;
	}

	private Object nextValue() {
        if (dependentRules.isEmpty())
            values.next();
		Object value = values.currentValue();
		return value;
	}

	@Override
	public void propertyCombinationStarted(State state) {
        if (triggeringProperties.isEmpty()) {
            if (condition.isSatisfied())
                addValue(state);
            else
                finished = true;
        }
	}

	private void addValue(State state) {
		Object nextValue = nextValue();
        valueAlreadyAddedToCurrentCombination = true;
		state.setPropertyValue(this, nextValue);
	}

	@Override
	public void propertyValueSet(PropertyAssignedEvent event) {
        if (valueAlreadyAddedToCurrentCombination)
            addDependencies(event);
        else if (triggeringProperties.contains(event.getTargetedPropertyName())
            && event.containsPropertyValues(triggeringProperties) && condition.isSatisfied())
            addValue(event.getState());
	}

    private void addDependencies(PropertyAssignedEvent event) {
        if (event.getRequiredProperties().contains(targetedPropertyName)) {
            Rule rule = event.getWorkingRule();
            rule.setNotFinished();
            dependentRules.add(rule);
        }
    }

    private boolean allRulesHaveFinished(Iterable<Rule> rules) {
        for (Rule rule : rules)
            if (!rule.hasFinished())
                return false;
        return true;
    }

	@Override
	public void propertyCombinationFinished(State state) {
		if (valueAlreadyAddedToCurrentCombination) {
			for (Rule rule : dependentRules)
				rule.propertyCombinationFinished(state);
            if (allRulesHaveFinished(dependentRules)) {
				dependentRules.clear();
                if (values.isNewIterationFinished())
                    finished = true;
			}
			valueAlreadyAddedToCurrentCombination = false;
		}
	}

	@Override
	public void setNotFinished() {
		finished = false;
	}

    @Override
    public boolean isActive() {
        return valueAlreadyAddedToCurrentCombination;
    }

    @Override
    public Rule combineWith(Rule rule) {
        return new CombinedRule(this, rule);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StatefulRule [");
        if (!triggeringProperties.isEmpty())
            stringBuilder.append(triggeringProperties).append(" -> ");
        stringBuilder.append(targetedPropertyName).append("]");
        return stringBuilder.toString();
    }

    @Override
    public Rule without(Rule rule) {
        return null;
    }

}
