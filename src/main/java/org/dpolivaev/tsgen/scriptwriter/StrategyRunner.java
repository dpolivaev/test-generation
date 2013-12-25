package org.dpolivaev.tsgen.scriptwriter;

import java.io.IOException;
import java.io.OutputStreamWriter;

import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.coverage.internal.RequirementCoverage;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.internal.MultipleScriptsWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.ReportWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.ScriptLogger;
import org.dpolivaev.tsgen.scriptwriter.internal.StreamResultFactory;
import org.dpolivaev.tsgen.utils.internal.Utils;

public class StrategyRunner {
	private OutputConfiguration outputConfiguration;
	private OutputConfiguration reportConfiguration;

	public StrategyRunner() {
		super();
		this.outputConfiguration = new OutputConfiguration();
		this.reportConfiguration = new OutputConfiguration("report.xml");
	}

	public void run(Strategy strategy){
		RuleEngine ruleEngine = new RuleEngine();
		OutputStreamWriter writer = new OutputStreamWriter(System.out);
		ScriptLogger logger = new ScriptLogger(writer);
		ruleEngine.addHandler(logger);
		ruleEngine.addErrorHandler(logger);
		GoalChecker goalChecker = new GoalChecker();
		goalChecker.addGoal(new Goal("requirements", new RequirementCoverage()));
		ruleEngine.addHandler(goalChecker);
		StreamResultFactory resultFactory = new StreamResultFactory();
		MultipleScriptsWriter scriptProducer = new MultipleScriptsWriter(resultFactory, GoalChecker.NO_GOALS);
		scriptProducer.setOutputConfiguration(outputConfiguration);
		ruleEngine.addHandler(scriptProducer);
		ruleEngine.run(strategy);
		try {
			scriptProducer.endScripts();
			writer.close();
		} catch (IOException e) {
			throw Utils.runtimeException(e);
		}
		ReportWriter reportWriter = new ReportWriter(resultFactory);
		reportWriter.createReport(goalChecker, new ScriptConfiguration(reportConfiguration, null));
	}
	
	public OutputConfiguration getOutputConfiguration() {
		return outputConfiguration;
	}

	public OutputConfiguration getReportConfiguration() {
		return reportConfiguration;
	}
}

