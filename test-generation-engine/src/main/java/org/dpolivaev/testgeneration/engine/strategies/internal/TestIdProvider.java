package org.dpolivaev.testgeneration.engine.strategies.internal;


import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.AssignmentFormatter;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor;


public class TestIdProvider implements ValueProvider{
	
	final private String propertySeparator;
	final private String valueNameSeparator;
	private String[] forcedNames;

	
	public TestIdProvider() {
		this("=", " ");
	}

	public TestIdProvider(String valueNameSeparator, String propertySeparator) {
		super();
		this.valueNameSeparator = valueNameSeparator;
		this.propertySeparator = propertySeparator;
		forcedNames  = new String[]{};
	}
	
	@Override
	public Object value(final PropertyContainer propertyContainer) {
		AssignmentFormatter assignmentFormatter = new AssignmentFormatter(propertySeparator, valueNameSeparator){

			@Override
			protected boolean includesAssignment(Assignment assignment) {
				return assignment.rule.forcesIteration()
						|| assignment.getTargetedPropertyName().equals(new AliasedPropertyAccessor(propertyContainer).getFocusPropertyName())
						|| Arrays.asList(forcedNames).contains(assignment.getTargetedPropertyName());
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
		Collection<Assignment> testPartProperties = new AssignmentFilter(propertyContainer).testPartRelevantAssignments();
		LinkedHashSet<Assignment> relevantProperties = new LinkedHashSet<>(testPartProperties);
		for(String forcedProperty : forcedNames){
			if(propertyContainer.get(forcedProperty) != SpecialValue.UNDEFINED){
				final Assignment assignment = propertyContainer.getAssignment(forcedProperty);
				relevantProperties.add(assignment);
			}
		}
		final String values = assignmentFormatter.format(relevantProperties);
		return values.trim();
	}

	public TestIdProvider include(String... propertyNames) {
		forcedNames = propertyNames;
		return this;
	}
}
