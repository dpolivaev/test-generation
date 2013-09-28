package org.dpolivaev.tsgen.scriptwriter.internal;

import org.xml.sax.ContentHandler;

import org.dpolivaev.tsgen.utils.internal.Utils;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class SaxXmlWriter implements XmlWriter {

    private String uri = "";
    public String getURI() {
        return uri;
    }

    public void setURI(String uri) {
        this.uri = uri;
    }

    private final ContentHandler handler;
    private AttributesImpl attrs;
    private String element;

    public SaxXmlWriter(ContentHandler handler) {
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

    public void beginElement(String element){
        try {
            outputElementOpeningTag();
            this.element = element;
            this.attrs = new AttributesImpl();
        }
        catch (Exception e) {
            throw Utils.runtimeException(e);
        }
    }

    public void endElement(String element) {
        try {
            outputElementOpeningTag();
            handler.endElement(uri, element, element);
            element = null;
        }
        catch (Exception e) {
            throw Utils.runtimeException(e);
        }
    }

    private void outputElementOpeningTag() throws SAXException {
        if(element != null){
            handler.startElement(uri, element, element, attrs);
            element = null;
        }
    }

    public void setAttribute(String name, String value) {
        attrs.addAttribute(uri, name, name, "CDATA", value);
    }

	@Override
	public void addTextContent(String content) {
		try {
			outputElementOpeningTag();
			handler.characters(content.toCharArray(), 0, content.length());
		} catch (SAXException e) {
            throw Utils.runtimeException(e);
		}
		
	}    
    
}