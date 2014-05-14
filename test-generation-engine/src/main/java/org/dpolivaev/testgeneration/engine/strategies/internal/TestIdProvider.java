package org.dpolivaev.testgeneration.engine.strategies.internal;


import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.AssignmentFormatter;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor;


public class TestIdProvider implements ValueProvider{
	
	final private String propertySeparator;
	final private String valueNameSeparator;
	private Collection<String> forcedNames;

	
	public TestIdProvider() {
		this("=", " ");
	}

	public TestIdProvider(String valueNameSeparator, String propertySeparator) {
		super();
		this.valueNameSeparator = valueNameSeparator;
		this.propertySeparator = propertySeparator;
		forcedNames  = Collections.emptyList();
	}
	
	@Override
	public Object value(final PropertyContainer propertyContainer) {
		final Collection<Assignment> testPartProperties = new AssignmentPartitioner(propertyContainer).testPartRelevantAssignments();
		final LinkedHashSet<Assignment> relevantProperties = new LinkedHashSet<>(testPartProperties);
		for(String forcedProperty : forcedNames){
			if(propertyContainer.get(forcedProperty) != SpecialValue.UNDEFINED){
				final Assignment assignment = propertyContainer.getAssignment(forcedProperty);
				relevantProperties.add(assignment);
			}
		}
		AssignmentFormatter assignmentFormatter = new AssignmentFormatter(propertySeparator, valueNameSeparator){

			@Override
			protected boolean includesAssignment(Assignment assignment) {
				return propertyCanHaveDifferentValues(assignment)
						|| assignment.getTargetedPropertyName().equals(new AliasedPropertyAccessor(propertyContainer).getFocusPropertyName())
						|| forcedNames.contains(assignment.getTargetedPropertyName());
			}

			private boolean propertyCanHaveDifferentValues(Assignment assignment) {
				final Rule rule = assignment.rule;
				if(rule.isDefaultRule())
					return false;
				if(rule.forcesIteration())
					return true;
				final Collection<String> requiredProperties = new HashSet<>(assignment.requiredProperties);
				requiredProperties.addAll(assignment.triggeringProperties);
				for(String requiredProperty : requiredProperties){
					final Assignment requiredAssignment = propertyContainer.getAssignment(requiredProperty);
					if(testPartProperties.contains(requiredAssignment)|| new AliasedPropertyAccessor(propertyContainer).isPart(requiredProperty))
						return false;
					if(requiredAssignment.rule.forcesIteration())
						return true;
				}
				return false;

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
		final String values = assignmentFormatter.format(relevantProperties);
		return values.trim();
	}

	public TestIdProvider include(String... propertyNames) {
		forcedNames = asList(propertyNames);
		return this;
	}
}
