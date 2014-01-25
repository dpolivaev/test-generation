package org.dpolivaev.tsgen.scriptwriter;

import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashSet;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.coverage.internal.CodeCoverageResetter;
import org.dpolivaev.tsgen.coverage.internal.RequirementsCoverageGoalBuilder;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.scriptwriter.internal.MultipleScriptsWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.ReportWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.ResultFactory;
import org.dpolivaev.tsgen.scriptwriter.internal.ScriptLogger;
import org.dpolivaev.tsgen.scriptwriter.internal.StreamResultFactory;

public class WriterFactory {
	final private OutputConfiguration outputConfiguration;
	final private OutputConfiguration reportConfiguration;
	final private Collection<CoverageTracker> trackers;
	final private Collection<CoverageEntry> requiredEntries;

	public WriterFactory(OutputConfiguration outputConfiguration,
			OutputConfiguration reportConfiguration) {
		super();
		this.outputConfiguration = outputConfiguration;
		this.reportConfiguration = reportConfiguration;
		trackers = new HashSet<>();
		requiredEntries = new HashSet<>();
	}
	
	public WriterFactory addCoverageTracker(final CoverageTracker coverageTracker) {
		trackers.add(coverageTracker);
		return this;
	}

	public WriterFactory addRequiredItems(
			final Collection<CoverageEntry> requiredItems) {
		requiredEntries.addAll(requiredItems);
		return this;
	}

	public void configureEngine(RuleEngine ruleEngine) {
		StreamResultFactory resultFactory = new StreamResultFactory();
		GoalChecker goalChecker = createGoalChecker(resultFactory);
		ruleEngine.addHandler(goalChecker);
		for(CoverageTracker tracker :trackers)
			ruleEngine.addHandler(new CodeCoverageResetter(tracker));
		MultipleScriptsWriter scriptProducer = null;
		if (outputConfiguration.isFileValid()){
			scriptProducer = new MultipleScriptsWriter(resultFactory, goalChecker);
			scriptProducer.setOutputConfiguration(outputConfiguration);
			ruleEngine.addHandler(scriptProducer);
		}
		OutputStreamWriter writer = new OutputStreamWriter(System.out);
		ScriptLogger logger = new ScriptLogger(writer);
		ruleEngine.addHandler(logger);
		ruleEngine.addErrorHandler(logger);
	}

	private GoalChecker createGoalChecker(ResultFactory resultFactory) {
		ReportWriter reportWriter = new ReportWriter(resultFactory, new ScriptConfiguration(reportConfiguration, null));
		GoalChecker goalChecker = new GoalChecker(reportWriter);
		goalChecker.addGoal(createCoverageGoal());
		return goalChecker;
	}

	private Goal createCoverageGoal() {
		return new RequirementsCoverageGoalBuilder(trackers, requiredEntries).createGoal();
	}
}
