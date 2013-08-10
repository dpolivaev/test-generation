package scriptproducer;

import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;
import static ruleengine.TestUtils.assignmentMock;

import javax.xml.transform.dom.DOMResult;

import org.junit.Assert;
import org.junit.Test;

import ruleengine.Assignments;

public class XmlScriptProducerTest {
    private Assignments propertyContainer = new Assignments();
    private void givenProperty(String name, Object value) {
        propertyContainer.add(assignmentMock(name, value));
    }

    @Test
    public void scriptWithOneTestcase() {
        DOMResult dom = new DOMResult();
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 1");
        XmlSingleScriptProducer producer = new XmlSingleScriptProducer(propertyContainer, dom);
        producer.makeScriptFor(propertyContainer);
        producer.endScript();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script self='scriptName'>" +
                "<TestCase self='testcase 1'/>" +
        "</Script>")));
    }

    @Test
    public void scriptWithTwoTestcases() {
        DOMResult dom = new DOMResult();
        givenProperty("script", "scriptName");
        givenProperty("testcase", "testcase 1");
        XmlSingleScriptProducer producer = new XmlSingleScriptProducer(propertyContainer, dom);
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

}
