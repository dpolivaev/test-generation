package org.dpolivaev.testgeneration.engine.testutils;

import java.io.StringWriter;
import java.io.Writer;

import org.dpolivaev.testgeneration.engine.ruleengine.PropertyCombinationHandler;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.ScriptLogger;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class CollectingScriptProducer extends PropertyCombinationHandler{

	private final ScriptLogger loggingScriptProducer;
	private Writer log;

	public CollectingScriptProducer() {
		log = new StringWriter();
		loggingScriptProducer = new ScriptLogger(log);
	}

	@Override
	public void handlePropertyCombination(PropertyContainer propertyContainer) {
		loggingScriptProducer.handlePropertyCombination(propertyContainer);
	}

	public String getAllScriptPropertyCombinations() {
		return log.toString();
	}

	public void appendReasons(boolean appendReasons) {
		loggingScriptProducer.appendReasons(appendReasons);
	}

	public void excludeUndefined(boolean excludeUndefined) {
		loggingScriptProducer.excludeUndefined(false);
		
	}
}
