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
    private Set<String> dependencies;
    private int count;

    public RuleEngine(ScriptProducer scriptProducer) {
        super();
        this.mapBasedState = new MapBasedState();
        this.scriptProducer = scriptProducer;
        this.strategy = null;
        dependencies = new HashSet<>();
    }

    public void run(Strategy strategy) {
        this.strategy = strategy;
        count = 0;
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
        count++;
        mapBasedState.nextCombination();
        fireNextCombinationStartedEvent();
        dependencies = new HashSet<>();
        scriptProducer.makeScriptFor(this);
    }

	private void fireNextCombinationFinishedEvent() {
        for (Rule rule : strategy.topRules())
            rule.propertyCombinationFinished(this);
	}

	private void fireNextCombinationStartedEvent() {
        Set<String> oldDependencies = dependencies;
        for (Rule rule : strategy.topRules()) {
            if (!dependencies.isEmpty())
                dependencies = new HashSet<>();
            rule.propertyCombinationStarted(this);
        }
        dependencies = oldDependencies;
	}

	@Override
    public void setPropertyValue(Rule rule, Object value, boolean valueChanged) {
        mapBasedState.setPropertyValue(new PropertyAssignment(rule, value, ""));
        if (!rule.isDefaultRule()) {
            PropertyAssignedEvent event = new PropertyAssignedEvent(this, rule, dependencies, valueChanged);
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
            if (rule.blocksRequiredProperties())
				return false;
		return true;
	}

	public String getAssignedPropertiesAsString() {
        return count + " : " + new StateFormatter().format(mapBasedState) + '\n';
	}

	@Override
	public boolean containsPropertyValues(Set<String> names) {
		return mapBasedState.containsProperties(names);
	}

	@Override
    public Object get(String name) {
        if (!mapBasedState.containsProperty(name)) {
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
