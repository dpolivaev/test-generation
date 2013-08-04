package scriptproducer;

import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;
import static ruleengine.TestUtils.assignmentMock;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.TransformerHandler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import ruleengine.Assignments;
import ruleengine.ScriptProducer;
import ruleengine.SpecialValues;

public class XmlScriptProducerTest {

    private DOMResult dom;
    private TransformerHandler handler;
    private ScriptProducer producer;
    private Assignments propertyContainer;

    @Before
    public void setup() throws TransformerFactoryConfigurationError, TransformerConfigurationException, SAXException {
        dom = new DOMResult();
        handler = new HandlerFactory().newHandler(dom);
        XmlWriter xmlWriter = new XmlProducerUsingTransformerHandler(handler);
        producer = new XmlScriptProducer(xmlWriter);
        propertyContainer = new Assignments();
    }

    private void givenProperty(String name, Object value) {
        propertyContainer.add(assignmentMock(name, value));
    }

    private void createScript() throws SAXException {
        producer.makeScriptFor(propertyContainer);
        handler.endDocument();
    }

    private void checkOutput(String xml) {
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the(xml)));
    }

    @Test
    public void createsTestCaseElement() throws Exception{
        createScript();
        checkOutput("<TestCase/>");
    }

    @Test
    public void createsTestCaseElementWithContent() throws Exception{
        givenProperty("testcase", "content");
        createScript();
        checkOutput("<TestCase content='content'/>");
    }

     @Test
    public void createsTestCaseElementWithArbitraryAttribute() throws Exception{
        propertyContainer.add(assignmentMock("testcase.attribute", "attribute"));
        createScript();
        checkOutput("<TestCase attribute='attribute'/>");
    }
    
    @Test
    public void ignoresUndefinedProperties() throws Exception{
        givenProperty("testcase.attribute", SpecialValues.UNDEFINED);
        createScript();
        checkOutput("<TestCase/>");
    }
}

