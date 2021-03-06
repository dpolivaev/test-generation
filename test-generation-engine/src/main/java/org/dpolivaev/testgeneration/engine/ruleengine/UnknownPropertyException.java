package org.dpolivaev.testgeneration.engine.ruleengine;

@SuppressWarnings("serial")
public class UnknownPropertyException extends RuntimeException {

    public UnknownPropertyException(String propertyName) {
        super("Unknown property " + propertyName + "requested");
    }

}
