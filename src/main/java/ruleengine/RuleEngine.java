package ruleengine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngine implements EngineState {
    final private RuleMap rules;
    final private MapBasedState mapBasedState;

    public RuleEngine() {
        this(new RuleMap(), new MapBasedState());
    }

    public RuleEngine(RuleMap rules, MapBasedState mapBasedState) {
        super();
        this.rules = rules;
        this.mapBasedState = mapBasedState;
    }

    @Override
    public void addRule(Rule rule) {
		rules.addRule(rule);
	}

    @Override
    public void removeRule(Rule rule) {
        rules.removeRule(rule);
    }

	public boolean hasRuleForProperty(String propertyName) {
		return rules.hasRuleForProperty(propertyName);
	}

	public void run(ScriptProducer scriptProducer) {
		do {
			mapBasedState.nextCombination();
			fireNextCombinationStartedEvent();
            Set<String> oldDependencies = dependencies;
            dependencies = new HashSet<>();
			scriptProducer.makeScriptFor(this);
            dependencies = oldDependencies;
			fireNextCombinationFinishedEvent();

		} while (!topRulesHaveFinished());
	}

	private void fireNextCombinationFinishedEvent() {
		for (Rule rule : rules())
            rule.propertyCombinationFinished(this);
	}

    private Set<String> dependencies;
	private void fireNextCombinationStartedEvent() {
        Set<String> oldDependencies = dependencies;
        for (Rule rule : rules()) {
            dependencies = new HashSet<>();
            rule.propertyCombinationStarted(this);
        }
        dependencies = oldDependencies;
	}

	@Override
	public void setPropertyValue(Rule rule, Object nextValue) {
		mapBasedState.setPropertyValue(rule, nextValue);
        if (rule.canTriggerOtherRules()) {
            PropertyAssignedEvent event = new PropertyAssignedEvent(this, rule, requiredProperties(rule));
            firePropertyAssignedEvent(event);
        }
	}

    private Set<String> requiredProperties(Rule rule) {
        Set<String> requiredProperties;
        if (dependencies.isEmpty()) {
            requiredProperties = rule.getTriggeringProperties();
        }
        else {
            requiredProperties = new HashSet<>(rule.getTriggeringProperties());
            requiredProperties.addAll(dependencies);
        }
        return requiredProperties;
    }

	private void firePropertyAssignedEvent(PropertyAssignedEvent event) {
        Set<String> oldDependencies = dependencies;
		for (Rule rule : rules()) {
            dependencies = new HashSet<>();
			rule.propertyValueSet(event);
		}
        dependencies = oldDependencies;
	}

	private boolean topRulesHaveFinished() {
		for (Rule rule : rules())
            if (rule.getTriggeringProperties().isEmpty() && !rule.hasFinished())
				return false;
		return true;
	}

	private Collection<Rule> rules() {
		return rules.rules();
	}

	public String getAssignedPropertiesAsString() {
		return mapBasedState.getAssignedPropertiesAsString();
	}

	@Override
	public boolean containsPropertyValues(Set<String> names) {
		return mapBasedState.containsPropertyValues(names);
	}

	@Override
    public Object get(String name) {
        if (!mapBasedState.containsPropertyValue(name)) {
            Set<String> oldDependencies = dependencies;
            dependencies = new HashSet<>();
            getPropertyValueFromDefaultRules(name);
            dependencies = oldDependencies;
        }
        dependencies.add(name);
		return mapBasedState.get(name);
	}

    private void getPropertyValueFromDefaultRules(String name) {
        rules.getRuleForProperty(name).propertyRequired(this);
    }

    @Override
    public void addRule(StatefulRuleBuilder builder) {
        addRule(builder.asRule());
    }
}
