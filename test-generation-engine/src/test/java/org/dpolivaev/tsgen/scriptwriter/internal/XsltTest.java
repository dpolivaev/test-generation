package org.dpolivaev.tsgen.scriptwriter.internal;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@RunWith(Parameterized.class)
public class XsltTest {
	
	@Parameterized.Parameters(name="{0}|{1}>{2}")
	   public static Collection<Object[]> resources() {
	      return Arrays.asList(new Object[][] {
	 	         { "/testInput.xml", "/java.xslt", "/expectedJava.txt" },
		         { "/testInput.xml", "/cmocka.xslt", "/expected_cmocka.c" }
	      });
	   }
	
	private String testInput;
	private String xslt;
	private String expected;
	private static TransformerFactory transFact;

	public XsltTest(String testInput, String xslt, String expected) {
		super();
		this.testInput = testInput;
		this.xslt = xslt;
		this.expected = expected;
	}

	private URL resource(String resource) {
		return getClass().getResource(resource);
	}

	private StreamSource streamSource(String resource) {
		return new StreamSource(getClass().getResourceAsStream(
				resource));
	}
	
	@BeforeClass
	public static void init(){
		transFact = TransformerFactory
				.newInstance(
						"com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl",
						XsltTest.class.getClassLoader());

	}

	@Test
	public void xsltTransform() throws Exception {
		Source xmlSource = streamSource(testInput);
		Source xsltSource = streamSource(xslt);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Result result = new StreamResult(outputStream);

		Transformer trans = transFact.newTransformer(xsltSource);

		trans.transform(xmlSource, result);

		String expectedJava = Resources.toString(resource(expected), Charsets.UTF_8);
		String actualJava = outputStream.toString(Charsets.UTF_8.toString());
		assertEquals(expectedJava, actualJava);
	}
}
