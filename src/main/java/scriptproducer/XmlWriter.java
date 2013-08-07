package scriptproducer;

public interface XmlWriter {
    void startDocument();

    void beginElement(String name);

    void endElement(String name);

    void setAttribute(String name, String value);

    void endDocument();

}
