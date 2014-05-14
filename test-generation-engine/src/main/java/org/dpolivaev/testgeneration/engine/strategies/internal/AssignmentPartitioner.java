package org.dpolivaev.testgeneration.engine.strategies.internal;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.PartValueParser;

class AssignmentPartitioner {
	private static final Pattern NUMBER_REGEX =  Pattern.compile("#\\d*");
	final private PropertyContainer propertyContainer;
	private Set<String> testPartRelevantAssignmentMap;
	private Set<String> testPartAssignmentMap;
	private Set<String> scriptPartRelevantAssignmentMap;
	private AliasedPropertyAccessor aliasedPropertyAccessor;

	public AssignmentPartitioner(PropertyContainer propertyContainer) {
		this.propertyContainer = propertyContainer;
	}

	private Set<String> testPartRelevantAssignmentMap(final String[] parts) {
		Set<String> testPartProperties = new HashSet<>();
		for(Assignment assignment : propertyContainer.getAssignments()){
			final String targetedPropertyName = assignment.getTargetedPropertyName();
			if(isTestPartRelevantProperty(parts, targetedPropertyName)){
				testPartAssignmentMap.add(targetedPropertyName);
				for(String requiredProperty : assignment.requiredProperties)
					testPartProperties.add(requiredProperty);
				final PartValueParser partValueParser = new PartValueParser(assignment.value.toString());
				testPartProperties.add(targetedPropertyName);
				final String[] argumentList = partValueParser.getArgumentList();
				for(String argument:argumentList)
					if(argument.startsWith(":")){
						String propertyName = argument.substring(1);
						if(! testPartProperties.contains(propertyName)) {
							final Assignment argumentAssignment = propertyContainer.getAssignment(propertyName);
							if(argumentAssignment != null)
								testPartProperties.add(propertyName);
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

	public boolean isDescriptionRelevant(final String targetedPropertyName) {
		final boolean isDescriptionRelevant = ! isTestIdRelevant(targetedPropertyName)
				&& ! scriptPartRelevantAssignmentMap.contains(targetedPropertyName)
				&& ! aliasedPropertyAccessor.isAlias(targetedPropertyName);
		return isDescriptionRelevant;
	}

	public boolean isTestIdRelevant(String targetedPropertyName) {
		return testPartRelevantAssignmentMap.contains(targetedPropertyName);
	}

	public boolean isTestPartMethodCall(String targetedPropertyName) {
		return testPartAssignmentMap.contains(targetedPropertyName);
	}

	public void run() {
		testPartAssignmentMap = new HashSet<>();
		aliasedPropertyAccessor = new AliasedPropertyAccessor(propertyContainer);
		testPartRelevantAssignmentMap = testPartRelevantAssignmentMap(aliasedPropertyAccessor.getTestCaseParts());
		scriptPartRelevantAssignmentMap = testPartRelevantAssignmentMap(aliasedPropertyAccessor.getScriptParts());
	}
}
