package scriptproducer;

import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;
import static ruleengine.TestUtils.assignmentMock;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.TransformerHandler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import ruleengine.Assignments;
import ruleengine.SpecialValues;

public class XmlTestCaseProducerTest {

    private DOMResult dom;
    private XmlTestCaseProducer producer;
    private Assignments propertyContainer;
    private XmlWriter xmlWriter;

    @Before
    public void setup() throws TransformerFactoryConfigurationError, TransformerConfigurationException, SAXException {
        dom = new DOMResult();
        TransformerHandler handler = new HandlerFactory().newHandler(dom);
        xmlWriter = new XmlProducerUsingTransformerHandler(handler);
        producer = new XmlTestCaseProducer(xmlWriter);
        propertyContainer = new Assignments();
    }

    private void givenProperty(String name, Object value) {
        propertyContainer.add(assignmentMock(name, value));
    }

    private void createScript() throws SAXException {
        xmlWriter.startDocument();
        producer.makeScriptFor(propertyContainer);
        xmlWriter.endDocument();
    }

    private void checkOutput(String xml) {
        Assert.assertThat(the(dom.getNode()), isEquivalentTo(the(xml)));
    }

    @Test
    public void createsTestCaseElement() throws Exception{
        createScript();
        checkOutput("<TestCase/>");
    }

    @Test
    public void createsTestCaseElementWithContent() throws Exception{
        givenProperty("testcase", "self");
        createScript();
        checkOutput("<TestCase self='self'/>");
    }

     @Test
    public void createsTestCaseElementWithArbitraryAttribute() throws Exception{
        givenProperty("testcase.attribute", "attribute");
        createScript();
        checkOutput("<TestCase attribute='attribute'/>");
    }
    
    @Test
    public void ignoresUndefinedProperties() throws Exception{
        givenProperty("testcase.attribute", SpecialValues.UNDEFINED);
        createScript();
        checkOutput("<TestCase/>");
    }
    
    @Test
    public void createsTestCaseElementWithFocusPartAndContent() throws Exception{
        givenProperty("testcase", "testcase");
        givenProperty("foc", "focus");
        createScript();
        checkOutput("<TestCase self='testcase'><Focus self='focus'/></TestCase>");
    }

    @Test
    public void createsTestCaseElementWithTwoFocusPartsAndContent() throws Exception{
        givenProperty("testcase", "testcase");
        givenProperty("foc", "focus");
        givenProperty("foc1", "focus1");
        createScript();
        checkOutput("<TestCase self='testcase'><Focus self='focus'/><Focus self='focus1'/></TestCase>");
    }

    @Test
    public void createsTestCaseElementWithThreeFocusPartsAndContent() throws Exception{
        givenProperty("testcase", "testcase");
        givenProperty("foc", "focus");
        givenProperty("foc1", "focus1");
        givenProperty("foc3", "focus3");
        createScript();
        checkOutput("<TestCase self='testcase'><Focus self='focus'/><Focus self='focus1'/><Focus self='focus3'/></TestCase>");
    }


    @Test
    public void createsTestCaseElementWithAllPartsAndContent() throws Exception{
        givenProperty("testcase", "testcase");
        givenProperty("pre", "precondition");
        givenProperty("state", "State");
        givenProperty("preInState", "precondition in state");
        givenProperty("foc", "focus");
        givenProperty("veriInState", "verification in state");
        givenProperty("stateAfter", "state after");
        givenProperty("veri", "verification");
        givenProperty("post", "postprocessing");
        createScript();
        checkOutput("<TestCase self='testcase'>" +
                "<Precondition self='precondition'/>" +
                "<State self='State'/>" +
                "<PreconditionInState self='precondition in state'/>" +
                "<Focus self='focus'/>" +
                "<VerificationInState self='verification in state'/>" +
                "<StateAfter self='state after'/>" +
                "<Verification self='verification'/>" +
                "<Postprocessing self='postprocessing'/>" +
        	"</TestCase>");
    }
}

