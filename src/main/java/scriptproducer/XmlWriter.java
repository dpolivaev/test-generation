package scriptproducer;

public interface XmlWriter {

    XmlWriter beginElement(String name);

    XmlWriter endElement(String name);

    XmlWriter setAttribute(String name, String value);

}
