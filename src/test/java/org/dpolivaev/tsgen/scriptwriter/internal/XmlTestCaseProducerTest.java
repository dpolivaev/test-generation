package org.dpolivaev.tsgen.scriptwriter.internal;

import static org.dpolivaev.tsgen.testutils.TestUtils.assignmentMock;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import java.util.Arrays;
import java.util.List;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.TransformerHandler;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.ruleengine.Assignments;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

public class XmlTestCaseProducerTest {

    private DOMResult dom;
    private PropertyHandler producer;
    private Assignments propertyContainer;
    private XmlWriter xmlWriter;
	private List<Goal> goals;
	private Goal goal;
	private CoverageTracker coverageTracker;

    @Before
    public void setup() throws TransformerFactoryConfigurationError, TransformerConfigurationException, SAXException {
        dom = new DOMResult();
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        Mockito.when(resultFactory.newResult("result", "xml")).thenReturn(dom);
        TransformerHandler handler = new HandlerFactory(resultFactory).newHandler(OutputConfiguration.OUTPUT_XML.forScript("result"));
        xmlWriter = new XmlWriterUsingTransformerHandler(handler);
		goal = Mockito.mock(Goal.class);
		Mockito.when(goal.name()).thenReturn("name");
		coverageTracker = new CoverageTracker();
		Mockito.when(goal.coverageTracker()).thenReturn(coverageTracker);
		goals = Arrays.asList(goal);
        producer = new XmlTestCaseWriter(xmlWriter);
        propertyContainer = new Assignments();
    }

    private void givenProperty(String name, Object value) {
        propertyContainer.add(assignmentMock(name, value));
    }

    private void createScript() throws SAXException {
        xmlWriter.startDocument();
        producer.handlePropertyCombination(propertyContainer, goals);
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
        givenProperty("testcase", "id");
        createScript();
        checkOutput("<TestCase id='id'/>");
    }

     @Test
    public void createsTestCaseElementWithArbitraryAttribute() throws Exception{
        givenProperty("testcase.attribute", "attribute");
        createScript();
        checkOutput("<TestCase>"
        		+ "<Parameter name='attribute'>attribute</Parameter>"
        		+ "</TestCase>");
    }
    
     @Test
    public void createsTestCaseElementWithDescription() throws Exception{
        givenProperty("testcaseDescription", "attribute");
        createScript();
        checkOutput("<TestCase>"
        		+ "<Description>attribute</Description>"
        		+ "</TestCase>");
    }
     
    @Test
    public void ignoresUndefinedProperties() throws Exception{
        givenProperty("testcase.attribute", SpecialValue.UNDEFINED);
        createScript();
        checkOutput("<TestCase/>");
    }
    
    @Test
    public void createsTestCaseElementWithFocusPartAndContent() throws Exception{
        givenProperty("testcase", "testcase");
        givenProperty("foc", "focus");
        createScript();
        checkOutput("<TestCase id='testcase'><Focus id='focus'/></TestCase>");
    }

    @Test
    public void createsTestCaseElementWithTwoFocusPartsAndContent() throws Exception{
        givenProperty("testcase", "testcase");
        givenProperty("foc", "focus");
        givenProperty("foc1", "focus1");
        createScript();
        checkOutput("<TestCase id='testcase'><Focus id='focus'/><Focus id='focus1'/></TestCase>");
    }

    @Test
    public void createsTestCaseElementWithThreeFocusPartsAndContent() throws Exception{
        givenProperty("testcase", "testcase");
        givenProperty("foc", "focus");
        givenProperty("foc1", "focus1");
        givenProperty("foc3", "focus3");
        createScript();
        checkOutput("<TestCase id='testcase'><Focus id='focus'/><Focus id='focus1'/><Focus id='focus3'/></TestCase>");
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
        checkOutput("<TestCase id='testcase'>" +
                "<Precondition id='precondition'/>" +
                "<EnterState id='State'/>" +
                "<PreconditionInState id='precondition in state'/>" +
                "<Focus id='focus'/>" +
                "<VerificationInState id='verification in state'/>" +
                "<CheckState id='state after'/>" +
                "<Verification id='verification'/>" +
                "<Postprocessing id='postprocessing'/>" +
        	"</TestCase>");
    }
    @Test
    public void createsTestCaseElementWithFirstHitCoverage() throws Exception{
        givenCoverage("requirement id", "description");
        createScript();
        checkOutput("<TestCase>"
        		+ "<Goal name='name' item='requirement id' count='1' firstTime = 'true'>description</Goal>"
        		+ "</TestCase>");
    }

    @Test
    public void createsTestCaseElementWithSecondHitCoverage() throws Exception{
        givenCoverage("requirement id", "description");
        coverageTracker.startRound();
        givenCoverage("requirement id", "description");
        createScript();
        checkOutput("<TestCase>"
        		+ "<Goal name='name' item='requirement id' count='2' firstTime = 'false'>description</Goal>"
        		+ "</TestCase>");
    }

	private void givenCoverage(String requirementId, String description) {
		coverageTracker.add(new CoverageEntry(requirementId, description));
		
	}

}

