package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ContentHandlerMultiplier implements ContentHandler {
	private static final class IgnoringHandler implements ContentHandler {
		@Override
		public void startPrefixMapping(String arg0, String arg1)
				throws SAXException {
		}

		@Override
		public void startElement(String arg0, String arg1, String arg2,
				Attributes arg3){
		}

		@Override
		public void startDocument(){
		}

		@Override
		public void skippedEntity(String arg0){
		}

		@Override
		public void setDocumentLocator(Locator arg0) {
		}

		@Override
		public void processingInstruction(String arg0, String arg1)
				throws SAXException {
		}

		@Override
		public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
				throws SAXException {
		}

		@Override
		public void endPrefixMapping(String arg0){
		}

		@Override
		public void endElement(String arg0, String arg1, String arg2)
				throws SAXException {
		}

		@Override
		public void endDocument(){
		}

		@Override
		public void characters(char[] arg0, int arg1, int arg2){
		}
	}

	private static final ContentHandler DO_NOTHING = new IgnoringHandler();
	final private ContentHandler[] delegates;

	public ContentHandlerMultiplier(ContentHandler... contentHandlers) {
		delegates = contentHandlers;
	}
	
	public void characters(char[] arg0, int arg1, int arg2){
		for (int i = 0; i < delegates.length; i++)
			try {
				delegates[i].characters(arg0, arg1, arg2);
			}
			catch (SAXException e) {
				delegates[i] = DO_NOTHING;
				e.printStackTrace();
			}
	}

	public void endDocument(){
		for (int i = 0; i < delegates.length; i++)
			try {
				delegates[i].endDocument();
			}
			catch (SAXException e) {
				delegates[i] = DO_NOTHING;
				e.printStackTrace();
			}
	}

	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		for (int i = 0; i < delegates.length; i++)
			try {
			delegates[i].endElement(arg0, arg1, arg2);
			}
			catch (SAXException e) {
				delegates[i] = DO_NOTHING;
				e.printStackTrace();
			}
	}

	public void endPrefixMapping(String arg0){
		for (int i = 0; i < delegates.length; i++)
			try {
				delegates[i].endPrefixMapping(arg0);
			}
			catch (SAXException e) {
				delegates[i] = DO_NOTHING;
				e.printStackTrace();
			}
	}

	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		for (int i = 0; i < delegates.length; i++)
			delegates[i].ignorableWhitespace(arg0, arg1, arg2);
	}

	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		for (int i = 0; i < delegates.length; i++)
			delegates[i].processingInstruction(arg0, arg1);
	}

	public void setDocumentLocator(Locator arg0) {
		for (int i = 0; i < delegates.length; i++)
			delegates[i].setDocumentLocator(arg0);
	}

	public void skippedEntity(String arg0){
		for (int i = 0; i < delegates.length; i++)
			try {
				delegates[i].skippedEntity(arg0);
			}
			catch (SAXException e) {
				delegates[i] = DO_NOTHING;
				e.printStackTrace();
			}
	}

	public void startDocument(){
		for (int i = 0; i < delegates.length; i++)
			try {
				delegates[i].startDocument();
			}
			catch (SAXException e) {
				delegates[i] = DO_NOTHING;
				e.printStackTrace();
			}
	}

	public void startElement(String arg0, String arg1, String arg2,
			Attributes arg3){
		for (int i = 0; i < delegates.length; i++)
			try {
				delegates[i].startElement(arg0, arg1, arg2, arg3);
			}
			catch (SAXException e) {
				delegates[i] = DO_NOTHING;
				e.printStackTrace();
			}
	}

	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		for (int i = 0; i < delegates.length; i++)
			delegates[i].startPrefixMapping(arg0, arg1);
	}

}
