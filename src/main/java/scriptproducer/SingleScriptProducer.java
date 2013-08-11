package scriptproducer;

import static utils.Utils.runtimeException;

import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;

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
        testCaseProducer.addAttributes(propertyContainer, "script");
    }
    
    public SingleScriptProducer(PropertyContainer propertyContainer, Result result) {
        this(propertyContainer, null, result);
    }

    public void endScript() {
        xmlWriter.endElement("Script");
        xmlWriter.endDocument();
        closeResult();
    }

    private void closeResult() {
        if(result instanceof StreamResult){
            try {
                ((StreamResult)result).getOutputStream().close();
            }
            catch (IOException e) {
                throw runtimeException(e);
            }
        }
    }

    public Result result() {
        return result;
    }

}
