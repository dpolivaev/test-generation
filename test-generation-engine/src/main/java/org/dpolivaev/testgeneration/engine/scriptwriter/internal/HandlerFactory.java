package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration;
import org.dpolivaev.testgeneration.engine.utils.internal.Utils;
import org.xml.sax.ContentHandler;

public class HandlerFactory {
	private ResultFactory resultFactory;
    
    public HandlerFactory(ResultFactory resultFactory) {
		this.resultFactory = resultFactory;
	}

	public ContentHandler newHandler(ScriptConfiguration scriptConfiguration) {
		Result result = resultFactory.newResult(scriptConfiguration);
        try {
            SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl", null);
            final OutputConfiguration outputConfiguration = scriptConfiguration.outputConfiguration;
			Source xsltSource = outputConfiguration.getXsltSource();
            final TransformerHandler xsltHandler = xsltSource != null ? tf.newTransformerHandler(xsltSource) :  tf.newTransformerHandler();
            if(xsltSource == null)
				setOutputProperties(xsltHandler);
            else
            	xsltHandler.getTransformer().setParameter("scriptConfiguration", this);
            final TransformerHandler closingXsltHandler = autoCloseStream(xsltHandler);
			closingXsltHandler.setResult(result);
            if(xsltSource == null || outputConfiguration.getXmlExtension() == null)
            	return closingXsltHandler;
			TransformerHandler plainXmlHandler = tf.newTransformerHandler();
			final File xmlFile = scriptConfiguration.xmlFile();
        	setOutputProperties(plainXmlHandler);
			final TransformerHandler closingXmlHandler = autoCloseStream(plainXmlHandler);
			closingXmlHandler.setResult(new StreamResultFactory().streamResult(xmlFile));
            return new ContentHandlerMultiplier(closingXmlHandler, closingXsltHandler);
            
        }
        catch (Exception e) {
            throw Utils.runtimeException(e);
        }
	}

	public TransformerHandler autoCloseStream(
			final TransformerHandler transformerHandler) {
		return new AutoClosingTransformerHandler(transformerHandler);
	}

	public void setOutputProperties(final TransformerHandler xsltHandler) {
		Transformer transformer = xsltHandler.getTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT,"yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	}

}
