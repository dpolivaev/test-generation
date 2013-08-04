package scriptproducer;

import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.TransformerHandler;

import org.junit.Assert;
import org.junit.Test;

import ruleengine.Assignments;
import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;
import ruleengine.TestUtils;

public class XmlScriptProducerTest {

    @Test
    public void createsTestCaseElement() throws Exception{
        DOMResult dom = new DOMResult();
        TransformerHandler handler = new HandlerFactory().newHandler(dom);
        XmlWriter xmlWriter = new XmlProducerUsingTransformerHandler(handler);
        ScriptProducer producer = new XmlScriptProducer(xmlWriter);
        PropertyContainer propertyContainer = new Assignments();
        producer.makeScriptFor(propertyContainer);
        
        handler.endDocument();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<TestCase/>")));
    }

    @Test
    public void createsTestCaseElementWithContent() throws Exception{
        DOMResult dom = new DOMResult();
        TransformerHandler handler = new HandlerFactory().newHandler(dom);
        XmlWriter xmlWriter = new XmlProducerUsingTransformerHandler(handler);
        XmlScriptProducer producer = new XmlScriptProducer(xmlWriter);
        Assignments propertyContainer = new Assignments();
        propertyContainer.add(TestUtils.assignmentMock("testcase", "content"));
        
        producer.makeScriptFor(propertyContainer);
        
        handler.endDocument();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<TestCase content='content'/>")));
    }
}

