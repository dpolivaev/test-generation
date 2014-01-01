package org.dpolivaev.tsgen.strategies.internal;

import org.dpolivaev.tsgen.ruleengine.Assignment;
import org.dpolivaev.tsgen.ruleengine.AssignmentFormatter;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

public class TestIdProvider implements ValueProvider{
	
	public static final String TEST_CASE_PARTS_REGEX = "(?:pre|foc|veri|post)(?:\\d+)?(?:\\..+)?";


	public static Strategy strategy(String propertyName){
		Strategy strategy = new Strategy();
		TestIdProvider instance = new TestIdProvider("=", " ");
		strategy.addDefaultRule(RuleBuilder.Factory.iterate(propertyName).over(instance));
		return strategy;

	}; 
	
	final String propertySeparator;
	final String valueNameSeparator;

	
	public TestIdProvider(String valueNameSeparator, String propertySeparator) {
		super();
		this.valueNameSeparator = valueNameSeparator;
		this.propertySeparator = propertySeparator;
	}
	
	@Override
	public Object value(PropertyContainer propertyContainer) {
		AssignmentFormatter assignmentFormatter = new AssignmentFormatter(propertySeparator, valueNameSeparator){

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
		assignmentFormatter.shouldFormatIteratingRulesOnly(true);
		assignmentFormatter.appendReasons(false);
		assignmentFormatter.include(TEST_CASE_PARTS_REGEX);
		final String values = assignmentFormatter.format(propertyContainer);
		return values;
	}

}
