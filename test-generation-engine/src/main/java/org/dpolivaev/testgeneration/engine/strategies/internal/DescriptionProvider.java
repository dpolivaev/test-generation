package org.dpolivaev.testgeneration.engine.strategies.internal;

import static java.util.Arrays.asList;
import static org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor.DEFAULT_SCRIPT_PROPERTY_NAME;
import static org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor.DEFAULT_TESTCASE_PROPERTY;

import java.util.ArrayList;
import java.util.Collection;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.AssignmentFormatter;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.PatternBasedAssignmentFilter;
import org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor;

public class DescriptionProvider  implements ValueProvider{

	final private String nameValueSeparator;
	final private String propertySeparator;
	final private PatternBasedAssignmentFilter forcedAssignmentFilter;
	final private PatternBasedAssignmentFilter excludedAssignmentFilter;


	public DescriptionProvider(String nameValueSeparator, String propertySeparator) {
		this.nameValueSeparator = nameValueSeparator;
		this.propertySeparator = propertySeparator;
		nameValueSeparator = propertySeparator;
		forcedAssignmentFilter = new PatternBasedAssignmentFilter();
		excludedAssignmentFilter = new PatternBasedAssignmentFilter();
	}

	public String describe(PropertyContainer assignments) {
		AssignmentFormatter formatter;
		formatter = AssignmentFormatter.create(nameValueSeparator, propertySeparator);
		excludedAssignmentFilter.addPattern("\\[.*");
		final AliasedPropertyAccessor aliasedPropertyAccessor = new AliasedPropertyAccessor(assignments);
		final String scriptProperty = aliasedPropertyAccessor.getAlias(DEFAULT_SCRIPT_PROPERTY_NAME);
		excludedAssignmentFilter.addPattern("(?:" + scriptProperty + ")(?:\\..+)?");
		excludedAssignmentFilter.addPattern("(" + DEFAULT_SCRIPT_PROPERTY_NAME + "|" + DEFAULT_TESTCASE_PROPERTY + ")\\.alias");
		final String testcaseProperty = aliasedPropertyAccessor.getAlias(DEFAULT_TESTCASE_PROPERTY) + "\\.name";
		formatter.exclude(testcaseProperty);
		formatter.excludeUndefined(true);
		formatter.appendReasons(false);
		final AssignmentPartitioner assignmentPartitioner = new AssignmentPartitioner(assignments);
		assignmentPartitioner.run();
		final Collection<Assignment> allAssignments = assignments.getAssignments();
		final ArrayList<Assignment> descriptionRelevantAssignments = new ArrayList<>(allAssignments.size());
		for(Assignment assignment : allAssignments){
			if(forcedAssignmentFilter.matches(assignment)
					|| assignmentPartitioner.isDescriptionRelevant(assignment.getTargetedPropertyName())
					&& ! excludedAssignmentFilter.matches(assignment))
				descriptionRelevantAssignments.add(assignment);
		}
		return formatter.format(descriptionRelevantAssignments);
	}
	
	@Override
	public Object value(PropertyContainer propertyContainer) {
		return describe(propertyContainer);
	}

	public DescriptionProvider exclude(String... propertyNames) {
		excludedAssignmentFilter.addPatterns(asList(propertyNames));
		return this;
	}

	public DescriptionProvider include(String... propertyNames) {
		forcedAssignmentFilter.addPatterns(asList(propertyNames));
		return this;
	}
}
