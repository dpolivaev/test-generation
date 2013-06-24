package ruleengine;

import java.util.Collection;
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

    public StatefulRule(Set<String> triggeredBy, Condition condition, String targetedPropertyName,
        ValueWithRulesProvider[] values) {
		this.triggeringProperties = triggeredBy;
		this.condition = condition;
		this.targetedPropertyName = targetedPropertyName;
        this.values = new OrderedValues(values);
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

    private void addValueWithRules(EngineState engineState) {
        if (dependentRules.isEmpty()) {
            values.next();
            addRules(engineState);
        }
        setValue(engineState);
	}

    private void setValue(EngineState engineState) {
        Object value = values.currentValue();
        valueAlreadyAddedToCurrentCombination = true;
		engineState.setPropertyValue(this, value);
    }

    private void addRules(EngineState engineState) {
        Collection<Rule> rules = values.currentValueRelatedRules();
        for (Rule rule : rules)
            engineState.addRule(rule);
    }

	@Override
	public void propertyValueSet(PropertyAssignedEvent event) {
        if (valueAlreadyAddedToCurrentCombination)
            addDependencies(event);
        else if (triggeringProperties.contains(event.getTargetedPropertyName())
            && event.containsPropertyValues(triggeringProperties) && condition.isSatisfied())
            addValueWithRules(event.getState());
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
    public void propertyCombinationFinished(EngineState engineState) {
		if (valueAlreadyAddedToCurrentCombination) {
			for (Rule rule : dependentRules)
                rule.propertyCombinationFinished(engineState);
            if (allRulesHaveFinished(dependentRules)) {
                removeValueRelatedRules(engineState);
				dependentRules.clear();
                if (values.allValuesUsed())
                    finished = true;
			}
			valueAlreadyAddedToCurrentCombination = false;
		}
	}

    private void removeValueRelatedRules(EngineState engineState) {
        Collection<Rule> relatedRules = values.currentValueRelatedRules();
        for (Rule rule : relatedRules) {
            engineState.removeRule(rule);
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

    @Override
    public void propertyCombinationStarted(EngineState engineState) {
        if (triggeringProperties.isEmpty()) {
            if (condition.isSatisfied())
                addValueWithRules(engineState);
            else
                finished = true;
        }
    }
}
