package org.dpolivaev.tsgen.scriptwriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
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
		GoalChecker goalChecker = new GoalChecker();
		goalChecker.addGoal(new Goal("requirements", new RequirementCoverage()));
		ruleEngine.setGoalChecker(goalChecker);
		StreamResultFactory resultFactory = new StreamResultFactory();
		MultipleScriptsWriter scriptProducer = new MultipleScriptsWriter(resultFactory, GoalChecker.NO_GOALS);
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
	
	public StrategyRunner configureOutput(Source xsltSource, String fileExtension, boolean outputXml) {
		return configureOutput(xsltSource, null, fileExtension, outputXml);
	}

	public StrategyRunner configureOutput(Source xsltSource, File outputDirectory, String fileExtension, boolean outputXml) {
		this.outputConfiguration = new OutputConfiguration(xsltSource, outputDirectory, fileExtension, outputXml);
		return this;
	}

	public StrategyRunner configureOutput(String fileExtension) {
		return configureOutput((Source)null, fileExtension, false);
	}
	
	public StrategyRunner configureOutput(String xsltSource, String fileExtension, boolean outputXml) {
		return configureOutput(xsltSource, null, fileExtension, outputXml);
	}
	
	public StrategyRunner configureOutput(String xsltSource, File outputDirectory, String fileExtension, boolean outputXml) {
		File xsltFile = new File(xsltSource);
		if(xsltFile.canRead())
			return configureOutput(new StreamSource(xsltFile), outputDirectory, fileExtension, outputXml);
		InputStream resource = getClass().getResourceAsStream(xsltSource);
		if(resource != null)
			return configureOutput(new StreamSource(resource), outputDirectory, fileExtension, outputXml);
		throw new IllegalArgumentException("source " + xsltSource + " not available");
	}

	public StrategyRunner configureOutput(File outputDirectory, String fileExtension) {
		return configureOutput((Source)null, outputDirectory, fileExtension, false);
	}
}

