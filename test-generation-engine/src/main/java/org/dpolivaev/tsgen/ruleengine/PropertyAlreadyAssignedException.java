package org.dpolivaev.tsgen.ruleengine;

@SuppressWarnings("serial")
public class PropertyAlreadyAssignedException extends RuntimeException {

	public PropertyAlreadyAssignedException(String targetedPropertyName) {
		super("Property " +  targetedPropertyName + " already assigned");
	}

}
