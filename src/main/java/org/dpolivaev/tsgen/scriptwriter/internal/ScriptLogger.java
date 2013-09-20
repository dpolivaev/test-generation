package org.dpolivaev.tsgen.scriptwriter.internal;

import java.io.IOException;
import java.io.Writer;

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.AssignmentFormatter;
import org.dpolivaev.tsgen.ruleengine.ErrorHandler;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ScriptWriter;
import org.dpolivaev.tsgen.utils.internal.Utils;

public class ScriptLogger implements ScriptWriter, ErrorHandler {
	final private Writer log;
	final private AssignmentFormatter assignmentFormatter;

	public ScriptLogger(Writer writer) {
		log = writer;
		assignmentFormatter = AssignmentFormatter.create("=", ", ");
		assignmentFormatter.appendReasons(false);
	}

	@Override
	public void createScriptFor(PropertyContainer propertyContainer, CoverageTracker coverage) {
		try {
			log.append(propertyContainer.getCombinationCounter() + " : "
					+ assignmentFormatter.format(propertyContainer) + '\n');
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
}