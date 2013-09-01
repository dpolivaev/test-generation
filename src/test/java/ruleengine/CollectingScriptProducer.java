package ruleengine;

import java.io.StringWriter;
import java.io.Writer;

import scriptproducer.LoggingScriptProducer;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class CollectingScriptProducer implements ScriptProducer {

	private final LoggingScriptProducer loggingScriptProducer;
	private Writer log;

	public CollectingScriptProducer() {
		log = new StringWriter();
		loggingScriptProducer = new LoggingScriptProducer(log);
	}

	@Override
	public void makeScriptFor(PropertyContainer propertyContainer) {
		loggingScriptProducer.makeScriptFor(propertyContainer);
	}

	public String getAllScriptPropertyCombinations() {
		return log.toString();
	}

	public void appendReasons(boolean appendReasons) {
		loggingScriptProducer.appendReasons(appendReasons);
	}
}
