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
    private int combinationCounter;
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
        combinationCounter = 0;
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
        combinationCounter++;
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
        Collection<Rule> topRules = strategy.topRules();
        RuleEventPropagator propagator = new PropertyCombinationStartedPropagator(this);
        fireEvent(propagator, topRules);
	}

	@Override
    public void setPropertyValue(Rule rule, Object value, boolean valueChanged) {
        assignments.setPropertyValue(new Assignment(rule, value, assignmentReason));
        PropertyAssignedEvent event = new PropertyAssignedEvent(this, rule, dependencies, valueChanged);
        firePropertyAssignedEvent(assignments.firedRules(), event);
        if (!rule.isDefaultRule()) {
            firePropertyAssignedEvent(strategy.triggeredRules(), event);
        }
	}

    private void firePropertyAssignedEvent(Collection<Rule> rules, PropertyAssignedEvent event) {
        assignmentReason = event.getTargetedPropertyName() + "->";
        RuleEventPropagator propagator = new PropertyValueSetPropagator(event);
        fireEvent(propagator, rules);
    }

    private void fireEvent(RuleEventPropagator propagator, Collection<Rule> rules) {
        Set<String> oldDependencies = dependencies;
        for (Rule rule : rules) {
            if (!dependencies.isEmpty())
                dependencies = new HashSet<>();
            processedProperty = rule.getTargetedPropertyName();
            propagator.propagateEvent(rule);
        }
        dependencies = oldDependencies;
    }

	private boolean topRulesHaveFinished() {
        for (Rule rule : strategy.topRules())
            if (rule.blocksRequiredProperties())
				return false;
		return true;
	}

    @Override
    public int getCombinationCounter() {
        return combinationCounter;
    }

    @Override
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
            executeDefaultRulesForProperty(name);
            
        }
        dependencies.add(name);
		return assignments.get(name);
	}

    private void executeDefaultRulesForProperty(String name) {
        Rule defaultRule = strategy.getDefaultRulesForProperty(name);
        if(defaultRule == null)
            return;
        Set<String> oldDependencies = dependencies;
        dependencies = new HashSet<>();
        String oldAssignmentReason = assignmentReason;
        assignmentReason = processedProperty + "<-";
        processedProperty = name;
        defaultRule.propertyRequired(this);
        assignmentReason = oldAssignmentReason;
        dependencies = oldDependencies;
    }

    @Override
    public Strategy currentStrategy() {
        return strategy;
    }

    @Override
    public boolean containsPropertyValue(String name) {
        return assignments.containsProperty(name);
    }

    @Override
    public Set<String> availableProperties(String startWith) {
        HashSet<String> availableProperties = new HashSet<>(); 
        addMatchingStrings(availableProperties, startWith, assignments.assignedProperties());
        addMatchingStrings(availableProperties, startWith, strategy.availableDefaultProperties());
        return availableProperties;
    }

    private void addMatchingStrings(HashSet<String> availableProperties, String startWith, Set<String> strings) {
        for(String string : strings)
            if(string.startsWith(startWith))
                availableProperties.add(string);
    }
}
