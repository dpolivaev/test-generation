package ruleengine;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class LoggingScriptProducerMock implements ScriptProducer {

    private StringBuilder log;
    private AssignmentFormatter assignmentFormatter;

	public LoggingScriptProducerMock() {
        log = new StringBuilder();
        assignmentFormatter = new AssignmentFormatter();
        assignmentFormatter.appendReasons(false);
    }

    @Override
	public void makeScriptFor(PropertyContainer propertyContainer) {
		log.append(propertyContainer.getCombinationCounter() + " : " + assignmentFormatter.format(propertyContainer) + '\n');
	}

	public String getAllScriptPropertyCombinations() {
		return log.toString();
	}

    public void appendReasons(boolean appendReasons) {
        assignmentFormatter.appendReasons(appendReasons);
    }
}
