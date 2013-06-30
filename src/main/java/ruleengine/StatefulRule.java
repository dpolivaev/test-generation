package ruleengine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
abstract class StatefulRule implements Rule {

    final private String targetedPropertyName;
	final private Values values;
    final private Set<Rule> dependentRules;
    final private Condition condition;

	private boolean valueAlreadyAddedToCurrentCombination;
    private boolean finished;

    public StatefulRule(Condition condition, String targetedPropertyName, Values ruleValues) {
		this.condition = condition;
		this.targetedPropertyName = targetedPropertyName;
        this.values = ruleValues;
		this.setFinished(false);
        this.setValueAlreadyAddedToCurrentCombination(false);
		dependentRules = new HashSet<>();
	}

    @Override
	public String getTargetedPropertyName() {
		return targetedPropertyName;
	}

    @Override
    public Set<String> getTriggeringProperties() {
        return Collections.<String> emptySet();
    }

	@Override
	public boolean hasFinished() {
		return isFinished();
	}

    protected void addValueWithRules(EngineState engineState) {
        if (dependentRules.isEmpty()) {
            values.next();
            addRules(engineState);
        }
        setValue(engineState);
	}

    private void setValue(EngineState engineState) {
        Object value = values.currentValue();
        setValueAlreadyAddedToCurrentCombination(true);
		engineState.setPropertyValue(this, value);
    }

    private void addRules(EngineState engineState) {
        Collection<Rule> rules = values.currentValueRelatedRules();
        for (Rule rule : rules)
            engineState.addRule(rule);
    }

	@Override
	public void propertyValueSet(PropertyAssignedEvent event) {
        if (isValueAlreadyAddedToCurrentCombination())
            addDependencies(event);
    }

    protected void addDependencies(PropertyAssignedEvent event) {
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
        if (isValueAlreadyAddedToCurrentCombination()) {
			for (Rule rule : dependentRules)
                rule.propertyCombinationFinished(engineState);
            if (allRulesHaveFinished(dependentRules)) {
                removeValueRelatedRules(engineState);
				dependentRules.clear();
                if (values.allValuesUsed())
                    setFinished(true);
			}
            setValueAlreadyAddedToCurrentCombination(false);
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
		setFinished(false);
	}

    @Override
    public boolean isActive() {
        return isValueAlreadyAddedToCurrentCombination();
    }

    @Override
    public Rule combineWith(Rule rule) {
        return new CombinedRule(this, rule);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StatefulRule [");
        appendTriggeringPropertyList(stringBuilder);
        stringBuilder.append(targetedPropertyName).append("]");
        return stringBuilder.toString();
    }

    protected void appendTriggeringPropertyList(StringBuilder stringBuilder) {
    }

    @Override
    public Rule without(Rule rule) {
        return null;
    }

    @Override
    public void propertyCombinationStarted(EngineState engineState) {
    }

    @Override
    public void propertyRequired(EngineState engineState) {
    }

    protected boolean isValueAlreadyAddedToCurrentCombination() {
        return valueAlreadyAddedToCurrentCombination;
    }

    private void setValueAlreadyAddedToCurrentCombination(boolean valueAlreadyAddedToCurrentCombination) {
        this.valueAlreadyAddedToCurrentCombination = valueAlreadyAddedToCurrentCombination;
    }

    protected Condition getCondition() {
        return condition;
    }

    private boolean isFinished() {
        return finished;
    }

    protected void setFinished(boolean finished) {
        this.finished = finished;
    }
}
