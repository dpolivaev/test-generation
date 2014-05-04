package org.dpolivaev.testgeneration.engine.strategies.internal;

import static org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor.DEFAULT_SCRIPT_PROPERTY_NAME;
import static org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor.DEFAULT_TESTCASE_PROPERTY;

import org.dpolivaev.testgeneration.engine.ruleengine.AssignmentFormatter;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor;

public class DescriptionProvider  implements ValueProvider{

	final private String nameValueSeparator;
	final private String propertySeparator;

	public DescriptionProvider(String nameValueSeparator, String propertySeparator) {
		this.nameValueSeparator = nameValueSeparator;
		this.propertySeparator = propertySeparator;
		nameValueSeparator = propertySeparator;
	}

	public String describe(PropertyContainer assignments) {
		AssignmentFormatter formatter;
		formatter = AssignmentFormatter.create(nameValueSeparator, propertySeparator);
		formatter.exclude("\\[.*");
		final AliasedPropertyAccessor aliasedPropertyAccessor = new AliasedPropertyAccessor(assignments);
		final String scriptProperty = aliasedPropertyAccessor.getAlias(DEFAULT_SCRIPT_PROPERTY_NAME);
		formatter.exclude("(?:" + scriptProperty + ")(?:\\..+)?");
		formatter.exclude("(" + DEFAULT_SCRIPT_PROPERTY_NAME + "|" + DEFAULT_TESTCASE_PROPERTY + ")\\.alias");
		final String testcaseProperty = aliasedPropertyAccessor.getAlias(DEFAULT_TESTCASE_PROPERTY);
		formatter.exclude(testcaseProperty);
		formatter.excludeUndefined(true);
		formatter.appendReasons(false);
		final AssignmentFilter assignmentFilter = new AssignmentFilter(assignments);
		return formatter.format(assignmentFilter.descriptionRelevantAssignments());
	}
	
	@Override
	public Object value(PropertyContainer propertyContainer) {
		return describe(propertyContainer);
	}
}