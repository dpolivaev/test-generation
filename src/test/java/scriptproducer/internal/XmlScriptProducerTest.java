package scriptproducer.internal;

import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;
import static testutils.TestUtils.assignmentMock;

import javax.xml.transform.dom.DOMResult;

import org.junit.Assert;
import org.junit.Test;

import ruleengine.impl.Assignments;
import scriptproducer.internal.DomResultFactory;
import scriptproducer.internal.MultipleScriptsProducer;
import scriptproducer.internal.SingleScriptProducer;

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
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script self='scriptName'>" +
                "<TestCase self='testcase 1'/>" +
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
        
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script self='scriptName'>" +
            "<TestCase self='testcase 1'/>" +
            "<TestCase self='testcase 2'/>" +
        "</Script>")));
    }

    @Test
    public void scriptWithOneTestcase() {
        givenProperty("script", "scriptName1");
        givenProperty("testcase", "testcase 1");
        MultipleScriptsProducer producer = new MultipleScriptsProducer(new DomResultFactory());
        producer.makeScriptFor(propertyContainer);
        producer.endScripts();
        DOMResult dom = (DOMResult) producer.getResult("scriptName1");
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script self='scriptName1'>" +
                "<TestCase self='testcase 1'/>" +
        "</Script>")));
    }


    @Test
    public void twoScriptsWithOneTestcase() {
        MultipleScriptsProducer producer = new MultipleScriptsProducer(new DomResultFactory());
        givenProperty("script", "scriptName1");
        givenProperty("testcase", "testcase 1");
        producer.makeScriptFor(propertyContainer);
        propertyContainer.startNewCombination();
        givenProperty("script", "scriptName2");
        givenProperty("testcase", "testcase 1");
        producer.makeScriptFor(propertyContainer);
        producer.endScripts();
        
        DOMResult dom = (DOMResult) producer.getResult("scriptName2");
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script self='scriptName2'>" +
                "<TestCase self='testcase 1'/>" +
        "</Script>")));
    }
    @Test
    public void implicitScriptName() {
        DOMResult dom = new DOMResult();
        givenProperty("testcase", "testcase 1");
        SingleScriptProducer producer = new SingleScriptProducer(propertyContainer, dom);
        producer.makeScriptFor(propertyContainer);
        producer.endScript();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script self='script'>" +
                "<TestCase self='testcase 1'/>" +
        "</Script>")));
    }

}
