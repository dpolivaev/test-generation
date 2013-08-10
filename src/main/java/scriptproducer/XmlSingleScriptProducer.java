package scriptproducer;

import javax.xml.transform.Result;
import javax.xml.transform.sax.TransformerHandler;

import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;

public class XmlSingleScriptProducer implements ScriptProducer {

    private XmlWriter xmlWriter;
    private XmlTestCaseProducer testCaseProducer;

    @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        testCaseProducer.makeScriptFor(propertyContainer);
    }

    public XmlSingleScriptProducer(PropertyContainer propertyContainer, Result dom) {
        TransformerHandler handler = new HandlerFactory().newHandler(dom);
        xmlWriter = new XmlProducerUsingTransformerHandler(handler);
        xmlWriter.startDocument();
        testCaseProducer = new XmlTestCaseProducer(xmlWriter);
        xmlWriter.beginElement("Script");
        testCaseProducer.addAttributes(propertyContainer, "script");
    }

    public void endScript() {
        xmlWriter.endElement("Script");
        xmlWriter.endDocument();
    }

}
