package ruleengine;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class CountingScriptProducerMock implements ScriptProducer {

	private int callCount = 0;

	public int callCount() {
		return callCount;
	}

	@Override
	public void makeScriptFor(RuleEngine ruleEngine) {
		callCount++;
	}

}
