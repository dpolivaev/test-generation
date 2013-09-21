package org.dpolivaev.tsgen.scriptwriter.internal;

import static org.dpolivaev.tsgen.testutils.TestUtils.assignmentMock;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import javax.xml.transform.dom.DOMResult;

import org.dpolivaev.tsgen.ruleengine.internal.Assignments;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.internal.MultipleScriptsWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.ResultFactory;
import org.dpolivaev.tsgen.scriptwriter.internal.SingleScriptWriter;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class XmlScriptProducerTest {
    private Assignments propertyContainer = new Assignments();
    private void givenProperty(String name, Object value) {
        propertyContainer.add(assignmentMock(name, value));
    }

    @Test
    public void oneTestcase() {
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 1");
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
		Mockito.when(resultFactory.newResult("scriptName", "xml")).thenReturn(dom);
        SingleScriptWriter producer = new SingleScriptWriter(propertyContainer, OutputConfiguration.OUTPUT_XML.forScript("scriptName"), resultFactory);
        producer.handlePropertyCombination(propertyContainer, null);
        producer.endScript();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='scriptName'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }

    @Test
    public void twoTestcases() {
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
		Mockito.when(resultFactory.newResult("scriptName", "xml")).thenReturn(dom);
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 1");
        SingleScriptWriter producer = new SingleScriptWriter(propertyContainer, 
        		OutputConfiguration.OUTPUT_XML.forScript("scriptName"), 
        		resultFactory);
        producer.handlePropertyCombination(propertyContainer, null);
        
        propertyContainer.startNewCombination();
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 2");
        producer.handlePropertyCombination(propertyContainer, null);
        producer.endScript();
        
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='scriptName'>" +
            "<TestCase id='testcase 1'/>" +
            "<TestCase id='testcase 2'/>" +
        "</Script>")));
    }

    @Test
    public void scriptWithOneTestcase() {
        givenProperty("script", "scriptName1");
        givenProperty("testcase", "testcase 1");
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
		Mockito.when(resultFactory.newResult("scriptName1", "xml")).thenReturn(dom);
        MultipleScriptsWriter producer = new MultipleScriptsWriter(resultFactory);
        producer.handlePropertyCombination(propertyContainer, null);
        producer.endScripts();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='scriptName1'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }


    @Test
    public void twoScriptsWithOneTestcase() {
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom1 = new DOMResult();
        Mockito.when(resultFactory.newResult("scriptName1", "xml")).thenReturn(dom1);
        final DOMResult dom2 = new DOMResult();
		Mockito.when(resultFactory.newResult("scriptName2", "xml")).thenReturn(dom2);
        MultipleScriptsWriter producer = new MultipleScriptsWriter(resultFactory);
        givenProperty("script", "scriptName1");
        givenProperty("testcase", "testcase 1");
        producer.handlePropertyCombination(propertyContainer, null);
        propertyContainer.startNewCombination();
        givenProperty("script", "scriptName2");
        givenProperty("testcase", "testcase 1");
        producer.handlePropertyCombination(propertyContainer, null);
        producer.endScripts();
        
        Assert.assertThat(the(dom2.getNode()), isEquivalentTo(the("<Script id='scriptName2'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }
    @Test
    public void implicitScriptName() {
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
		Mockito.when(resultFactory.newResult("scriptName1", "xml")).thenReturn(dom);
        givenProperty("testcase", "testcase 1");
        SingleScriptWriter producer = new SingleScriptWriter(propertyContainer, 
        		OutputConfiguration.OUTPUT_XML.forScript("scriptName1"),resultFactory);
        producer.handlePropertyCombination(propertyContainer, null);
        producer.endScript();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='script'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }

}
