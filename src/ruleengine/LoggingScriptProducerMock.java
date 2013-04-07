package ruleengine;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class LoggingScriptProducerMock implements ScriptProducer {

	private StringBuilder log = new StringBuilder();

	@Override
	public void makeScriptFor(RuleEngine ruleEngine) {
		log.append(ruleEngine.getAssignedPropertiesAsString());
	}

	public String getAllScriptPropertyCombinations() {
		return log.toString();
	}
}
