package scriptproducer;

import java.io.IOException;
import java.io.Writer;

import ruleengine.AssignmentFormatter;
import ruleengine.ErrorHandler;
import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;
import utils.Utils;

public class LoggingScriptProducer implements ScriptProducer, ErrorHandler {
	final private Writer log;
	final private AssignmentFormatter assignmentFormatter;

	public LoggingScriptProducer(Writer writer) {
		log = writer;
		assignmentFormatter = new AssignmentFormatter();
		assignmentFormatter.appendReasons(false);
		assignmentFormatter.setPropertySeparator(", ");
	}

	@Override
	public void makeScriptFor(PropertyContainer propertyContainer) {
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