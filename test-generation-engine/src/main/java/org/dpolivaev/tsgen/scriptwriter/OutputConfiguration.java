package org.dpolivaev.tsgen.scriptwriter;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class OutputConfiguration {
	private String xsltSource;
	private String fileExtension;
	private String xmlExtension;
	private String fileDirectory;
	private String xmlDirectory;
	
	public static final OutputConfiguration OUTPUT_NOTHING = new OutputConfiguration();

	final private String[] testCaseParts = {
			"testcase.precondition", "Precondition",
			"testcase.focus", "Focus",
			"testcase.verification", "Verification",
			"testcase.postprocessing", "Postprocessing",
	};
	final private String[] scriptParts = {
			"script.precondition", "ScriptPrecondition",
			"script.postprocessing", "ScriptPostprocessing",
	};
	private static final int PRECONDITION_INDEX = 0;
	private static final int FOCUS_INDEX = 2;
	private static final int VERIFICATION_INDEX = 4;
	private static final int POSTPOCESSING_INDEX = 6;
	private static final int SCRIPT_PRECONDITION_INDEX = 0;
	private static final int SCRIPT_POSTPOCESSING_INDEX = 2;

	public OutputConfiguration(){
	}

	public Source getXsltSource() {
		if(xsltSource == null)
			return null;
		File xsltFile = new File(xsltSource);
		if(xsltFile.canRead()) {
			return new StreamSource(xsltFile);
		}
		else{
			InputStream resource = getClass().getResourceAsStream(xsltSource);
			if(resource != null) {
				return new StreamSource(resource);
			}
			else
				throw new IllegalArgumentException("source " + xsltSource + " not available");
		}
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
		this.xsltSource = xsltSource;
		return this;
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
		if (containsText(precondition))
			testCaseParts[propertyIndex] = precondition;
		propertyIndex += 2;
		if (containsText(focus))
			testCaseParts[propertyIndex] = focus;
		propertyIndex += 2;
		if (containsText(verification))
			testCaseParts[propertyIndex] = verification;
		propertyIndex += 2;
		if (containsText(postprocessing))
			testCaseParts[propertyIndex] = postprocessing;
	}

	private boolean containsText(String string) {
		return string != null  && ! string.isEmpty();
	}

	public String[] getScriptParts() {
		return scriptParts;
	}

	public void setScriptPropertyNames(String scriptPrecondition, String scriptPostprocessing) {
		int propertyIndex = 0;
		if (containsText(scriptPrecondition))
			scriptParts[propertyIndex] = scriptPrecondition;
		propertyIndex += 2;
		if (containsText(scriptPostprocessing))
			scriptParts[propertyIndex] = scriptPostprocessing;
	}
	
	public String getPreconditionPropertyName() {
		return getTestCaseParts()[PRECONDITION_INDEX];
	}
	
	public String getFocusPropertyName() {
		return getTestCaseParts()[FOCUS_INDEX];
	}
	
	public String getVerificationPropertyName() {
		return getTestCaseParts()[VERIFICATION_INDEX];
	}
	
	public String getPostprocessingPropertyName() {
		return getTestCaseParts()[POSTPOCESSING_INDEX];
	}
	
	public String getScriptPreconditionPropertyName() {
		return getScriptParts()[SCRIPT_PRECONDITION_INDEX];
	}
	
	public String getScriptPostprocessingPropertyName() {
		return getScriptParts()[SCRIPT_POSTPOCESSING_INDEX];
	}
	
	public boolean isPart(String name){
		return Arrays.asList(testCaseParts).contains(name) || Arrays.asList(scriptParts).contains(name);
	}

	
	
}