package ruleengine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngine implements EngineState {
    private Strategy strategy;
    final private MapBasedState mapBasedState;
    private final ScriptProducer scriptProducer;

    public RuleEngine(ScriptProducer scriptProducer) {
        this(scriptProducer, new MapBasedState());
    }

    public RuleEngine(ScriptProducer scriptProducer, MapBasedState mapBasedState) {
        super();
        this.scriptProducer = scriptProducer;
        this.mapBasedState = mapBasedState;
        strategy = null;
    }

    @Override
    public void addRule(Rule rule) {
		strategy.addRule(rule);
	}

    @Override
    public void removeRule(Rule rule) {
        strategy.removeRule(rule);
    }

    public void run(Strategy strategy) {
        this.strategy = strategy;
        try {
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
        finally {
            this.strategy = null;
        }
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
		return strategy.rules();
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
        strategy.getRuleForProperty(name).propertyRequired(this);
    }

    public void addRule(StatefulRuleBuilder builder) {
        strategy.addRule(builder.asRule());
    }
}
