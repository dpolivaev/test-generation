package scriptproducer;

import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;
import ruleengine.SpecialValues;

public class XmlScriptProducer implements ScriptProducer {

    private static final String TEST_CASE = "TestCase";
    private final XmlWriter xmlWriter;

    public XmlScriptProducer(XmlWriter xmlWriter) {
        this.xmlWriter = xmlWriter;
    }

    @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        startTestCase();
        Object testcaseContent = propertyContainer.get("testcase");
        if(!testcaseContent.equals(SpecialValues.UNDEFINED))
            xmlWriter.setAttribute("content", testcaseContent.toString());
        endTestCase();

    }

    private void endTestCase() {
        xmlWriter.endElement(TEST_CASE);
    }

    private XmlWriter startTestCase() {
        return xmlWriter.beginElement(TEST_CASE);
    }

}
