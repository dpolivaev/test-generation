package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import static java.util.Arrays.asList;
import static org.dpolivaev.testgeneration.engine.testutils.TestUtils.assignmentMock;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;

import org.dpolivaev.testgeneration.engine.coverage.CheckList;
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.Goal;
import org.dpolivaev.testgeneration.engine.coverage.GoalChecker;
import org.dpolivaev.testgeneration.engine.ruleengine.Assignments;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.HandlerFactory;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.ResultFactory;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.SaxXmlWriter;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.ScriptConfiguration;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.XmlWriter;
import org.dpolivaev.testgeneration.engine.testutils.TestUtils;
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

	private void givenCoverage(String requirementId, String description) {
		checkList.addReached(asList(new CoverageEntry(requirementId, description)));
		
	}

    @Before
    public void setup() throws TransformerFactoryConfigurationError, TransformerConfigurationException, SAXException {
        dom = new DOMResult();
        ResultFactory resultFactory = Mockito.mock(ResultFactory.class);
        Mockito.when(resultFactory.newResult(Mockito.<ScriptConfiguration>any())).thenReturn(dom);
        ContentHandler handler = new HandlerFactory(resultFactory).newHandler(new ScriptConfiguration(OutputConfiguration.OUTPUT_NOTHING, "result"));
        xmlWriter = new SaxXmlWriter(handler);
		goal = Mockito.mock(Goal.class);
		Mockito.when(goal.name()).thenReturn("name");
		checkList = new CheckList();
		Mockito.when(goal.checkList()).thenReturn(checkList);
		GoalChecker goalChecker = new GoalChecker();
		goalChecker.addGoal(goal);
        writer = new ScriptConfiguration(new OutputConfiguration(), null).testCaseWriter(xmlWriter, goalChecker);
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
        TestUtils.assertXmlEquals(xml, dom.getNode());
    }

    @Test
    public void createsTestCaseElement() throws Exception{
        createScript();
        checkOutput("<TestCase/>");
    }

    @Test
    public void createsTestCaseElementWithContent() throws Exception{
        givenProperty("testcase.name", "name");
        createScript();
        checkOutput("<TestCase name='name'/>");
    }

     @Test
    public void createsTestCaseElementWithDescription() throws Exception{
        givenProperty("testcase.description", "attribute");
        createScript();
        checkOutput("<TestCase>"
        		+ "<Description>attribute</Description>"
        		+ "</TestCase>");
    }
     
    @Test
    public void ignoresUndefinedProperties() throws Exception{
        givenProperty("testcase.focus", SpecialValue.UNDEFINED);
        createScript();
        checkOutput("<TestCase/>");
    }
    
    @Test
    public void createsTestCaseElementWithFocusPart() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.focus", "focus");
        createScript();
        checkOutput("<TestCase name='testcase'><Focus step='focus'/></TestCase>");
    }

    @Test
    public void createsTestCaseElementWithCommentedFocusPart() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.focus", "focus ; comment");
        createScript();
        checkOutput("<TestCase name='testcase'>"
			+ "<Focus step='focus'>"
			+ "<Comment>comment</Comment>"
			+ "</Focus>"
			+ "</TestCase>");
    }

    @Test
    public void createsTestCaseElementWithFocusElementAlias() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("Focus.alias", "Alias");
        givenProperty("testcase.focus", "focus");
        createScript();
        checkOutput("<TestCase name='testcase'><Alias step='focus'/></TestCase>");
    }
    @Test
    public void createsTestCaseElementWithTestCaseElementAlias() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("TestCase.alias", "Alias");
        createScript();
        checkOutput("<Alias name='testcase'/>");
    }

    @Test
    public void createsTestCaseElementWithTestCaseAlias() throws Exception{
        givenProperty("testcase.alias", "scenario");
        givenProperty("TestCase.alias", "Scenario");
        givenProperty("scenario.name", "scenario");
        createScript();
        checkOutput("<Scenario name='scenario'/>");
    }

    @Test
    public void ignoredTestPartAlias() throws Exception{
        givenProperty("testcase.focus.alias", "focus");
        givenProperty("testcase.name", "testcase");
        createScript();
        checkOutput("<TestCase name='testcase'/>");
    }

    @Test
    public void createsTestCaseElementWithTwoFocusParts() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.focus", "focus");
        givenProperty("testcase.focus#1", "focus1");
        createScript();
        checkOutput("<TestCase name='testcase'><Focus step='focus'/><Focus step='focus1'/></TestCase>");
    }

    @Test
    public void createsTestCaseElementWithThreeFocusPartsAndContent() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.focus", "focus");
        givenProperty("testcase.focus#1", "focus1");
        givenProperty("testcase.focus#3", "focus3");
        createScript();
        checkOutput("<TestCase name='testcase'><Focus step='focus'/><Focus step='focus1'/><Focus step='focus3'/></TestCase>");
    }


    @Test
    public void createsTestCaseElementWithAllPartsAndContent() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.precondition", "precondition");
        givenProperty("testcase.focus", "focus");
        givenProperty("testcase.verification", "verification");
        givenProperty("testcase.postprocessing", "postprocessing");
        createScript();
        checkOutput("<TestCase name='testcase'>" +
                "<Precondition step='precondition'/>" +
                "<Focus step='focus'/>" +
                "<Verification step='verification'/>" +
                "<Postprocessing step='postprocessing'/>" +
        	"</TestCase>");
    }
    @Test
    public void createsTestCaseElementWithFirstHitCoverage() throws Exception{
        givenCoverage("requirement id", "description");
        createScript();
        checkOutput("<TestCase>"
        		+ "<Goal name='name'><Item name='requirement id' count='1' firstTime = 'true'>description</Item></Goal>"
        		+ "</TestCase>");
    }

    @Test
    public void createsTestCaseElementWithSecondHitCoverage() throws Exception{
        givenCoverage("requirement id", "description");
        givenCoverage("requirement id", "description");
        createScript();
        checkOutput("<TestCase>"
        		+ "<Goal name='name'><Item name='requirement id' count='2' firstTime = 'false'>description</Item></Goal>"
        		+ "</TestCase>");
    }

    @Test
    public void createsTestCaseElementWithZeroFocusParameters() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.focus", "focus ()");
        createScript();
        checkOutput("<TestCase name='testcase'><Focus step='focus'>"
        		+ "</Focus></TestCase>");
    }
    
    @Test
    public void createsTestCaseElementWithUndefinedFocusParameters() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.focus", "focus (:x)");
        createScript();
        checkOutput("<TestCase name='testcase'><Focus step='focus'>"
        		+ "<Parameter name='x'  type='org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue'>UNDEFINED</Parameter>"
        		+ "</Focus></TestCase>");
    }
    

    @Test
    public void createsTestCaseElementWithNamedFocusParameters() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.focus", "focus (x:y)");
        createScript();
        checkOutput("<TestCase name='testcase'><Focus step='focus'>"
			+ "<Parameter type='' name='x'>y</Parameter>"
			+ "</Focus></TestCase>");
    }

    @Test
    public void createsTestCaseElementWithLiteralFocusParameter() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.focus", "focus (x)");
        createScript();
        checkOutput("<TestCase name='testcase'><Focus step='focus'>"
			+ "<Parameter type='' name='x'>x</Parameter>"
			+ "</Focus></TestCase>");
    }
    @Test
    public void createsTestCaseElementWithOneFocusParameter() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.focus", "focus (:x)");
        givenProperty("x", 1);
        createScript();
        checkOutput("<TestCase name='testcase'><Focus step='focus'>"
        		+ "<Parameter type='java.lang.Integer' name='x'>1</Parameter>"
        		+ "</Focus></TestCase>");
    }

    
    @Test
    public void createsTestCaseElementWithTwoFocusParameters() throws Exception{
        givenProperty("testcase.name", "testcase");
        givenProperty("testcase.focus", "focus (:x, :y)");
        givenProperty("x", "1");
        givenProperty("y", "2");
        createScript();
        checkOutput("<TestCase name='testcase'><Focus step='focus'>"
        		+ "<Parameter type='java.lang.String' name='x'>1</Parameter>"
        		+ "<Parameter type='java.lang.String' name='y'>2</Parameter>"
        		+ "</Focus></TestCase>");
    }
}

