package org.dpolivaev.testgeneration.engine.ruleengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.ruleengine.internal.PropertyAssignedEvent;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.PropertyCombinationStartedPropagator;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.PropertyValueSetPropagator;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.RuleEventPropagator;
import org.dpolivaev.testgeneration.engine.utils.internal.Utils;


/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngine implements EngineState {
    private Strategy strategy;
    final private Assignments assignments;
    private final Collection<PropertyHandler> propertyHandlers;
    private Set<String> dependencies;
    private String assignmentReason;
	private String processedProperty;
	private final Collection<ErrorHandler> errorHandlers;

	public RuleEngine() {
        super();
        this.assignments = new Assignments();
        this.strategy = null;
        dependencies = new HashSet<>();
        propertyHandlers = new ArrayList<>();
        errorHandlers = new ArrayList<>();
    }

    public RuleEngine addHandler(PropertyHandler propertyHandler) {
		propertyHandlers.add(propertyHandler);
		return this;
	}

    public void removeHandler(PropertyHandler propertyHandler) {
		propertyHandlers.remove(propertyHandler);
	}

    public void addErrorHandler(ErrorHandler errorHandler) {
    	errorHandlers.add(errorHandler);
	}

    public void removeErrorHandler(ErrorHandler errorHandler) {
    	errorHandlers.remove(errorHandler);
	}

    public void run(Strategy strategy) {
        this.strategy = strategy;
        strategy.initialize(this);
        assignments.resetCounter();
        try {
        	fireGenerationStarted();
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
            fireGenerationFinished();
        }
        finally {
            this.strategy = null;
        }
	}

	private void fireGenerationStarted() {
		for(PropertyHandler propertyHandler :propertyHandlers)
			propertyHandler.generationStarted(this);
	}

	private void fireGenerationFinished() {
		for(PropertyHandler propertyHandler :propertyHandlers)
			propertyHandler.generationFinished();
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
        handlePropertyCombination();
    }

	private void handlePropertyCombination() {
		for(PropertyHandler propertyHandler :propertyHandlers)
        	propertyHandler.handlePropertyCombination(this);
	}

    private void fireNextCombinationFinishedEvent() {
        for (Rule rule : assignments.firedRules())
        	if(rule.isLazyRule()) {
				String name = rule.getTargetedPropertyName();
				getLazyRulesForProperty(name).propertyCombinationFinished(this);
			}
        for (Rule rule : strategy.triggeredRules())
            rule.propertyCombinationFinished(this);
	}

	private Rule getLazyRulesForProperty(String name) {
		Rule lazyRule = strategy.getLazyRulesForProperty(name);
		if(lazyRule != null)
			return lazyRule;
		Assignment assignment = assignments.getAssignment(name);
		if(assignment != null && assignment.rule.isLazyRule())
			return assignment.rule;
		else
			return RuleBuilder.Factory.iterate(name).over(SpecialValue.UNDEFINED).asLazyRule().create();
	}

	private void fireNextCombinationStartedEvent() {
        Iterable<Rule> topRules = strategy.triggeredRules();
        RuleEventPropagator propagator = new PropertyCombinationStartedPropagator(this);
        fireEvent(propagator, topRules);
	}

	@Override
    public void setPropertyValue(Rule rule, Object value) {

		assignments.add(new Assignment(rule, value, assignmentReason, dependencies, rule.getTriggeringProperties()));
        PropertyAssignedEvent event = new PropertyAssignedEvent(this, rule, dependencies);
        firePropertyAssignedEvent(assignments.firedRules(), event);
        if (!rule.isLazyRule()) {
            firePropertyAssignedEvent(strategy.triggeredRules(), event);
        }
	}

    private void firePropertyAssignedEvent(Iterable<Rule> rules, PropertyAssignedEvent event) {
    	String oldAssignmentReason = assignmentReason;
        assignmentReason = event.getTargetedPropertyName() + "->";
        RuleEventPropagator propagator = new PropertyValueSetPropagator(event);
        fireEvent(propagator, rules);
        assignmentReason = oldAssignmentReason;
    }

    private void fireEvent(RuleEventPropagator propagator, Iterable<Rule> rules) {
        Set<String> oldDependencies = dependencies;
        String oldProcessedProperty = processedProperty;
        for (Rule rule : rules) {
            if (!dependencies.isEmpty())
                dependencies = new HashSet<>();
            processedProperty = rule.getTargetedPropertyName();
            propagator.propagateEvent(rule);
        }
        dependencies = oldDependencies;
        processedProperty = oldProcessedProperty;
    }

	private boolean topRulesHaveFinished() {
        for (Rule rule : strategy.triggeredRules())
            if (rule.blocksRequiredProperties())
				return false;
		return true;
	}

    @Override
    public int getCombinationCounter() {
        return assignments.getCombinationCounter();
    }

	@SuppressWarnings("unchecked")
    @Override
    public <T> T get(String name) {
        Assignment oldAssignment = assignments.getAssignment(name);
		if (oldAssignment == null || oldAssignment.rule.isLazyRule()) {
            executeLazyRulesForProperty(name);
            
        }
        dependencies.add(name);
		T value = (T) assignments.get(name);
		return value;
	}
	

	@Override
	public Assignment getAssignment(String name) {
		return assignments.getAssignment(name);
	}


    private void executeLazyRulesForProperty(String name) {
        Rule lazyRule = getLazyRulesForProperty(name);
        if(lazyRule == null)
            return;
        Set<String> oldDependencies = dependencies;
        dependencies = new HashSet<>();
        String oldAssignmentReason = assignmentReason;
        String oldProcessedProperty = processedProperty;
        assignmentReason = processedProperty + "<-";
        processedProperty = name;
        lazyRule.propertyRequired(this);
        assignmentReason = oldAssignmentReason;
        processedProperty = oldProcessedProperty;
        dependencies = oldDependencies;
    }

    @Override
    public Strategy currentStrategy() {
        return strategy;
    }

    @Override
    public boolean isPropertyAvailable(String name) {
        return assignments.isPropertyAvailable(name) || strategy.availableLazyProperties().contains(name);
    }

    @Override
    public boolean containsTriggeringPropertyValue(String name) {
        return assignments.containsTriggeringPropertyValue(name);
    }

    @Override
    public Set<String> availableProperties(String startWith) {
        Set<String> availableProperties = assignments.availableProperties(startWith); 
        Utils.addMatchingStrings(availableProperties, startWith, strategy.availableLazyProperties());
        return availableProperties;
    }

    @Override
    public Collection<Assignment> getAssignments() {
        return assignments.getAssignments();
    }
    
    public String getAssignmentReason() {
		return assignmentReason;
	}

	@Override
	public boolean containsCompatibleRule(Rule rule) {
		return currentStrategy().containsCompatibleRule(rule);
	}

	@Override
	public void addTemporaryRule(Rule creatingRule, Rule rule) {
		currentStrategy().addTemporaryRule(creatingRule, rule);
	}

	@Override
	public String toString() {
		AssignmentFormatter assignmentFormatter = new AssignmentFormatterFactory().createDetailedAssignmentFormatter();
		return "Combination " + getCombinationCounter() + ":\n" + assignmentFormatter.format(this) + '\n';
	}
	
	
}
