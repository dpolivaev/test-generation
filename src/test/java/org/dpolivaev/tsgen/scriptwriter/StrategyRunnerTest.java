package org.dpolivaev.tsgen.scriptwriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.io.File;

import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.strategies.StrategyHelper;
import org.junit.Test;

public class StrategyRunnerTest {
	@Test
	public void outputsXml() throws Exception {
		Strategy strategy = new Strategy();
		final File expectedOutputFile = new File("testoutput", "script.xml");
		expectedOutputFile.delete();
		new StrategyRunner().addOutput(new File("testoutput"), "xml").run(strategy.with(StrategyHelper.id("testcase")));
		assertThat(expectedOutputFile.canRead(), equalTo(true));
	}
}
