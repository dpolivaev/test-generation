package org.dpolivaev.testgeneration.engine.testutils;

import static org.mockito.Mockito.mock;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.junit.Assert;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class TestUtils {
    public static Rule ruleMock(String targetedPropertyName) {
        Rule rule = mock(Rule.class);
        Mockito.when(rule.getTargetedPropertyName()).thenReturn(targetedPropertyName);
        return rule;
    }
 
	static public Assignment assignmentMock(String name, Object value, String reason) {
		return new Assignment(ruleMock(name), value, reason);
	}

	public static void assertEqualsXml(String expected, Node actual) {
		assertXmlEquals(expected, actual);
	}

	public static void assertXmlEquals(String expected, Node actual){
	    assertXmlEquals(nodeFrom(expected), actual);
	}

	public static void assertXmlEquals(Node expected, Node actual) {
		String expectedFormatted = format(expected);
	    String actualFormatted = format(actual);
		Assert.assertEquals(expectedFormatted, actualFormatted);
	}

	public static Element nodeFrom(String expected){
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.parse(new InputSource(new StringReader(expected)));
			Element documentElement = doc.getDocumentElement();
			return documentElement;
		} catch (Exception e) {
			throw runtimeException(e);
		}
	}

	public static RuntimeException runtimeException(Exception e) {
		if(e instanceof RuntimeException) 
			return (RuntimeException) e;
		else
			return new RuntimeException(e);
	}

	public static String format(Node node){
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			StringWriter sw = new StringWriter();
			transformer.transform(new DOMSource(node), new StreamResult(sw));
			String actualFormatted = sw.toString();
			return actualFormatted;
		} catch (Exception e) {
			throw runtimeException(e);
		}
	}

	static public ArgumentMatcher<Rule> rulePropertyNameMatches(final String name) {
		return new ArgumentMatcher<Rule>() {
			@Override
			public boolean matches(Object argument) {
				return ((Rule)argument).getTargetedPropertyName().equals(name);
			}
		};
	}
}
