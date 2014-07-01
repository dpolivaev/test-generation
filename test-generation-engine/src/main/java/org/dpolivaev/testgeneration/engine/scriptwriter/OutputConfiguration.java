package org.dpolivaev.testgeneration.engine.scriptwriter;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class OutputConfiguration {
	private String xsltSource;
	private String fileExtension;
	private String xmlExtension;
	private String fileDirectory;
	private String xmlDirectory;
	final private Map<String, Object> xsltParameters;
	public static final int TEST_PART_NUMBER_MAXIMUM = 99;
	
	public static final OutputConfiguration OUTPUT_NOTHING = new OutputConfiguration();

	public OutputConfiguration(){
		xsltParameters = new HashMap<>();
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

	public Map<String, Object> getXsltParameters() {
		return xsltParameters;
	}

	public Object putXsltParameter(String key, Object value) {
		return xsltParameters.put(key, value);
	}


	
}