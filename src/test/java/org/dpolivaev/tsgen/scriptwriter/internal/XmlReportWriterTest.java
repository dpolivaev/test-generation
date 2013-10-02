package org.dpolivaev.tsgen.scriptwriter.internal;

import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.transform.dom.DOMResult;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.coverage.GoalFunction;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class XmlReportWriterTest {
    @Test
    public void emptyReport() {
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
        final ScriptConfiguration scriptConfiguration = OutputConfiguration.OUTPUT_XML.forScript("reportName");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
		ReportWriter producer = new ReportWriter(resultFactory);
        producer.createReport(GoalChecker.NO_GOALS, scriptConfiguration);
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Report>" +
        "</Report>")));
    }
    
    @Test
    public void openGoalReport() {
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
        final ScriptConfiguration scriptConfiguration = OutputConfiguration.OUTPUT_XML.forScript("reportName");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
		final GoalChecker goals = new GoalChecker();
		goals.addGoal(new Goal("goal", new GoalFunction() {
			
			@Override
			public Collection<CoverageEntry> check(PropertyContainer propertyContainer) {
					return Arrays.asList(new CoverageEntry("item", "value"));
			}
		}));
		goals.handlePropertyCombination(null);
		ReportWriter producer = new ReportWriter(resultFactory);
        producer.createReport(goals, scriptConfiguration);
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the(
        	"<Report>"
        	+ "<Goal name='goal' required='0' achieved='1'>"
        		+ "<Item name='item' required='0' reached='1'>value</Item>"
        	+ "</Goal>" 
        	+ "</Report>")));
    }
}
