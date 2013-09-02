package scriptproducer;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.transform.Source;

import ruleengine.RuleEngine;
import ruleengine.Strategy;
import scriptproducer.impl.LoggingScriptProducer;
import scriptproducer.impl.MultipleScriptsProducer;
import scriptproducer.impl.StreamResultFactory;
import utils.Utils;

public class StrategyRunner {
	private Source xsltSource = null;
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
	
	public Source getXsltSource() {
		return xsltSource;
	}
	public StrategyRunner apply(Source xsltSource) {
		this.xsltSource = xsltSource;
		return this;
	}
	
}

