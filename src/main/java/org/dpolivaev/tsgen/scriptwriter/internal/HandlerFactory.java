package org.dpolivaev.tsgen.scriptwriter.internal;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;
import org.dpolivaev.tsgen.utils.internal.Utils;

public class HandlerFactory {
	private ResultFactory resultFactory;
    
    public HandlerFactory(ResultFactory resultFactory) {
		this.resultFactory = resultFactory;
	}

	public TransformerHandler newHandler(ScriptConfiguration scriptConfiguration) {
		Result result = resultFactory.newResult(scriptConfiguration.scriptName, scriptConfiguration.outputConfiguration.fileExtension);
        try {
            SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl", null);
            Source xsltSource = scriptConfiguration.outputConfiguration.xsltSource;
            final TransformerHandler handler = xsltSource != null ? tf.newTransformerHandler(xsltSource) :  tf.newTransformerHandler();
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
