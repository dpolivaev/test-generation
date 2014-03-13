package org.dpolivaev.tsgen.strategies.internal;

import org.dpolivaev.tsgen.ruleengine.AssignmentFormatter;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;

public class DescriptionProvider  implements ValueProvider{

	private AssignmentFormatter formatter;
	final private OutputConfiguration outputConfiguration;

	public DescriptionProvider() {
		this(new OutputConfiguration(), ": ", "\n");
	}

	public DescriptionProvider(OutputConfiguration outputConfiguration, String nameValueSeparator, String propertySeparator) {
		this.outputConfiguration = outputConfiguration;
		formatter = AssignmentFormatter.create(nameValueSeparator, propertySeparator);
		formatter.exclude("\\[.*");
		formatter.exclude("(?:script)(?:\\..+)?");
		formatter.exclude("testcase");
		formatter.excludeUndefined(true);
		formatter.appendReasons(false);
	}

	public String describe(PropertyContainer assignments) {
		final AssignmentFilter assignmentFilter = new AssignmentFilter(outputConfiguration, assignments);
		return formatter.format(assignmentFilter.descriptionRelevantAssignments());
	}

	public static Strategy strategy(OutputConfiguration outputConfiguration, String propertyName){
		Strategy strategy = new Strategy();
		DescriptionProvider instance = new DescriptionProvider(outputConfiguration, ": ", "\n");
		strategy.addDefaultRule(RuleBuilder.Factory.iterate(propertyName).over(instance));
		return strategy;

	}; 
	
	@Override
	public Object value(PropertyContainer propertyContainer) {
		return describe(propertyContainer);
	}
}
