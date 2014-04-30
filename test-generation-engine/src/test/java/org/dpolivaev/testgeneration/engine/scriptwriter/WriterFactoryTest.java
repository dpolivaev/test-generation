package org.dpolivaev.testgeneration.engine.scriptwriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.io.File;

import org.dpolivaev.testgeneration.engine.ruleengine.RuleEngine;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration;
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory;
import org.junit.Test;

public class WriterFactoryTest {
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
		final OutputConfiguration outputConfiguration = new OutputConfiguration();
		outputConfiguration.setFileDirectory("testoutput").setFileExtension("xml");
		final OutputConfiguration reportConfiguration = new OutputConfiguration();
		reportConfiguration.setFileExtension("report.xml");
		final WriterFactory writerFactory = new WriterFactory(outputConfiguration, reportConfiguration);
		
		RuleEngine ruleEngine = new RuleEngine();
		writerFactory.configureEngine(ruleEngine);
		ruleEngine.run(strategy);
		
		checkOutputFilesAreCreated(expectedOutputFile, expectedReportFile);
		checkOutputStreamsAreClosed(expectedOutputFile, expectedReportFile);
	}

	
	@Test
	public void outputsNothing() throws Exception {
		Strategy strategy = new Strategy();
		final WriterFactory writerFactory = new WriterFactory(new OutputConfiguration(), new OutputConfiguration());
		RuleEngine ruleEngine = new RuleEngine();
		writerFactory.configureEngine(ruleEngine);
		ruleEngine.run(strategy);

	}
}
