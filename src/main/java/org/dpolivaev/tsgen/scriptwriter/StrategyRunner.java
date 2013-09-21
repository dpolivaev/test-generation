package org.dpolivaev.tsgen.scriptwriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.dpolivaev.tsgen.coverage.internal.RequirementCoverage;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.internal.ScriptLogger;
import org.dpolivaev.tsgen.scriptwriter.internal.MultipleScriptsWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.StreamResultFactory;
import org.dpolivaev.tsgen.utils.internal.Utils;

public class StrategyRunner {
	private OutputConfiguration outputConfiguration;

	public StrategyRunner() {
		super();
		this.outputConfiguration = OutputConfiguration.OUTPUT_XML;
	}

	public void run(Strategy strategy){
		RuleEngine ruleEngine = new RuleEngine();
		OutputStreamWriter writer = new OutputStreamWriter(System.out);
		ScriptLogger logger = new ScriptLogger(writer);
		ruleEngine.addScriptWriter(logger);
		ruleEngine.addErrorHandler(logger);
		ruleEngine.addGoal(new RequirementCoverage());
		StreamResultFactory resultFactory = new StreamResultFactory();
		MultipleScriptsWriter scriptProducer = new MultipleScriptsWriter(resultFactory);
		scriptProducer.setOutputConfiguration(outputConfiguration);
		ruleEngine.addScriptWriter(scriptProducer);
		ruleEngine.run(strategy);
		try {
			scriptProducer.endScripts();
			writer.close();
		} catch (IOException e) {
			throw Utils.runtimeException(e);
		}
	}
	
	public StrategyRunner addOutput(Source xsltSource, String fileExtension) {
		this.outputConfiguration = new OutputConfiguration(xsltSource, fileExtension);
		return this;
	}

	public StrategyRunner addOutput(String fileExtension) {
		return addOutput((Source)null, fileExtension);
	}
	
	public StrategyRunner addOutput(String xsltSource, String fileExtension) {
		File xsltFile = new File(xsltSource);
		if(xsltFile.canRead())
			return addOutput(new StreamSource(xsltFile), fileExtension);
		InputStream resource = getClass().getResourceAsStream(xsltSource);
		if(resource != null)
			return addOutput(new StreamSource(resource), fileExtension);
		throw new IllegalArgumentException("source " + xsltSource + " not available");
	}
}

