package org.dpolivaev.tsgen.strategies.internal;

import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.Assignment;
import org.dpolivaev.tsgen.ruleengine.AssignmentFormatter;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;


public class TestIdProvider implements ValueProvider{
	
	public static Strategy strategy(OutputConfiguration outputConfiguration, String propertyName){
		Strategy strategy = new Strategy();
		TestIdProvider instance = new TestIdProvider(outputConfiguration, "=", " ");
		strategy.addDefaultRule(RuleBuilder.Factory.iterate(propertyName).over(instance));
		return strategy;

	}; 
	
	final String propertySeparator;
	final String valueNameSeparator;
	final private OutputConfiguration outputConfiguration;

	
	public TestIdProvider(OutputConfiguration outputConfiguration, String valueNameSeparator, String propertySeparator) {
		super();
		this.outputConfiguration = outputConfiguration;
		this.valueNameSeparator = valueNameSeparator;
		this.propertySeparator = propertySeparator;
	}
	
	@Override
	public Object value(PropertyContainer propertyContainer) {
		AssignmentFormatter assignmentFormatter = new AssignmentFormatter(propertySeparator, valueNameSeparator){

			@Override
			protected boolean includesAssignment(Assignment assignment) {
				return assignment.rule.forcesIteration() || assignment.getTargetedPropertyName().equals(outputConfiguration.getFocusPropertyName());
			}

			@Override
			protected AssignmentFormatter append(
					StringBuilder assignedPropertiesStringBuilder,
					Assignment assignment) {
				Object value = assignment.value;
				final boolean isTrue = Boolean.TRUE.equals(value);
				final boolean isNumber = value instanceof Number;
				if(isTrue || isNumber)
					appendName(assignedPropertiesStringBuilder, assignment);
				if(value instanceof Boolean)
					return this;
				if(isNumber)
					appendNameValueSeparator(assignedPropertiesStringBuilder, assignment);
				appendValue(assignedPropertiesStringBuilder, assignment);
				return this;
			}

			@Override
			protected void appendName(
					StringBuilder assignedPropertiesStringBuilder,
					Assignment assignment) {
				String targetedPropertyName = assignment.getTargetedPropertyName();
				final int endOfTestPartName = targetedPropertyName.indexOf('.');
				assignedPropertiesStringBuilder.append(targetedPropertyName.substring(endOfTestPartName + 1));
			}
		};
		assignmentFormatter.appendReasons(false);
		Collection<Assignment> testPartProperties = new AssignmentFilter(outputConfiguration, propertyContainer).testPartRelevantAssignments();
		final String values = assignmentFormatter.format(testPartProperties);
		return values.trim();
	}
}
