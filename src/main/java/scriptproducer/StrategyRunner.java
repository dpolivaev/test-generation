package scriptproducer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import ruleengine.RuleEngine;
import ruleengine.Strategy;
import scriptproducer.internal.LoggingScriptProducer;
import scriptproducer.internal.MultipleScriptsProducer;
import scriptproducer.internal.StreamResultFactory;
import utils.Utils;

public class StrategyRunner {
	private Source xsltSource = null;
	private String fileExtension = "xml";
	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public void run(Strategy strategy){
		RuleEngine ruleEngine = new RuleEngine();
		OutputStreamWriter writer = new OutputStreamWriter(System.out);
		LoggingScriptProducer logger = new LoggingScriptProducer(writer);
		ruleEngine.addScriptProducer(logger);
		ruleEngine.addErrorHandler(logger);
		StreamResultFactory resultFactory = new StreamResultFactory();
		resultFactory.setFileExtension(fileExtension);
		MultipleScriptsProducer scriptProducer = new MultipleScriptsProducer(resultFactory);
		scriptProducer.setXsltSource(xsltSource);
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
	public StrategyRunner apply(Source xsltSource, String fileExtension) {
		this.xsltSource = xsltSource;
		this.fileExtension = fileExtension;
		return this;
	}

	public StrategyRunner apply(String xsltSource, String fileExtension) {
		File xsltFile = new File(xsltSource);
		if(xsltFile.canRead())
			return apply(new StreamSource(xsltFile), fileExtension);
		InputStream resource = getClass().getResourceAsStream(xsltSource);
		if(resource != null)
			return apply(new StreamSource(resource), fileExtension);
		throw new IllegalArgumentException("source " + xsltSource + " not available");
	}
}

