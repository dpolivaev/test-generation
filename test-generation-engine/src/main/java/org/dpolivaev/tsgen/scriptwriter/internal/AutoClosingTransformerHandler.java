package org.dpolivaev.tsgen.scriptwriter.internal;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

final class AutoClosingTransformerHandler implements
		TransformerHandler {
	private final TransformerHandler transformerHandler;
	private Result result;

	AutoClosingTransformerHandler(TransformerHandler transformerHandler) {
		this.transformerHandler = transformerHandler;
	}

	public void setResult(Result result)
			throws IllegalArgumentException {
		this.result = result;
		transformerHandler.setResult(result);
	}

	public void setSystemId(String systemID) {
		transformerHandler.setSystemId(systemID);
	}

	public String getSystemId() {
		return transformerHandler.getSystemId();
	}

	public Transformer getTransformer() {
		return transformerHandler.getTransformer();
	}

	public void startDTD(String name, String publicId, String systemId)
			throws SAXException {
		transformerHandler.startDTD(name, publicId, systemId);
	}

	public void notationDecl(String name, String publicId,
			String systemId) throws SAXException {
		transformerHandler.notationDecl(name, publicId, systemId);
	}

	public void setDocumentLocator(Locator locator) {
		transformerHandler.setDocumentLocator(locator);
	}

	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		transformerHandler.unparsedEntityDecl(name, publicId, systemId, notationName);
	}

	public void startDocument() throws SAXException {
		transformerHandler.startDocument();
	}

	public void endDTD() throws SAXException {
		transformerHandler.endDTD();
	}

	public void startEntity(String name) throws SAXException {
		transformerHandler.startEntity(name);
	}

	public void endDocument() throws SAXException {
		transformerHandler.endDocument();
		if(result  instanceof StreamResult){
			try {
				((StreamResult) result).getOutputStream().close();
			} catch (Exception e) {
			}
			result = null;
		}
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		transformerHandler.startPrefixMapping(prefix, uri);
	}

	public void endEntity(String name) throws SAXException {
		transformerHandler.endEntity(name);
	}

	public void startCDATA() throws SAXException {
		transformerHandler.startCDATA();
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		transformerHandler.endPrefixMapping(prefix);
	}

	public void endCDATA() throws SAXException {
		transformerHandler.endCDATA();
	}

	public void comment(char[] ch, int start, int length)
			throws SAXException {
		transformerHandler.comment(ch, start, length);
	}

	public void startElement(String uri, String localName,
			String qName, Attributes atts) throws SAXException {
		transformerHandler.startElement(uri, localName, qName, atts);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		transformerHandler.endElement(uri, localName, qName);
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		transformerHandler.characters(ch, start, length);
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		transformerHandler.ignorableWhitespace(ch, start, length);
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		transformerHandler.processingInstruction(target, data);
	}

	public void skippedEntity(String name) throws SAXException {
		transformerHandler.skippedEntity(name);
	}
}