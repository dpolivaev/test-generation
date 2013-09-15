package org.dpolivaev.tsgen.scriptproducer.internal;

import static org.dpolivaev.tsgen.testutils.TestUtils.assignmentMock;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import javax.xml.transform.dom.DOMResult;

import org.dpolivaev.tsgen.ruleengine.internal.Assignments;
import org.dpolivaev.tsgen.scriptproducer.internal.DomResultFactory;
import org.dpolivaev.tsgen.scriptproducer.internal.MultipleScriptsProducer;
import org.dpolivaev.tsgen.scriptproducer.internal.SingleScriptProducer;
import org.junit.Assert;
import org.junit.Test;

public class XmlScriptProducerTest {
    private Assignments propertyContainer = new Assignments();
    private void givenProperty(String name, Object value) {
        propertyContainer.add(assignmentMock(name, value));
    }

    @Test
    public void oneTestcase() {
        DOMResult dom = new DOMResult();
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 1");
        SingleScriptProducer producer = new SingleScriptProducer(propertyContainer, dom);
        producer.makeScriptFor(propertyContainer);
        producer.endScript();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='scriptName'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }

    @Test
    public void twoTestcases() {
        DOMResult dom = new DOMResult();
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 1");
        SingleScriptProducer producer = new SingleScriptProducer(propertyContainer, dom);
        producer.makeScriptFor(propertyContainer);
        
        propertyContainer.startNewCombination();
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 2");
        producer.makeScriptFor(propertyContainer);
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
        MultipleScriptsProducer producer = new MultipleScriptsProducer(new DomResultFactory(), null);
        producer.makeScriptFor(propertyContainer);
        producer.endScripts();
        DOMResult dom = (DOMResult) producer.getResult("scriptName1");
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='scriptName1'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }


    @Test
    public void twoScriptsWithOneTestcase() {
        MultipleScriptsProducer producer = new MultipleScriptsProducer(new DomResultFactory(), null);
        givenProperty("script", "scriptName1");
        givenProperty("testcase", "testcase 1");
        producer.makeScriptFor(propertyContainer);
        propertyContainer.startNewCombination();
        givenProperty("script", "scriptName2");
        givenProperty("testcase", "testcase 1");
        producer.makeScriptFor(propertyContainer);
        producer.endScripts();
        
        DOMResult dom = (DOMResult) producer.getResult("scriptName2");
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='scriptName2'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }
    @Test
    public void implicitScriptName() {
        DOMResult dom = new DOMResult();
        givenProperty("testcase", "testcase 1");
        SingleScriptProducer producer = new SingleScriptProducer(propertyContainer, dom);
        producer.makeScriptFor(propertyContainer);
        producer.endScript();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script id='script'>" +
                "<TestCase id='testcase 1'/>" +
        "</Script>")));
    }

}
