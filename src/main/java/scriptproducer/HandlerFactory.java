package scriptproducer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;

import utils.Utils;

public class HandlerFactory {

    public TransformerHandler newHandler(Result result){
        try {
            TransformerHandler handler;
            SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            handler = tf.newTransformerHandler();
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
