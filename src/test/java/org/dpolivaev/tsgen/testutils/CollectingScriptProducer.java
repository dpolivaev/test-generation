package org.dpolivaev.tsgen.testutils;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;

import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.scriptwriter.internal.ScriptLogger;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class CollectingScriptProducer implements PropertyHandler {

	private final ScriptLogger loggingScriptProducer;
	private Writer log;

	public CollectingScriptProducer() {
		log = new StringWriter();
		loggingScriptProducer = new ScriptLogger(log);
	}

	@Override
	public void handlePropertyCombination(PropertyContainer propertyContainer, Collection<Goal> goals) {
		loggingScriptProducer.handlePropertyCombination(propertyContainer, goals);
	}

	public String getAllScriptPropertyCombinations() {
		return log.toString();
	}

	public void appendReasons(boolean appendReasons) {
		loggingScriptProducer.appendReasons(appendReasons);
	}
}
