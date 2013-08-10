package scriptproducer;

import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.TransformerHandler;

import ruleengine.Assignments;
import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;

public class XmlScriptProducer implements ScriptProducer {

    @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        // TODO Auto-generated method stub
        
    }

    public DOMResult getResult(String string) {
        DOMResult dom = new DOMResult();
        TransformerHandler handler = new HandlerFactory().newHandler(dom);
        XmlWriter xmlWriter = new XmlProducerUsingTransformerHandler(handler);
        xmlWriter.startDocument();
        xmlWriter.beginElement("Script");
        xmlWriter.endElement("Script");
        xmlWriter.endDocument();
        return dom;
    }

}
