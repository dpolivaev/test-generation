package scriptproducer;

import org.xml.sax.SAXException;

public interface XmlWriter {

    XmlWriter beginElement(String name);

    XmlWriter endElement(String name);

    XmlWriter setAttribute(String name, String value);

}
