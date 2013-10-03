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
import org.dpolivaev.tsgen.scriptwriter.internal.ReportWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.ScriptLogger;
import org.dpolivaev.tsgen.scriptwriter.internal.MultipleScriptsWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.StreamResultFactory;
import org.dpolivaev.tsgen.utils.internal.Utils;

public class StrategyRunner {
	private OutputConfiguration outputConfiguration;
	private ScriptConfiguration reportConfiguration;

	public StrategyRunner() {
		super();
		this.outputConfiguration = OutputConfiguration.OUTPUT_XML;
		this.reportConfiguration = new ScriptConfiguration(OutputConfiguration.OUTPUT_XML, "report");
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
		ReportWriter reportWriter = new ReportWriter(resultFactory);
		reportWriter.createReport(goalChecker, reportConfiguration);
	}
	
	public StrategyRunner configureOutput(String outputXml, Source xsltSource, String fileExtension) {
		return configureOutput(null, outputXml, xsltSource, fileExtension);
	}

	public StrategyRunner configureOutput(File outputDirectory, String outputXml, Source xsltSource, String fileExtension) {
		this.outputConfiguration = new OutputConfiguration(outputDirectory, outputXml, xsltSource, fileExtension);
		return this;
	}

	public StrategyRunner configureOutput(String fileExtension) {
		return configureOutput(null, (Source)null, fileExtension);
	}
	
	public StrategyRunner configureOutput(String outputXml, String xsltSource, String fileExtension) {
		return configureOutput(null, outputXml, xsltSource, fileExtension);
	}
	
	public StrategyRunner configureOutput(File outputDirectory, String outputXml, String xsltSource, String fileExtension) {
		File xsltFile = new File(xsltSource);
		if(xsltFile.canRead())
			return configureOutput(outputDirectory, outputXml, new StreamSource(xsltFile), fileExtension);
		InputStream resource = getClass().getResourceAsStream(xsltSource);
		if(resource != null)
			return configureOutput(outputDirectory, outputXml, new StreamSource(resource), fileExtension);
		throw new IllegalArgumentException("source " + xsltSource + " not available");
	}

	public StrategyRunner configureOutput(File outputDirectory, String fileExtension) {
		return configureOutput(outputDirectory, null, (Source)null, fileExtension);
	}
}

