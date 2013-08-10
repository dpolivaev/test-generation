package scriptproducer;

import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;
import static ruleengine.TestUtils.assignmentMock;

import javax.xml.transform.dom.DOMResult;

import org.junit.Assert;
import org.junit.Test;

import ruleengine.Assignments;
import ruleengine.ScriptProducer;

public class XmlScriptProducerTest {
    private Assignments propertyContainer = new Assignments();
    private void givenProperty(String name, Object value) {
        propertyContainer.add(assignmentMock(name, value));
    }

    @Test
    public void test() {
        givenProperty("script", "scriptName");
        XmlScriptProducer producer = new XmlScriptProducer();
        producer.makeScriptFor(propertyContainer);
        DOMResult dom = producer.getResult("scriptName");
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the("<Script/>")));
    }

}
