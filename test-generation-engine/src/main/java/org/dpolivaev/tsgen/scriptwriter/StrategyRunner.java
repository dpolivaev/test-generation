package org.dpolivaev.tsgen.scriptwriter;

import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashSet;

import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.coverage.code.Model;
import org.dpolivaev.tsgen.coverage.internal.RequirementsCoverageGoalBuilder;
import org.dpolivaev.tsgen.coverage.internal.CodeCoverageResetter;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.internal.MultipleScriptsWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.ReportWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.ResultFactory;
import org.dpolivaev.tsgen.scriptwriter.internal.ScriptLogger;
import org.dpolivaev.tsgen.scriptwriter.internal.StreamResultFactory;

public class StrategyRunner {
	private OutputConfiguration outputConfiguration;
	private OutputConfiguration reportConfiguration;
	final private Collection<Model> models;
	final private Collection<PropertyHandler> propertyAccessors;

	public StrategyRunner() {
		super();
		this.outputConfiguration = new OutputConfiguration();
		this.reportConfiguration = new OutputConfiguration();
		models = new HashSet<>();
		propertyAccessors = new HashSet<>();
	}
	
	public StrategyRunner addModel(Model model){
		models.add(model);
		return this;
	}

	public StrategyRunner addPropertyAccessor(PropertyHandler propertyAccessor){
		propertyAccessors.add(propertyAccessor);
		return this;
	}

	public void run(Strategy strategy){
		RuleEngine ruleEngine = new RuleEngine();
		OutputStreamWriter writer = new OutputStreamWriter(System.out);
		ScriptLogger logger = new ScriptLogger(writer);
		ruleEngine.addHandler(logger);
		ruleEngine.addErrorHandler(logger);
		StreamResultFactory resultFactory = new StreamResultFactory();
		GoalChecker goalChecker = createGoalChecker(resultFactory);
		ruleEngine.addHandler(goalChecker);
		for(Model model:models)
			ruleEngine.addHandler(new CodeCoverageResetter(model.getCodeCoverageTracker()));
		MultipleScriptsWriter scriptProducer = null;
		if (outputConfiguration.isFileValid()){
			scriptProducer = new MultipleScriptsWriter(resultFactory, goalChecker);
			scriptProducer.setOutputConfiguration(outputConfiguration);
			ruleEngine.addHandler(scriptProducer);
		}
		for(PropertyHandler propertyAccessor : propertyAccessors)
			ruleEngine.addHandler(propertyAccessor);
		ruleEngine.run(strategy);
	}

	private GoalChecker createGoalChecker(ResultFactory resultFactory) {
		ReportWriter reportWriter = new ReportWriter(resultFactory, new ScriptConfiguration(reportConfiguration, null));
		GoalChecker goalChecker = new GoalChecker(reportWriter);
		goalChecker.addGoal(createCoverageGoal());
		return goalChecker;
	}

	private Goal createCoverageGoal() {
		return new RequirementsCoverageGoalBuilder(models).createGoal();
	}

	public OutputConfiguration getOutputConfiguration() {
		return outputConfiguration;
	}

	public OutputConfiguration getReportConfiguration() {
		return reportConfiguration;
	}
}

