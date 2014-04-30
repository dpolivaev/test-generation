package org.dpolivaev.testgeneration.engine.strategies.internal;

import java.util.Collection;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.AssignmentFormatter;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor;


public class TestIdProvider implements ValueProvider{
	
	final String propertySeparator;
	final String valueNameSeparator;

	
	public TestIdProvider(String valueNameSeparator, String propertySeparator) {
		super();
		this.valueNameSeparator = valueNameSeparator;
		this.propertySeparator = propertySeparator;
	}
	
	@Override
	public Object value(final PropertyContainer propertyContainer) {
		AssignmentFormatter assignmentFormatter = new AssignmentFormatter(propertySeparator, valueNameSeparator){

			@Override
			protected boolean includesAssignment(Assignment assignment) {
				return assignment.rule.forcesIteration() || assignment.getTargetedPropertyName().equals(new AliasedPropertyAccessor(propertyContainer).getFocusPropertyName());
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
		final String values = assignmentFormatter.format(testPartProperties);
		return values.trim();
	}
}
