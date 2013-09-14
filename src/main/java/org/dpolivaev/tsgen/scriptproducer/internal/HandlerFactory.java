package org.dpolivaev.tsgen.scriptproducer.internal;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.dpolivaev.tsgen.utils.internal.Utils;

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
            SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl", null);
            handler = xsltSource != null ? tf.newTransformerHandler(xsltSource) :  tf.newTransformerHandler();
            Transformer transformer = handler.getTransformer();
            if(xsltSource == null){
            	transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            }
            handler.setResult(result);
            return handler;
        }
        catch (Exception e) {
            throw Utils.runtimeException(e);
        }
    }

}
