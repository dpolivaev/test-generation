package ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ruleengine.internal.Assignment;
import ruleengine.internal.Assignments;
import ruleengine.internal.PropertyAssignedEvent;
import ruleengine.internal.PropertyCombinationStartedPropagator;
import ruleengine.internal.PropertyValueSetPropagator;
import ruleengine.internal.RuleEventPropagator;
import utils.Utils;


/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngine implements EngineState {
    private Strategy strategy;
    final private Assignments assignments;
    private final Collection<ScriptProducer> scriptProducers;
    private Set<String> dependencies;
    private String assignmentReason;
    private String processedProperty;
	private final Collection<ErrorHandler> errorHandlers;

    public RuleEngine(ScriptProducer scriptProducer) {
    	this();
        addScriptProducer(scriptProducer);
    }

    public RuleEngine() {
        super();
        this.assignments = new Assignments();
        this.strategy = null;
        dependencies = new HashSet<>();
        scriptProducers = new ArrayList<>();
        errorHandlers = new ArrayList<>();
    }

    public void addScriptProducer(ScriptProducer scriptProducer) {
		scriptProducers.add(scriptProducer);
	}

    public void removeScriptProducer(ScriptProducer scriptProducer) {
		scriptProducers.remove(scriptProducer);
	}

    public void addErrorHandler(ErrorHandler errorHandler) {
    	errorHandlers.add(errorHandler);
	}

    public void removeErrorHandler(ErrorHandler errorHandler) {
    	errorHandlers.remove(errorHandler);
	}

    public void run(Strategy strategy) {
        this.strategy = strategy;
        assignments.resetCounter();
        try {
            do {
                Set<String> oldDependencies = dependencies;
                try {
                    generateCombination();
                }
                catch (InvalidCombinationException e) {
                }
                catch (RuntimeException e) {
                	handle(e);
                	throw e;
                }
                dependencies = oldDependencies;
            	fireNextCombinationFinishedEvent();

            } while (!topRulesHaveFinished());
        }
        finally {
            this.strategy = null;
        }
	}

    private void handle(Exception e) {
    	for (ErrorHandler errorHandler : errorHandlers){
    		try{
    			errorHandler.handleError(e, this);
    		}
    		catch(Exception producerException){
    			
    		}
    	}
	}

	private void generateCombination() {
        assignments.startNewCombination();
        assignmentReason = "->";
        fireNextCombinationStartedEvent();
        dependencies = new HashSet<>();
        processedProperty = "";
        for(ScriptProducer scriptProducer :scriptProducers)
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
        assignments.add(new Assignment(rule, value, assignmentReason));
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
        return assignments.getCombinationCounter();
    }

    @Override
	public boolean containsPropertyValues(Set<String> names) {
		return assignments.containsPropertyValues(names);
	}

	@SuppressWarnings("unchecked")
    @Override
    public <T> T get(String name) {
        if (!assignments.containsProperty(name)) {
            executeDefaultRulesForProperty(name);
            
        }
        dependencies.add(name);
		return (T) assignments.get(name);
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
        return assignments.containsPropertyValue(name) || strategy.availableDefaultProperties().contains(name);
    }

    @Override
    public boolean containsTriggeringPropertyValue(String name) {
        return assignments.containsTriggeringPropertyValue(name);
    }

    @Override
    public Set<String> availableProperties(String startWith) {
        Set<String> availableProperties = assignments.availableProperties(startWith); 
        Utils.addMatchingStrings(availableProperties, startWith, strategy.availableDefaultProperties());
        return availableProperties;
    }

    @Override
    public Map<String, Assignment> getAssignmentsAsMap() {
        return assignments.getAssignmentsAsMap();
    }
}
