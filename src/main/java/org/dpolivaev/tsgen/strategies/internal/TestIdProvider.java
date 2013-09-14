package org.dpolivaev.tsgen.strategies.internal;

import java.util.regex.Pattern;

import org.dpolivaev.tsgen.ruleengine.AssignmentFormatter;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

public class TestIdProvider implements ValueProvider{
	final static private String VALUE_NAME_SEPARATOR_CHAR = "\u001F"; // Unicode unit separator char
	final static private String PROPERTY_SEPARATOR_CHAR = "\u001E";	// Unicode record separator char
	final static private AssignmentFormatter assignmentFormatter;
	
	public static Strategy strategy(String propertyName){
		Strategy strategy = new Strategy();
		TestIdProvider instance = new TestIdProvider("=", " ");
		strategy.addDefaultRule(RuleBuilder.Factory.iterate(propertyName).over(instance));
		return strategy;

	}; 
	static {
		assignmentFormatter = AssignmentFormatter.create(VALUE_NAME_SEPARATOR_CHAR, PROPERTY_SEPARATOR_CHAR);
		assignmentFormatter.shouldFormatIteratingRulesOnly(true);
		assignmentFormatter.appendReasons(false);
	}
	
	final static private Pattern truePattern = Pattern.compile("([^" + PROPERTY_SEPARATOR_CHAR + VALUE_NAME_SEPARATOR_CHAR + "]+" + ")" + VALUE_NAME_SEPARATOR_CHAR + "true(" +PROPERTY_SEPARATOR_CHAR + "|$)", Pattern.CASE_INSENSITIVE); 
	final static private Pattern falsePattern = Pattern.compile("(?:[^" + PROPERTY_SEPARATOR_CHAR + VALUE_NAME_SEPARATOR_CHAR + "]+" + ")" + VALUE_NAME_SEPARATOR_CHAR + "false(?:" +PROPERTY_SEPARATOR_CHAR + "|$)", Pattern.CASE_INSENSITIVE); 
	final static private Pattern recordSeparatorPattern = Pattern.compile(PROPERTY_SEPARATOR_CHAR); 
	final static private Pattern unitSeparatorPattern = Pattern.compile(VALUE_NAME_SEPARATOR_CHAR);
	
	final String propertySeparator;
	final String valueNameSeparator;

	
	public TestIdProvider(String valueNameSeparator, String propertySeparator) {
		super();
		this.valueNameSeparator = valueNameSeparator;
		this.propertySeparator = propertySeparator;
	}

	public String idFrom(String string) {
		String id = string;
		id = truePattern.matcher(id).replaceAll("$1$2");
		id = falsePattern.matcher(id).replaceAll("");
		id = recordSeparatorPattern.matcher(id).replaceAll(propertySeparator);
		id = unitSeparatorPattern.matcher(id).replaceAll(valueNameSeparator);
		return id;
	}
	
	@Override
	public Object value(PropertyContainer propertyContainer) {
		final String values = assignmentFormatter.format(propertyContainer);
		return idFrom(values);
	}

}
