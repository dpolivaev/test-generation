package scriptproducer;

import java.util.Set;

import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;
import ruleengine.SpecialValues;

public class XmlScriptProducer implements ScriptProducer {

    private static final String TESTCASE_PROPERTY = "testcase";
    private static final String TESTCASE_ELEMENT = "TestCase";
    private final XmlWriter xmlWriter;

    public XmlScriptProducer(XmlWriter xmlWriter) {
        this.xmlWriter = xmlWriter;
    }

    @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        startTestCase();
        String property = TESTCASE_PROPERTY;
        addElementContent(propertyContainer, property);
        endTestCase();

    }

    public void addElementContent(PropertyContainer propertyContainer, String property) {
        Object testcaseContent = propertyContainer.get(property);
        if(!testcaseContent.equals(SpecialValues.UNDEFINED))
            xmlWriter.setAttribute("content", testcaseContent.toString());
        String prefix = property + '.';
        Set<String> availableProperties = propertyContainer.availableProperties(prefix);
        for(String attributeProperty : availableProperties){
            Object attributeValue = propertyContainer.get(attributeProperty);
            if(attributeValue != SpecialValues.UNDEFINED){
                String attributeName = attributeProperty.substring(prefix.length());
                xmlWriter.setAttribute(attributeName, attributeValue.toString());
            }
        }
    }

    private void endTestCase() {
        xmlWriter.endElement(TESTCASE_ELEMENT);
    }

    private XmlWriter startTestCase() {
        return xmlWriter.beginElement(TESTCASE_ELEMENT);
    }

}
