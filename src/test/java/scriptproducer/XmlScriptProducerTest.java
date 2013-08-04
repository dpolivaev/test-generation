package scriptproducer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.TransformerHandler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xmlmatchers.XmlMatchers;

import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;
import ruleengine.SpecialValues;

public class XmlScriptProducerTest {

    @Test
    public void createsTestCaseElement() throws Exception{
        DOMResult dom = new DOMResult();
        TransformerHandler handler = new HandlerFactory().newHandler(dom);
        XmlWriter xmlWriter = new XmlProducerUsingTransformerHandler(handler);
        ScriptProducer producer = new XmlScriptProducer(xmlWriter);
        PropertyContainer propertyContainer = mock(PropertyContainer.class);
        when(propertyContainer.get(Mockito.anyString())).thenReturn(SpecialValues.UNDEFINED);
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
        PropertyContainer propertyContainer = mock(PropertyContainer.class);
        when(propertyContainer.get("testcase")).thenReturn("content");
        
        producer.makeScriptFor(propertyContainer);
        
        handler.endDocument();
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<TestCase content='content'/>")));
    }
}

