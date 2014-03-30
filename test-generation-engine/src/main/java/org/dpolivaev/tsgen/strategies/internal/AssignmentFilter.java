package org.dpolivaev.tsgen.strategies.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.dpolivaev.tsgen.ruleengine.Assignment;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.scriptwriter.AliasedPropertyAccessor;
import org.dpolivaev.tsgen.scriptwriter.internal.PartValueParser;

public class AssignmentFilter {
	private static final Pattern NUMBER_REGEX =  Pattern.compile("#\\d*");
	final private PropertyContainer propertyContainer;

	public AssignmentFilter(PropertyContainer propertyContainer) {
		this.propertyContainer = propertyContainer;
	}

	public Collection<Assignment> testPartRelevantAssignments() {
		Map<String, Assignment> testPartProperties = testPartRelevantAssignmentMap(new AliasedPropertyAccessor(propertyContainer).getTestCaseParts());
		return testPartProperties.values();
	}

	private Map<String, Assignment> testPartRelevantAssignmentMap(final String[] parts) {
		Map<String, Assignment> testPartProperties = new LinkedHashMap<>();
		for(Assignment assignment : propertyContainer.getAssignments()){
			final String targetedPropertyName = assignment.getTargetedPropertyName();
			if(isTestPartRelevantProperty(parts, targetedPropertyName)){
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

	private boolean isTestPartRelevantProperty(String[] parts, final String propertyName) {
		int i = 0;
		for(String testCasePart : parts)
			if(i++ % 2 == 0 && propertyName.startsWith(testCasePart)) {
				final String numberCandidate = propertyName.substring(testCasePart.length());
				return numberCandidate.isEmpty() || NUMBER_REGEX.matcher(numberCandidate).matches();
			}
		return false;
	}

	public Collection<Assignment> descriptionRelevantAssignments() {
		final Collection<Assignment> allAssignments = propertyContainer.getAssignments();
		final ArrayList<Assignment> descriptionRelevantAssignments = new ArrayList<>(allAssignments.size());
		final AliasedPropertyAccessor aliasedPropertyAccessor = new AliasedPropertyAccessor(propertyContainer);
		final Map<String, Assignment> testPartRelevantAssignmentMap = testPartRelevantAssignmentMap(aliasedPropertyAccessor.getTestCaseParts());
		final Map<String, Assignment> scriptPartRelevantAssignmentMap = testPartRelevantAssignmentMap(aliasedPropertyAccessor.getScriptParts());
		for(Assignment assignment : allAssignments){
			final String targetedPropertyName = assignment.getTargetedPropertyName();
			if(! testPartRelevantAssignmentMap.containsKey(targetedPropertyName)
					&& ! scriptPartRelevantAssignmentMap.containsKey(targetedPropertyName)
					&& ! aliasedPropertyAccessor.isAlias(targetedPropertyName))
				descriptionRelevantAssignments.add(assignment);
		}
		return descriptionRelevantAssignments;
	}
}
