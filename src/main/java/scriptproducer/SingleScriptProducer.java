package scriptproducer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.TransformerHandler;

import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;
import ruleengine.SpecialValues;

public class SingleScriptProducer implements ScriptProducer {

    private XmlWriter xmlWriter;
    private TestCaseProducer testCaseProducer;
    private final Result result;

    @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        testCaseProducer.makeScriptFor(propertyContainer);
    }

    public SingleScriptProducer(PropertyContainer propertyContainer, Source xsltSource, Result result) {
        this.result = result;
        TransformerHandler handler = new HandlerFactory(xsltSource).newHandler(result);
        xmlWriter = new XmlWriterUsingTransformerHandler(handler);
        xmlWriter.startDocument();
        testCaseProducer = new TestCaseProducer(xmlWriter);
        xmlWriter.beginElement("Script");
        Object scriptValue = propertyContainer.get("script");
        if(scriptValue.equals(SpecialValues.UNDEFINED))
        	xmlWriter.setAttribute("self", "script");
        testCaseProducer.addAttributes(propertyContainer, "script");
    }
    
    public SingleScriptProducer(PropertyContainer propertyContainer, Result result) {
        this(propertyContainer, null, result);
    }

    public void endScript() {
        xmlWriter.endElement("Script");
        xmlWriter.endDocument();
    }

    public Result result() {
        return result;
    }

}
