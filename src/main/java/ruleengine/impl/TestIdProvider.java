package ruleengine.impl;

import java.util.regex.Pattern;

import ruleengine.AssignmentFormatter;
import ruleengine.PropertyContainer;
import ruleengine.RuleBuilder;
import ruleengine.Strategy;
import ruleengine.ValueProvider;

public class TestIdProvider implements ValueProvider{
	final static private String RS = "\u001E";
	final static private String US = "\u001F";
	final static private AssignmentFormatter assignmentFormatter;
	
	public static Strategy strategy(String propertyName){
		Strategy strategy = new Strategy();
		TestIdProvider instance = new TestIdProvider("=", " ");
		strategy.addDefaultRule(RuleBuilder.Factory.iterate(propertyName).over(instance));
		return strategy;

	}; 
	static {
		assignmentFormatter = AssignmentFormatter.create(US, RS);
		assignmentFormatter.shouldFormatIteratingRulesOnly(true);
		assignmentFormatter.appendReasons(false);
	}
	
	final static private Pattern truePattern = Pattern.compile("([^" + RS + US + "]+" + ")" + US + "true(" +RS + "|$)", Pattern.CASE_INSENSITIVE); 
	final static private Pattern falsePattern = Pattern.compile("(?:[^" + RS + US + "]+" + ")" + US + "false(?:" +RS + "|$)", Pattern.CASE_INSENSITIVE); 
	final static private Pattern recordSeparatorPattern = Pattern.compile(RS); 
	final static private Pattern unitSeparatorPattern = Pattern.compile(US);
	
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
