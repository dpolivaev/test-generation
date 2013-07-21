package ruleengine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngine implements EngineState {
    private Strategy strategy;
    final private Assignments assignments;
    private final ScriptProducer scriptProducer;
    private Set<String> dependencies;
    private int combinationCount;
    private String assignmentReason;
    private String processedProperty;

    public RuleEngine(ScriptProducer scriptProducer) {
        super();
        this.assignments = new Assignments();
        this.scriptProducer = scriptProducer;
        this.strategy = null;
        dependencies = new HashSet<>();
    }

    public void run(Strategy strategy) {
        this.strategy = strategy;
        combinationCount = 0;
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
        combinationCount++;
        assignments.clear();
        assignmentReason = "->";
        fireNextCombinationStartedEvent();
        dependencies = new HashSet<>();
        processedProperty = "";
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
            processedProperty = rule.getTargetedPropertyName();
            rule.propertyCombinationStarted(this);
        }
        dependencies = oldDependencies;
	}

	@Override
    public void setPropertyValue(Rule rule, Object value, boolean valueChanged) {
        assignments.setPropertyValue(new Assignment(rule, value, assignmentReason));
        if (!rule.isDefaultRule()) {
            PropertyAssignedEvent event = new PropertyAssignedEvent(this, rule, dependencies, valueChanged);
            firePropertyAssignedEvent(assignments.firedRules(), event);
            firePropertyAssignedEvent(strategy.triggeredRules(), event);
        }
	}

    private void firePropertyAssignedEvent(Collection<Rule> rules, PropertyAssignedEvent event) {
        Set<String> oldDependencies = dependencies;
        assignmentReason = event.getTargetedPropertyName() + "->";
        for (Rule rule : rules) {
            if (!dependencies.isEmpty())
                dependencies = new HashSet<>();
            processedProperty = rule.getTargetedPropertyName();
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

    public int getCombinationCount() {
        return combinationCount;
    }

    public Assignments getAssignments() {
        return assignments;
    }

    @Override
	public boolean containsPropertyValues(Set<String> names) {
		return assignments.containsProperties(names);
	}

	@Override
    public Object get(String name) {
        if (!assignments.containsProperty(name)) {
            Set<String> oldDependencies = dependencies;
            dependencies = new HashSet<>();
            String oldAssignmentReason = assignmentReason;
            assignmentReason = processedProperty + "<-";
            processedProperty = name;
            executeDefaultRulesForProperty(name);
            assignmentReason = oldAssignmentReason;
            dependencies = oldDependencies;
        }
        dependencies.add(name);
		return assignments.get(name);
	}

    private void executeDefaultRulesForProperty(String name) {
        strategy.getDefaultRulesForProperty(name).propertyRequired(this);
    }

    @Override
    public Strategy currentStrategy() {
        return strategy;
    }
}
