package ruleengine;

import static ruleengine.TestUtils.set;

import java.util.List;
import java.util.Set;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngine implements State {
	private Rules rules = new Rules();
	private MapBasedState mapBasedState = new MapBasedState();

	public void addRule(Rule rule) {
		rules.addRule(rule);
	}

	public boolean hasRuleForProperty(String propertyName) {
		return rules.hasRuleForProperty(propertyName);
	}

	public void run(ScriptProducer scriptProducer) {
		do {
			mapBasedState.nextIteration();
			fireNextIterationStartedEvent();
			scriptProducer.makeScriptFor(this);
			fireNextIterationFinishedEvent();

		} while (!allRulesHaveFinished());
	}

	private void fireNextIterationFinishedEvent() {
		for (Rule rule : rules())
			rule.finishIteration(this);
	}

	private void fireNextIterationStartedEvent() {
		for (Rule rule : rules())
			rule.nextIteration(this);
	}

	@Override
	public void setPropertyValue(Rule rule, Object nextValue) {
		mapBasedState.setPropertyValue(rule, nextValue);
		PropertyAssignedEvent event = new PropertyAssignedEvent(this, rule,
				set());
		firePropertyAssignedEvent(event);
	}

	private void firePropertyAssignedEvent(PropertyAssignedEvent event) {
		for (Rule rule : rules()) {
			rule.propertyValueSet(event);
		}
	}

	private boolean allRulesHaveFinished() {
		for (Rule rule : rules())
			if (!rule.hasFinished())
				return false;
		return true;
	}

	private List<Rule> rules() {
		return rules.values();
	}

	public String getAssignedPropertiesAsString() {
		return mapBasedState.getAssignedPropertiesAsString();
	}

	@Override
	public boolean containsPropertyValue(String name) {
		return mapBasedState.containsPropertyValue(name);
	}

	@Override
	public boolean containsPropertyValues(Set<String> names) {
		return mapBasedState.containsPropertyValues(names);
	}

}
