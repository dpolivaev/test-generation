package org.dpolivaev.tsgen.scriptwriter.internal;

import static org.dpolivaev.tsgen.testutils.TestUtils.assignmentMock;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import javax.xml.transform.dom.DOMResult;

import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.ruleengine.Assignments;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;
import org.dpolivaev.tsgen.scriptwriter.internal.ResultFactory;
import org.dpolivaev.tsgen.scriptwriter.internal.SingleScriptWriter;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class XmlScriptWriterTest {
    private Assignments propertyContainer = new Assignments();
    private void givenProperty(String name, Object value) {
        propertyContainer.add(assignmentMock(name, value, ""));
    }
    
	private DOMResult createScript() {
		ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
        final ScriptConfiguration scriptConfiguration = OutputConfiguration.OUTPUT_NOTHING.forScript("scriptName");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
		SingleScriptWriter producer = new SingleScriptWriter(propertyContainer, scriptConfiguration, resultFactory, GoalChecker.NO_GOALS);
        producer.handlePropertyCombination(propertyContainer);
        producer.endScript();
		return dom;
	}

    @Test
    public void oneTestcase() {
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 1");
        final DOMResult dom = createScript();
        checkOutput(dom, "<Script id='scriptName' driver='scriptNameDriver'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>");
    }

    @Test
    public void explicitDriverTestcase() {
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 1");
        givenProperty("driver", "driver1");
        final DOMResult dom = createScript();
        checkOutput(dom, "<Script id='scriptName' driver='driver1'>" +
                "<TestCase id='testcase 1'/>" +
        		"</Script>");
    }

    @Test
    public void twoTestcases() {
    	givenProperty("script", "scriptName");
    	givenProperty("testcase", "testcase 1");
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
        final ScriptConfiguration scriptConfiguration = OutputConfiguration.OUTPUT_NOTHING.forScript("scriptName");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
		SingleScriptWriter producer = new SingleScriptWriter(propertyContainer, 
        		scriptConfiguration, 
        		resultFactory, GoalChecker.NO_GOALS);
        producer.handlePropertyCombination(propertyContainer);
        
        propertyContainer.startNewCombination();
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 2");
        producer.handlePropertyCombination(propertyContainer);
        producer.endScript();
        
        checkOutput(dom, "<Script id='scriptName'  driver='scriptNameDriver'>" +
		            "<TestCase id='testcase 1'/>" +
		            "<TestCase id='testcase 2'/>" +
		        "</Script>");
    }

	private void checkOutput(final DOMResult dom, String expected) {
		Assert.assertThat(the(dom.getNode()), isEquivalentTo(the(expected)));
	}

    @Test
    public void implicitScriptName() {
    	givenProperty("testcase", "testcase 1");
    	final DOMResult dom = createScript();
    	checkOutput(dom, "<Script id='script' driver='scriptDriver'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>");
    }

    @Test
    public void createsScriptWithAllPartsAndContent() throws Exception{
        givenProperty("script", "scriptName");
        givenProperty("scriptpre", "global precondition");
        givenProperty("scriptpost", "global postprocessing");
        givenProperty("testcase", "testcase 1");
        final DOMResult dom = createScript();
        checkOutput(dom, "<Script id='scriptName' driver='scriptNameDriver'>" +
                "<ScriptPrecondition id='global precondition'/>" +
                "<ScriptPostprocessing id='global postprocessing'/>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>");
    	
    }
}
