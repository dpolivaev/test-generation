package org.dpolivaev.tsgen.scriptwriter;

import java.io.File;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class OutputConfiguration {
	private Source xsltSource;
	private String fileExtension;
	private String xmlExtension;
	private String fileDirectory;
	private String xmlDirectory;
	
	public static final OutputConfiguration OUTPUT_NOTHING = new OutputConfiguration();

	final private String[] testCaseParts = {
			"precondition", "Precondition",
			"focus", "Focus",
			"verification", "Verification",
			"postprocessing", "Postprocessing",
	};
	final private String[] scriptParts = {
			"scriptprecondition", "ScriptPrecondition",
			"scriptpostprocessing", "ScriptPostprocessing",
	};
	public static final int FOCUS_INDEX = 2;

	public OutputConfiguration(){
	}

	public Source getXsltSource() {
		return xsltSource;
	}

	public OutputConfiguration setXsltSource(Source xsltSource) {
		this.xsltSource = xsltSource;
		return this;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public OutputConfiguration setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
		return this;
	}

	public String getXmlExtension() {
		return xmlExtension;
	}
	
	public OutputConfiguration setXsltSource(String xsltSource) {
		File xsltFile = new File(xsltSource);
		if(xsltFile.canRead()) {
			return setXsltSource(new StreamSource(xsltFile));
		}
		else{
			InputStream resource = getClass().getResourceAsStream(xsltSource);
			if(resource != null) {
				return setXsltSource(new StreamSource(resource));
			}
			else
				throw new IllegalArgumentException("source " + xsltSource + " not available");
		}
	}

	public OutputConfiguration setXmlExtension(String xmlFileExtension) {
		this.xmlExtension = xmlFileExtension;
		return this;
	}

	public String getFileDirectory() {
		return fileDirectory;
	}

	public OutputConfiguration setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
		return this;
	}

	public String getXmlDirectory() {
		return xmlDirectory;
	}

	public OutputConfiguration setXmlDirectory(String xmlDirectory) {
		this.xmlDirectory = xmlDirectory;
		return this;
	}

	public boolean isFileValid() {
		return  getFileExtension() != null;
	}

	public String[] getTestCaseParts() {
		return testCaseParts;
	}

	public void setTestCasePropertyNames(String precondition, String focus, String verification, String postprocessing) {
		int propertyIndex = 0;
		testCaseParts[propertyIndex] = precondition;
		propertyIndex += 2;
		testCaseParts[propertyIndex] = focus;
		propertyIndex += 2;
		testCaseParts[propertyIndex] = verification;
		propertyIndex += 2;
		testCaseParts[propertyIndex] = postprocessing;
	}

	public String[] getScriptParts() {
		return scriptParts;
	}

	public void setScriptPropertyNames(String scriptPrecondition, String scriptPostprocessing) {
		int propertyIndex = 0;
		scriptParts[propertyIndex] = scriptPrecondition;
		propertyIndex += 2;
		scriptParts[propertyIndex] = scriptPostprocessing;
	}
	
	public String getFocusPropertyName() {
		return getTestCaseParts()[FOCUS_INDEX];
	}

	
	
}