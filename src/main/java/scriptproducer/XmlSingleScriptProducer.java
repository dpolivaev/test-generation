package scriptproducer;

import javax.xml.transform.Result;
import javax.xml.transform.sax.TransformerHandler;

import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;

public class XmlSingleScriptProducer implements ScriptProducer {

    private XmlWriter xmlWriter;
    private XmlTestCaseProducer testCaseProducer;
    private final Result result;

    @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        testCaseProducer.makeScriptFor(propertyContainer);
    }

    public XmlSingleScriptProducer(PropertyContainer propertyContainer, Result result) {
        this.result = result;
        TransformerHandler handler = new HandlerFactory().newHandler(result);
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

    public Result result() {
        return result;
    }

}
