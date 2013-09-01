package ruleengine;

public interface ErrorHandler {

	void handleError(Exception e, PropertyContainer propertyContainer);

}
