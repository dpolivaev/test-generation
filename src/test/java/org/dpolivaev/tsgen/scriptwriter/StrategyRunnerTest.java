package org.dpolivaev.tsgen.scriptwriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.io.File;

import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.strategies.StrategyHelper;
import org.junit.Test;

public class StrategyRunnerTest {
	private void checkOutputFilesAreCreated(final File expectedOutputFile,
			final File expectedReportFile) {
		assertThat(expectedOutputFile.canRead(), equalTo(true));
		assertThat(expectedReportFile.canRead(), equalTo(true));
	}

	private void checkOutputStreamsAreClosed(final File expectedOutputFile,
			final File expectedReportFile) {
		expectedOutputFile.delete();
		expectedReportFile.delete();
		assertThat(expectedOutputFile.canRead(), equalTo(false));
		assertThat(expectedReportFile.canRead(), equalTo(false));
	}
	
	@Test
	public void outputsXml() throws Exception {
		Strategy strategy = new Strategy();
		final File expectedOutputFile = new File("testoutput", "script.xml");
		final File expectedReportFile = new File("report.xml");
		expectedOutputFile.delete();
		expectedReportFile.delete();
		final StrategyRunner strategyRunner = new StrategyRunner();
		strategyRunner.getOutputConfiguration().setFileDirectory("testoutput").setFileExtension("xml");
		
		strategyRunner.run(strategy.with(StrategyHelper.id("testcase")));
		
		checkOutputFilesAreCreated(expectedOutputFile, expectedReportFile);
		checkOutputStreamsAreClosed(expectedOutputFile, expectedReportFile);
	}
}
