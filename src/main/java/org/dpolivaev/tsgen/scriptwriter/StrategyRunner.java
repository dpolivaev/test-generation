package org.dpolivaev.tsgen.scriptwriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashSet;

import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.coverage.code.Model;
import org.dpolivaev.tsgen.coverage.internal.RequirementsCoverageGoalBuilder;
import org.dpolivaev.tsgen.coverage.internal.CodeCoverageResetter;
import org.dpolivaev.tsgen.coverage.internal.RequirementCoverage;
import org.dpolivaev.tsgen.ruleengine.PropertyAccessor;
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
	final private Collection<Model> models;
	final private Collection<PropertyAccessor> propertyAccessors;

	public StrategyRunner() {
		super();
		this.outputConfiguration = new OutputConfiguration();
		this.reportConfiguration = new OutputConfiguration();
		models = new HashSet<>();
		propertyAccessors = new HashSet<>();
	}
	
	public StrategyRunner addModel(Model model){
		models.add(model);
		if(model instanceof PropertyAccessor)
			propertyAccessors.add((PropertyAccessor) model);
		return this;
	}

	public StrategyRunner addPropertyAccessor(PropertyAccessor propertyAccessor){
		propertyAccessors.add(propertyAccessor);
		return this;
	}

	public void run(Strategy strategy){
		RuleEngine ruleEngine = new RuleEngine();
		OutputStreamWriter writer = new OutputStreamWriter(System.out);
		ScriptLogger logger = new ScriptLogger(writer);
		ruleEngine.addHandler(logger);
		ruleEngine.addErrorHandler(logger);
		GoalChecker goalChecker = createGoalChecker();
		ruleEngine.addHandler(goalChecker);
		for(Model model:models)
			ruleEngine.addHandler(new CodeCoverageResetter(model.getCodeCoverageTracker()));
		MultipleScriptsWriter scriptProducer = null;
		StreamResultFactory resultFactory = new StreamResultFactory();
		if (outputConfiguration.isFileValid()){
			scriptProducer = new MultipleScriptsWriter(resultFactory, goalChecker);
			scriptProducer.setOutputConfiguration(outputConfiguration);
			ruleEngine.addHandler(scriptProducer);
		}
		try {
			for(PropertyAccessor propertyAccessor : propertyAccessors)
				propertyAccessor.setPropertyContainer(ruleEngine);
			ruleEngine.run(strategy);
		} 
		finally {
			for(PropertyAccessor propertyAccessor : propertyAccessors)
				propertyAccessor.setPropertyContainer(null);
		}
		try {
			if(scriptProducer != null)
				scriptProducer.endScripts();
			writer.close();
		} catch (IOException e) {
			throw Utils.runtimeException(e);
		}
		ReportWriter reportWriter = new ReportWriter(resultFactory);
		reportWriter.createReport(goalChecker, new ScriptConfiguration(reportConfiguration, null));
	}

	private GoalChecker createGoalChecker() {
		GoalChecker goalChecker = new GoalChecker();
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

