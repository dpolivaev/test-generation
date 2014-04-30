package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import org.dpolivaev.testgeneration.engine.coverage.GoalChecker;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;

public class ReportStarter implements PropertyHandler {
	private final GoalChecker goalChecker;
	private final StreamResultFactory resultFactory;
	private final ScriptConfiguration scriptConfiguration;

	public ReportStarter(GoalChecker goalChecker, StreamResultFactory resultFactory, ScriptConfiguration scriptConfiguration) {
		this.goalChecker = goalChecker;
		this.resultFactory = resultFactory;
		this.scriptConfiguration = scriptConfiguration;
	}

	@Override
	public void handlePropertyCombination(PropertyContainer propertyContainer) {
	}

	@Override
	public void generationStarted(PropertyContainer propertyContainer) {
	}

	@Override
	public void generationFinished() {
		ReportWriter reportWriter = new ReportWriter(resultFactory, scriptConfiguration);
		reportWriter.createReport(goalChecker);
	}
}