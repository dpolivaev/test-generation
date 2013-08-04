package scriptproducer;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import utils.Utils;

class XmlProducerUsingTransformerHandler implements XmlWriter {

    private String uri = "";
    public String getURI() {
        return uri;
    }

    public void setURI(String uri) {
        this.uri = uri;
    }

    private final TransformerHandler handler;
    private AttributesImpl attrs;

    public XmlProducerUsingTransformerHandler(TransformerHandler handler) {
        super();
        this.handler = handler;
    }

    public void startDocument() {
        try {
            handler.startDocument();
        }
        catch (Exception e) {
            throw Utils.runtimeException(e);
        }
    }

    public void endDocument() {
        try {
            handler.endDocument();
        }
        catch (Exception e) {
            throw Utils.runtimeException(e);
        }
    }

    public void addElement(String element, AttributesImpl attrs, String textContent) {
        try {
            handler.startElement(uri, element, element, attrs);
            handler.characters(textContent.toCharArray(), 0, textContent.length());
            handler.endElement(uri, element, element);
        }
        catch (Exception e) {
            throw Utils.runtimeException(e);
        }
    }

    public XmlWriter beginElement(String element) {
        this.attrs = new AttributesImpl();
        try {
            return this;
        }
        catch (Exception e) {
            throw Utils.runtimeException(e);
        }
    }

    public XmlWriter endElement(String element) {
        try {
            handler.startElement(uri, element, element, attrs);
            handler.endElement(uri, element, element);
            return this;
        }
        catch (Exception e) {
            throw Utils.runtimeException(e);
        }
    }

    public XmlWriter setAttribute(String name, String value) {
        attrs.addAttribute(uri, name, name, "CDATA", value);
        return this;
    }    
    
}