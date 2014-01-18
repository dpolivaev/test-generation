package org.dpolivaev.tsgen.strategies.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.dpolivaev.tsgen.ruleengine.Assignment;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.scriptwriter.internal.PartValueParser;

public class AssignmentFilter {
	private static final Pattern TEST_CASE_PARTS_REGEX =  Pattern.compile("(?:precondition|focus|verification|postprocessing)(?:\\d+)?");
	final private PropertyContainer propertyContainer;

	public AssignmentFilter(PropertyContainer propertyContainer) {
		this.propertyContainer = propertyContainer;
	}

	public Collection<Assignment> testPartRelevantAssignments() {
		Map<String, Assignment> testPartProperties = testPartRelevantAssignmentMap();
		return testPartProperties.values();
	}

	public Map<String, Assignment> testPartRelevantAssignmentMap() {
		Map<String, Assignment> testPartProperties = new LinkedHashMap<>();
		for(Assignment assignment : propertyContainer.getAssignments()){
			final String targetedPropertyName = assignment.getTargetedPropertyName();
			if(TEST_CASE_PARTS_REGEX.matcher(targetedPropertyName).matches()){
				final PartValueParser partValueParser = new PartValueParser(assignment.value.toString());
				testPartProperties.put(targetedPropertyName, new Assignment(assignment.rule, partValueParser.getCalledMethod(), assignment.reason));
				if(partValueParser.isArgumentListFound()){
					final String[] argumentList = partValueParser.getArgumentList();
					for(String argument:argumentList)
						if(! testPartProperties.containsKey(argument)) {
							final Assignment argumentAssignment = propertyContainer.getAssignment(argument);
							if(argumentAssignment != null)
								testPartProperties.put(argument, argumentAssignment);
						}
				}
			}
		}
		return testPartProperties;
	}

	public Collection<Assignment> descriptionRelevantAssignments() {
		final Collection<Assignment> allAssignments = propertyContainer.getAssignments();
		final ArrayList<Assignment> descriptionRelevantAssignments = new ArrayList<>(allAssignments.size());
		final Map<String, Assignment> testPartRelevantAssignmentMap = testPartRelevantAssignmentMap();
		for(Assignment assignment : allAssignments){
			if(! testPartRelevantAssignmentMap.containsKey(assignment.getTargetedPropertyName()))
				descriptionRelevantAssignments.add(assignment);
		}
		return descriptionRelevantAssignments;
	}
}
