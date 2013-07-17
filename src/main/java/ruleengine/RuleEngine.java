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
        super();
        this.mapBasedState = new MapBasedState();
        this.scriptProducer = scriptProducer;
        this.strategy = null;
    }

    public void run(Strategy strategy) {
        this.strategy = strategy;
        try {
            do {
                Set<String> oldDependencies = dependencies;
                try {
                    generateCombination();
                }
                catch (InvalidCombinationException e) {
                }
                dependencies = oldDependencies;
            	fireNextCombinationFinishedEvent();

            } while (!topRulesHaveFinished());
        }
        finally {
            this.strategy = null;
        }
	}

    private void generateCombination() {
        mapBasedState.nextCombination();
        fireNextCombinationStartedEvent();
        dependencies = new HashSet<>();
        scriptProducer.makeScriptFor(this);
    }

	private void fireNextCombinationFinishedEvent() {
        for (Rule rule : strategy.topRules())
            rule.propertyCombinationFinished(this);
	}

    private Set<String> dependencies;
	private void fireNextCombinationStartedEvent() {
        Set<String> oldDependencies = dependencies;
        for (Rule rule : strategy.topRules()) {
            dependencies = new HashSet<>();
            rule.propertyCombinationStarted(this);
        }
        dependencies = oldDependencies;
	}

	@Override
	public void setPropertyValue(Rule rule, Object nextValue) {
		mapBasedState.setPropertyValue(rule, nextValue);
        if (!rule.isDefaultRule()) {
            PropertyAssignedEvent event = new PropertyAssignedEvent(this, rule, dependencies);
            firePropertyAssignedEvent(mapBasedState.firedRules(), event);
            firePropertyAssignedEvent(strategy.triggeredRules(), event);
        }
	}

    private void firePropertyAssignedEvent(Collection<Rule> rules, PropertyAssignedEvent event) {
        Set<String> oldDependencies = dependencies;
        for (Rule rule : rules) {
            if (!dependencies.isEmpty())
                dependencies = new HashSet<>();
			rule.propertyValueSet(event);
		}
        dependencies = oldDependencies;
	}

	private boolean topRulesHaveFinished() {
        for (Rule rule : strategy.topRules())
            if (!rule.hasFinished())
				return false;
		return true;
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
            executeDefaultRulesForProperty(name);
            dependencies = oldDependencies;
        }
        dependencies.add(name);
		return mapBasedState.get(name);
	}

    private void executeDefaultRulesForProperty(String name) {
        strategy.getDefaultRulesForProperty(name).propertyRequired(this);
    }

    @Override
    public Strategy currentStrategy() {
        return strategy;
    }
}
