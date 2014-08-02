package org.dpolivaev.testgeneration.engine.strategies.internal;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor;
import org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.PartValueParser;

class AssignmentPartitioner {
	final private PropertyContainer propertyContainer;
	private Set<String> testPartRelevantAssignmens;
	private Set<String> testPartAssignments;
	private Set<String> scriptPartRelevantAssignments;
	private AliasedPropertyAccessor aliasedPropertyAccessor;

	public AssignmentPartitioner(PropertyContainer propertyContainer) {
		this.propertyContainer = propertyContainer;
	}

	private void addParts(Set<String> testPartProperties, String property) {
        if(propertyContainer.isPropertyAvailable(property))
		addTestPartAssignments(testPartProperties, property);
        for(int i = 1; i <= OutputConfiguration.TEST_PART_NUMBER_MAXIMUM; i++) {
			final String numberedPartName = property + '#' + i;
			if(propertyContainer.isPropertyAvailable(numberedPartName))
		addTestPartAssignments(testPartProperties, numberedPartName);
		}
    }

	private Set<String> testPartRelevantAssignmentMap(final String[] parts) {
		Set<String> testPartProperties = new LinkedHashSet<>();
		for(int i = 0; i < parts.length; i+=2)
			addParts(testPartProperties, parts[i]);
		return testPartProperties;
	}

	public void addTestPartAssignments(Set<String> testPartProperties, String targetedPropertyName) {
		testPartAssignments.add(targetedPropertyName);
		if (propertyContainer.get(targetedPropertyName) != SpecialValue.UNDEFINED){
			final Assignment assignment = propertyContainer.getAssignment(targetedPropertyName);
			for(String requiredProperty : assignment.requiredProperties)
				testPartProperties.add(requiredProperty);
			final PartValueParser partValueParser = new PartValueParser(propertyContainer, assignment.value.toString());
			testPartProperties.add(targetedPropertyName);
			addTestPartAssignments(testPartProperties, partValueParser.getMethodPropertyList());
			addTestPartAssignments(testPartProperties, partValueParser.getArgumentList());
		}
	}

	private void addTestPartAssignments(Set<String> testPartProperties,
			final String[] propertyList) {
		for(String argument:propertyList){
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

	public boolean isDescriptionRelevant(final String targetedPropertyName) {
		final boolean isDescriptionRelevant = ! isTestIdRelevant(targetedPropertyName)
				&& ! scriptPartRelevantAssignments.contains(targetedPropertyName)
				&& ! aliasedPropertyAccessor.isAlias(targetedPropertyName);
		return isDescriptionRelevant;
	}

	public boolean isTestIdRelevant(String targetedPropertyName) {
		return getTestPartRelevantAssignmens().contains(targetedPropertyName);
	}

	public boolean isTestPartMethodCall(String targetedPropertyName) {
		return testPartAssignments.contains(targetedPropertyName);
	}

	public Set<String> getTestPartRelevantAssignmens() {
		return testPartRelevantAssignmens;
	}

	public void run() {
		testPartAssignments = new HashSet<>();
		aliasedPropertyAccessor = new AliasedPropertyAccessor(propertyContainer);
		testPartRelevantAssignmens = testPartRelevantAssignmentMap(aliasedPropertyAccessor.getTestCaseParts());
		scriptPartRelevantAssignments = testPartRelevantAssignmentMap(aliasedPropertyAccessor.getScriptParts());
	}
}
