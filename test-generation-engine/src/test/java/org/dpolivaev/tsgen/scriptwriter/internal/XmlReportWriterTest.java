package org.dpolivaev.tsgen.scriptwriter.internal;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.transform.dom.DOMResult;

import org.dpolivaev.tsgen.coverage.CheckList;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.coverage.GoalFunction;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;
import org.dpolivaev.tsgen.testutils.TestUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class XmlReportWriterTest {
    @Test
    public void emptyReport() {
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
        final ScriptConfiguration scriptConfiguration = OutputConfiguration.OUTPUT_NOTHING.forScript("reportName");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
		ReportWriter producer = new ReportWriter(resultFactory, scriptConfiguration);
        producer.createReport(GoalChecker.NO_GOALS);
        Assert.assertThat(dom.getNode(), CoreMatchers.nullValue());
    }
    
    @Test
    public void openGoalReport() {
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
        final ScriptConfiguration scriptConfiguration = new OutputConfiguration().setFileExtension("report.xml").forScript("reportName");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
		final GoalChecker goals = new GoalChecker(null);
		CheckList checkList = new CheckList(); 
		checkList.addExpected(new CoverageEntry("item2", "value"));
		checkList.addExpected(new CoverageEntry("item1", CoverageEntry.ANY));
		goals.addGoal(new Goal("goal2", new GoalFunction() {
			
			@Override
			public Collection<CoverageEntry> check(PropertyContainer propertyContainer) {
				return Arrays.asList(
						new CoverageEntry("item2", "value"),
						new CoverageEntry("item1", "value2"),
						new CoverageEntry("item1", "value1"));
			}
		}, checkList));
		
		goals.addGoal(new Goal("goal1", new GoalFunction() {
			
			@Override
			public Collection<CoverageEntry> check(PropertyContainer propertyContainer) {
					return Arrays.asList(new CoverageEntry("item", "value"));
			}
		}));
		goals.handlePropertyCombination(null);
		ReportWriter producer = new ReportWriter(resultFactory, scriptConfiguration);
        producer.createReport(goals);
        TestUtils.assertXmlEquals("<Report>"
		    	+ "<Goal name='goal1' required='0' achieved='0' total='1'>"
				+ "<Item name='item' reached='1'>value</Item>"
			+ "</Goal>" 
			+ "<Goal name='goal2' required='2' achieved='2' total='3'>"
			+ "<Item name='item1' reached='1'>value1</Item>"
			+ "<Item name='item1' reached='1'>value2</Item>"
			+ "<Item name='item2' reached='1'>value</Item>"
		+ "</Goal>" 
		+ "</Report>", 
        	dom.getNode());
    }
}
