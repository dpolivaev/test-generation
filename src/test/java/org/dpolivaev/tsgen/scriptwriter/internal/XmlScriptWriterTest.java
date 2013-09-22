package org.dpolivaev.tsgen.scriptwriter.internal;

import static org.dpolivaev.tsgen.testutils.TestUtils.assignmentMock;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import java.util.Collections;

import javax.xml.transform.dom.DOMResult;

import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.ruleengine.Assignments;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;
import org.dpolivaev.tsgen.scriptwriter.internal.MultipleScriptsWriter;
import org.dpolivaev.tsgen.scriptwriter.internal.ResultFactory;
import org.dpolivaev.tsgen.scriptwriter.internal.SingleScriptWriter;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class XmlScriptWriterTest {
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
        final ScriptConfiguration scriptConfiguration = OutputConfiguration.OUTPUT_XML.forScript("scriptName");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
		SingleScriptWriter producer = new SingleScriptWriter(propertyContainer, scriptConfiguration, resultFactory);
        producer.handlePropertyCombination(propertyContainer, Collections.<Goal>emptyList());
        producer.endScript();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='scriptName'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }

    @Test
    public void twoTestcases() {
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
        final ScriptConfiguration scriptConfiguration = OutputConfiguration.OUTPUT_XML.forScript("scriptName");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 1");
		SingleScriptWriter producer = new SingleScriptWriter(propertyContainer, 
        		scriptConfiguration, 
        		resultFactory);
        producer.handlePropertyCombination(propertyContainer, Collections.<Goal>emptyList());
        
        propertyContainer.startNewCombination();
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 2");
        producer.handlePropertyCombination(propertyContainer, Collections.<Goal>emptyList());
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
		Mockito.when(resultFactory.newResult(Mockito.<ScriptConfiguration>any())).thenReturn(dom);
        MultipleScriptsWriter producer = new MultipleScriptsWriter(resultFactory);
        producer.handlePropertyCombination(propertyContainer, Collections.<Goal>emptyList());
        producer.endScripts();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='scriptName1'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }


    @Test
    public void twoScriptsWithOneTestcase() {
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom1 = new DOMResult();
        final DOMResult dom2 = new DOMResult();
        Mockito.when(resultFactory.newResult(Mockito.<ScriptConfiguration>any())).thenReturn(dom1).thenReturn(dom2);
        MultipleScriptsWriter producer = new MultipleScriptsWriter(resultFactory);
        givenProperty("script", "scriptName1");
        givenProperty("testcase", "testcase 1");
        producer.handlePropertyCombination(propertyContainer, Collections.<Goal>emptyList());
        propertyContainer.startNewCombination();
        givenProperty("script", "scriptName2");
        givenProperty("testcase", "testcase 1");
        producer.handlePropertyCombination(propertyContainer, Collections.<Goal>emptyList());
        producer.endScripts();
        
        Assert.assertThat(the(dom2.getNode()), isEquivalentTo(the("<Script id='scriptName2'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }
    @Test
    public void implicitScriptName() {
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        final DOMResult dom = new DOMResult();
        final ScriptConfiguration scriptConfiguration = OutputConfiguration.OUTPUT_XML.forScript("scriptName1");
		Mockito.when(resultFactory.newResult(scriptConfiguration)).thenReturn(dom);
        givenProperty("testcase", "testcase 1");
		SingleScriptWriter producer = new SingleScriptWriter(propertyContainer, scriptConfiguration,resultFactory);
        producer.handlePropertyCombination(propertyContainer, Collections.<Goal>emptyList());
        producer.endScript();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='script'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }

}
