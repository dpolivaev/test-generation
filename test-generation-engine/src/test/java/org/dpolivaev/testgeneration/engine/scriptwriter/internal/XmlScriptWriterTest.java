package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import static org.dpolivaev.testgeneration.engine.testutils.TestUtils.assignmentMock;

import javax.xml.transform.dom.DOMResult;

import org.dpolivaev.testgeneration.engine.coverage.GoalChecker;
import org.dpolivaev.testgeneration.engine.ruleengine.Assignments;
import org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.ResultFactory;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.ScriptConfiguration;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.SingleScriptWriter;
import org.dpolivaev.testgeneration.engine.testutils.TestUtils;
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
        final ScriptConfiguration scriptConfiguration = new ScriptConfiguration(OutputConfiguration.OUTPUT_NOTHING, "scriptName");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
		SingleScriptWriter producer = new SingleScriptWriter(propertyContainer, scriptConfiguration, resultFactory, GoalChecker.NO_GOALS);
        producer.handlePropertyCombination(propertyContainer);
        producer.generationFinished();
		return dom;
	}

    @Test
    public void oneTestcase() {
        givenProperty("script.name", "scriptName");
        givenProperty("testcase.name", "testcase 1");
        final DOMResult dom = createScript();
        checkOutput(dom, "<Script name='scriptName'>" +
                "<TestCase name='testcase 1'/>" +
        "</Script>");
    }

    @Test
    public void explicitDriverTestcase() {
        givenProperty("script.name", "scriptName");
        givenProperty("testcase.name", "testcase 1");
        givenProperty("script.driver", "driver1");
        final DOMResult dom = createScript();
        checkOutput(dom, "<Script name='scriptName'>"
        		+ "<Parameter type='java.lang.String' name='driver'>driver1</Parameter>" +
                "<TestCase name='testcase 1'/>" +
        		"</Script>");
    }

    @Test
    public void twoTestcases() {
    	givenProperty("script.name", "scriptName");
    	givenProperty("testcase.name", "testcase 1");
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
        final ScriptConfiguration scriptConfiguration = new ScriptConfiguration(OutputConfiguration.OUTPUT_NOTHING, "scriptName");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
		SingleScriptWriter producer = new SingleScriptWriter(propertyContainer, 
        		scriptConfiguration, 
        		resultFactory, GoalChecker.NO_GOALS);
        producer.handlePropertyCombination(propertyContainer);
        
        propertyContainer.startNewCombination();
        givenProperty("script.name", "scriptName");
        givenProperty("testcase.name", "testcase 2");
        producer.handlePropertyCombination(propertyContainer);
        producer.generationFinished();
        
        checkOutput(dom, "<Script name='scriptName'>" +
		            "<TestCase name='testcase 1'/>" +
		            "<TestCase name='testcase 2'/>" +
		        "</Script>");
    }

	private void checkOutput(final DOMResult dom, String expected) {
		TestUtils.assertEqualsXml(expected, dom.getNode());
	}

    @Test
    public void noScriptName() {
    	givenProperty("testcase.name", "testcase 1");
    	final DOMResult dom = createScript();
    	checkOutput(dom, "<Script>" +
                "<TestCase name='testcase 1'/>" +
        "</Script>");
    }

    @Test
    public void createsScriptWithAllPartsAndContent() throws Exception{
        givenProperty("script.name", "scriptName");
        givenProperty("script.precondition#2", "global precondition");
        givenProperty("script.postprocessing", "global postprocessing");
        givenProperty("testcase.name", "testcase 1");
        final DOMResult dom = createScript();
        checkOutput(dom, "<Script name='scriptName'>" +
                "<ScriptPrecondition step='global precondition'/>" +
                "<ScriptPostprocessing step='global postprocessing'/>" +
                "<TestCase name='testcase 1'/>" +
        "</Script>");
    	
    }
}
