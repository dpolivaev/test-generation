package org.dpolivaev.tsgen.scriptwriter.internal;

import static java.util.Arrays.asList;
import static org.dpolivaev.tsgen.testutils.TestUtils.assignmentMock;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import org.dpolivaev.tsgen.coverage.CheckList;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.ruleengine.Assignments;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class XmlTestCaseWriterTest {

    private DOMResult dom;
    private PropertyHandler writer;
    private Assignments propertyContainer;
    private XmlWriter xmlWriter;
	private Goal goal;
	private CheckList checkList;

    @Before
    public void setup() throws TransformerFactoryConfigurationError, TransformerConfigurationException, SAXException {
        dom = new DOMResult();
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        Mockito.when(resultFactory.newResult(Mockito.<ScriptConfiguration>any())).thenReturn(dom);
        ContentHandler handler = new HandlerFactory(resultFactory).newHandler(OutputConfiguration.OUTPUT_XML.forScript("result"));
        xmlWriter = new SaxXmlWriter(handler);
		goal = Mockito.mock(Goal.class);
		Mockito.when(goal.name()).thenReturn("name");
		checkList = new CheckList();
		Mockito.when(goal.checkList()).thenReturn(checkList);
		GoalChecker goalChecker = new GoalChecker();
		goalChecker.addGoal(goal);
        writer = new XmlTestCaseWriter(xmlWriter, goalChecker);
        propertyContainer = new Assignments();
    }

    private void givenProperty(String name, Object value) {
        propertyContainer.add(assignmentMock(name, value, ""));
    }

    private void createScript() throws SAXException {
        xmlWriter.startDocument();
        writer.handlePropertyCombination(propertyContainer);
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
        givenProperty("foc", "focus");
        givenProperty("veri", "verification");
        givenProperty("post", "postprocessing");
        createScript();
        checkOutput("<TestCase id='testcase'>" +
                "<Precondition id='precondition'/>" +
                "<Focus id='focus'/>" +
                "<Verification id='verification'/>" +
                "<Postprocessing id='postprocessing'/>" +
        	"</TestCase>");
    }
    @Test
    public void createsTestCaseElementWithFirstHitCoverage() throws Exception{
        givenCoverage("requirement id", "description");
        createScript();
        checkOutput("<TestCase>"
        		+ "<Goal name='name'> <Item name='requirement id' count='1' firstTime = 'true'>description</Item></Goal>"
        		+ "</TestCase>");
    }

    @Test
    public void createsTestCaseElementWithSecondHitCoverage() throws Exception{
        givenCoverage("requirement id", "description");
        givenCoverage("requirement id", "description");
        createScript();
        checkOutput("<TestCase>"
        		+ "<Goal name='name'> <Item name='requirement id' count='2' firstTime = 'false'>description</Item></Goal>"
        		+ "</TestCase>");
    }

	private void givenCoverage(String requirementId, String description) {
		checkList.addReached(asList(new CoverageEntry(requirementId, description)));
		
	}

}

