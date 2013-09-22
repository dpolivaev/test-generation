package org.dpolivaev.tsgen.scriptwriter.internal;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.*;
import java.net.URL;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class JavaXsltTest {

	@Test
	public void javaXslt() throws Exception {
		Source xmlSource = streamSource("/xml4java.xml");
		Source xsltSource = streamSource("/java.xslt");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Result result = new StreamResult(outputStream);

		TransformerFactory transFact = TransformerFactory
				.newInstance(
						"com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl",
						getClass().getClassLoader());

		Transformer trans = transFact.newTransformer(xsltSource);

		trans.transform(xmlSource, result);

		String expectedJava = Resources.toString(resource("/expectedJava.txt"), Charsets.UTF_8);
		String actualJava = outputStream.toString(Charsets.UTF_8.toString());
		assertEquals(expectedJava, actualJava);
	}

	private URL resource(String resource) {
		return getClass().getResource(resource);
	}

	private StreamSource streamSource(String resource) {
		return new StreamSource(getClass().getResourceAsStream(
				resource));
	}

}
