package org.dpolivaev.tsgen.scriptwriter.internal;

public interface XmlWriter {
    void startDocument();

    void beginElement(String name);

    void endElement(String name);

    void setAttribute(String name, String value);

    void endDocument();

	void addTextContent(String string);

}
