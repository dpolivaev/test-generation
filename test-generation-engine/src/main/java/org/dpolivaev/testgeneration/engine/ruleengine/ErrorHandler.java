package org.dpolivaev.testgeneration.engine.ruleengine;

public interface ErrorHandler {

	void handleError(Exception e, PropertyContainer propertyContainer);

}
