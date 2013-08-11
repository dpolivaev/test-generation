package scriptproducer;

import java.util.Set;

import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;
import ruleengine.SpecialValues;

public class TestCaseProducer implements ScriptProducer {

    private static final String TESTCASE_PROPERTY = "testcase";
    private static final String TESTCASE_ELEMENT = "TestCase";
    
    private static final String[] optionalElements = {
        "pre", "Precondition",
        "state", "State",
        "preInState", "PreconditionInState",
        "foc", "Focus",
        "veriInState", "VerificationInState",
        "stateAfter", "StateAfter",
        "veri", "Verification",
        "post", "Postprocessing",
    };
    private final XmlWriter xmlWriter;

    public TestCaseProducer(XmlWriter xmlWriter) {
        this.xmlWriter = xmlWriter;
    }

    @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        xmlWriter.beginElement(TESTCASE_ELEMENT);
        addAttributes(propertyContainer, TESTCASE_PROPERTY);
        for(int i = 0; i < optionalElements.length; i+=2)
            addOptionalElement(propertyContainer, optionalElements[i], optionalElements[i+1]);
        xmlWriter.endElement(TESTCASE_ELEMENT);

    }

    private void addOptionalElement(PropertyContainer propertyContainer, String property, String element) {
        if(propertyContainer.containsPropertyValue(property))
            addElement(propertyContainer, property, element);
        for(int i = 1; i <= 9; i++)
            if(propertyContainer.containsPropertyValue(property + i))
                addElement(propertyContainer, property + i, element);
    }

    private void addElement(PropertyContainer propertyContainer, String property, String element) {
        xmlWriter.beginElement(element);
        addAttributes(propertyContainer, property);
        xmlWriter.endElement(element);
    }

    public void addAttributes(PropertyContainer propertyContainer, String property) {
        Object value = propertyContainer.get(property);
        if(!value.equals(SpecialValues.UNDEFINED))
            xmlWriter.setAttribute("self", value.toString());
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
}
