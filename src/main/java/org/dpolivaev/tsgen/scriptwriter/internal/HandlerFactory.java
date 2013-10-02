package org.dpolivaev.tsgen.scriptwriter.internal;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;
import org.dpolivaev.tsgen.utils.internal.Utils;
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
			Source xsltSource = outputConfiguration.xsltSource;
            final TransformerHandler transformerHandler = xsltSource != null ? tf.newTransformerHandler(xsltSource) :  tf.newTransformerHandler();
			final TransformerHandler xsltHandler = autoCloseStream(transformerHandler);
            if(xsltSource == null){
            	setOutputProperties(autoCloseStream(xsltHandler));
            }
            autoCloseStream(xsltHandler).setResult(result);
            if(xsltSource == null || ! outputConfiguration.outputXml)
            	return autoCloseStream(xsltHandler);
			TransformerHandler plainXmlHandler = tf.newTransformerHandler();
			final File xmlFile = new File(outputConfiguration.directory, scriptConfiguration.scriptName + ".xml");
        	setOutputProperties(autoCloseStream(plainXmlHandler));
			autoCloseStream(plainXmlHandler).setResult(new StreamResult(new FileOutputStream(xmlFile)));
            return new ContentHandlerMultiplier(autoCloseStream(plainXmlHandler), autoCloseStream(xsltHandler));
            
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
		Transformer transformer = autoCloseStream(xsltHandler).getTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT,"yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	}

}
