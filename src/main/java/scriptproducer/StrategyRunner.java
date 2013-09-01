package scriptproducer;

import java.io.IOException;
import java.io.OutputStreamWriter;

import ruleengine.RuleEngine;
import ruleengine.Strategy;
import utils.Utils;

public class StrategyRunner {
	  public void run(Strategy strategy){
		RuleEngine ruleEngine = new RuleEngine();
		OutputStreamWriter writer = new OutputStreamWriter(System.out);
		LoggingScriptProducer logger = new LoggingScriptProducer(writer);
		ruleEngine.addScriptProducer(logger);
		ruleEngine.addErrorHandler(logger);
		StreamResultFactory resultFactory = new StreamResultFactory();
		MultipleScriptsProducer scriptProducer = new MultipleScriptsProducer(resultFactory);
		ruleEngine.addScriptProducer(scriptProducer);
		ruleEngine.run(strategy);
		try {
			scriptProducer.endScripts();
			writer.close();
		} catch (IOException e) {
			throw Utils.runtimeException(e);
		}
	  }
	
}

