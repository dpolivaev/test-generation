package scriptproducer.impl;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import utils.Utils;

public class HandlerFactory {
    private Source xsltSource;
    
    public HandlerFactory(Source xsltSource) {
        this.xsltSource = xsltSource;
    }

    public HandlerFactory() {
        this(null);
    }
    
    public TransformerHandler newHandler(Result result){
        try {
            TransformerHandler handler;
            SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            handler = xsltSource != null ? tf.newTransformerHandler(xsltSource) :  tf.newTransformerHandler();
            Transformer transformer = handler.getTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            handler.setResult(result);
            handler.startDocument();
            return handler;
        }
        catch (Exception e) {
            throw Utils.runtimeException(e);
        }
    }

}
