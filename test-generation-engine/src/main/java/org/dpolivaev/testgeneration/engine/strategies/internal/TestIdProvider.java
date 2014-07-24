package org.dpolivaev.testgeneration.engine.strategies.internal;


import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.AssignmentFormatter;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.PatternBasedAssignmentFilter;
import org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.PartValueParser;


public class TestIdProvider implements ValueProvider{
	
	final private String propertySeparator;
	final private String valueNameSeparator;
	final private PatternBasedAssignmentFilter excludedAssignmentFilter;
	private Set<String> forcedProperties;

	
	public TestIdProvider() {
		this("=", " ");
	}

	public TestIdProvider(String valueNameSeparator, String propertySeparator) {
		super();
		this.valueNameSeparator = valueNameSeparator;
		this.propertySeparator = propertySeparator;
		excludedAssignmentFilter = new PatternBasedAssignmentFilter();
		forcedProperties = Collections.<String>emptySet();
	}
	
	private boolean propertyCanHaveDifferentValues(Assignment assignment, AssignmentPartitioner assignmentPartitioner, PropertyContainer propertyContainer) {
		final Rule rule = assignment.rule;
		if(rule.isLazyRule())
			return false;
		if(rule.forcesIteration())
			return true;
		final Collection<String> requiredProperties = new HashSet<>(assignment.requiredProperties);
		for(String requiredProperty : requiredProperties){
			if(assignmentPartitioner.isTestIdRelevant(requiredProperty)|| new AliasedPropertyAccessor(propertyContainer).isPart(requiredProperty))
				return false;
			final Assignment requiredAssignment = propertyContainer.getAssignment(requiredProperty);
			if(requiredAssignment.rule.forcesIteration())
				return true;
		}
		return false;

	}
	@Override
	public Object value(final PropertyContainer propertyContainer) {
		final AssignmentPartitioner assignmentPartitioner = new AssignmentPartitioner(propertyContainer);
		assignmentPartitioner.run();
		final Collection<Assignment> relevantProperties = new ArrayList<>();
		final LinkedHashSet<String> assignments = new LinkedHashSet<>(assignmentPartitioner.getTestPartRelevantAssignmens());
		propertyContainer.getAssignments();
		assignments.addAll(forcedProperties);
		for(String targetedPropertyName : assignments){
			Assignment assignment = propertyContainer.getAssignment(targetedPropertyName);
			if(assignment != null && (forcedProperties.contains(targetedPropertyName)
					|| assignmentPartitioner.isTestIdRelevant(targetedPropertyName)
					 && (propertyCanHaveDifferentValues(assignment, assignmentPartitioner, propertyContainer)
							|| targetedPropertyName.equals(new AliasedPropertyAccessor(propertyContainer).getFocusPropertyName()))
					 && ! excludedAssignmentFilter.matches(assignment))){
				if(assignmentPartitioner.isTestPartMethodCall(targetedPropertyName)){
					final PartValueParser partValueParser = new PartValueParser(assignment.value.toString());
					relevantProperties.add(new Assignment(assignment.rule, partValueParser.getCalledMethod(), assignment.reason, assignment.requiredProperties, assignment.triggeringProperties));
				}
				else
					relevantProperties.add(assignment);
			}
		}
		AssignmentFormatter assignmentFormatter = new AssignmentFormatter(propertySeparator, valueNameSeparator){

			@Override
			protected boolean includesAssignment(Assignment assignment) {
				return true;
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

	public TestIdProvider exclude(String... propertyNames) {
		excludedAssignmentFilter.addPatterns(asList(propertyNames));
		return this;
	}



	public TestIdProvider include(String... propertyNames) {
		forcedProperties = new LinkedHashSet<>(asList(propertyNames));
		return this;
	}
}
