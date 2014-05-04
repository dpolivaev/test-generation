package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import java.io.IOException;
import java.io.Writer;

import org.dpolivaev.testgeneration.engine.ruleengine.AssignmentFormatter;
import org.dpolivaev.testgeneration.engine.ruleengine.ErrorHandler;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;
import org.dpolivaev.testgeneration.engine.utils.internal.Utils;

public class ScriptLogger implements PropertyHandler, ErrorHandler {
	final private Writer log;
	final private AssignmentFormatter assignmentFormatter;

	public ScriptLogger(Writer writer) {
		log = writer;
		assignmentFormatter = AssignmentFormatter.create("=", ", ");
		assignmentFormatter.appendReasons(false);
		assignmentFormatter.excludeUndefined(true);
	}

	@Override
	public void handlePropertyCombination(PropertyContainer propertyContainer) {
		try {
			log.append(propertyContainer.getCombinationCounter() + " : "
					+ assignmentFormatter.format(propertyContainer).replace('\n', ' ') + '\n');
			log.flush();
		} catch (IOException e) {
			throw Utils.runtimeException(e);
		}
	}

	public void appendReasons(boolean appendReasons) {
		assignmentFormatter.appendReasons(appendReasons);
	}

	@Override
	public void handleError(Exception error, PropertyContainer propertyContainer) {
		boolean appendsReasons = assignmentFormatter.appendsReasons();
		String propertySeparator = assignmentFormatter.getPropertySeparator();
		try{
			assignmentFormatter.appendReasons(true);
			assignmentFormatter.setPropertySeparator("\n");
			try {
				log.append(error.getMessage() + '\n');
				log.append(propertyContainer.getCombinationCounter() + " : "
						+ assignmentFormatter.format(propertyContainer) + '\n');
				log.flush();
			} catch (IOException e) {
				throw Utils.runtimeException(e);
			}
		}
		finally{
			assignmentFormatter.appendReasons(appendsReasons);
			assignmentFormatter.setPropertySeparator(propertySeparator);
		}
	}

	@Override
	public void generationStarted(PropertyContainer propertyContainer) {}
	
	@Override
	public void generationFinished() {
		try {
			log.flush();
		} catch (IOException e) {
			throw Utils.runtimeException(e);
		}
	}
}