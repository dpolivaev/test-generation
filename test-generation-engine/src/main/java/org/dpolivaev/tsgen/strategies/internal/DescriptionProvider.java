package org.dpolivaev.tsgen.strategies.internal;

import org.dpolivaev.tsgen.ruleengine.AssignmentFormatter;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;

public class DescriptionProvider  implements ValueProvider{

	private AssignmentFormatter formatter;

	public DescriptionProvider() {
		this(": ", "\n");
	}

	public DescriptionProvider(String nameValueSeparator, String propertySeparator) {
		formatter = AssignmentFormatter.create(nameValueSeparator, propertySeparator);
		formatter.exclude("\\[.*");
		formatter.exclude("(?:script)(?:\\..+)?");
		formatter.exclude("testcase");
		formatter.excludeUndefined(true);
		formatter.appendReasons(false);
	}

	public String describe(PropertyContainer assignments) {
		final AssignmentFilter assignmentFilter = new AssignmentFilter(assignments);
		return formatter.format(assignmentFilter.descriptionRelevantAssignments());
	}

	public static Strategy strategy(OutputConfiguration outputConfiguration, String propertyName){
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
