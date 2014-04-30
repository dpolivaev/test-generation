package org.dpolivaev.testgeneration.engine.ruleengine;

@SuppressWarnings("serial")
public class InvalidCombinationException extends RuntimeException {
    static final InvalidCombinationException INSTANCE = new InvalidCombinationException();

    public InvalidCombinationException() {
        super();
    }

    public InvalidCombinationException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidCombinationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCombinationException(String message) {
        super(message);
    }

    public InvalidCombinationException(Throwable cause) {
        super(cause);
    }

}
