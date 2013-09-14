package org.dpolivaev.tsgen.strategies.internal;

import org.dpolivaev.tsgen.ruleengine.AssignmentFormatter;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

public class DescriptionProvider  implements ValueProvider{

	private AssignmentFormatter formatter;

	public DescriptionProvider() {
		this(": ", "\n");
	}

	public DescriptionProvider(String nameValueSeparator, String propertySeparator) {
		formatter = AssignmentFormatter.create(nameValueSeparator, propertySeparator);
		formatter.exclude("requirement\\..*");
		formatter.appendReasons(true);
	}

	public String describe(PropertyContainer assignments) {
		return formatter.format(assignments);
	}

	public static Strategy strategy(String propertyName){
		Strategy strategy = new Strategy();
		DescriptionProvider instance = new DescriptionProvider(": ", "\n");
		strategy.addDefaultRule(RuleBuilder.Factory.iterate(propertyName).over(instance));
		return strategy;

	}; 
	
	@Override
	public Object value(PropertyContainer propertyContainer) {
		return describe(propertyContainer);
	}
}
