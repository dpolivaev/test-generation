package org.dpolivaev.tsgen.ruleengine;

public interface ErrorHandler {

	void handleError(Exception e, PropertyContainer propertyContainer);

}
