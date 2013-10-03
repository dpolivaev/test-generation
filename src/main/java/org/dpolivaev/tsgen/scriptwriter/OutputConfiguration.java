package org.dpolivaev.tsgen.scriptwriter;

import java.io.File;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class OutputConfiguration {
	private Source xsltSource;
	private String fileExtension;
	private File directory;
	private String xmlFileExtension;
	
	public static final OutputConfiguration OUTPUT_XML = new OutputConfiguration();

	public OutputConfiguration(){
		this("xml");
	}
	
	public OutputConfiguration(File directory, String outputXml, Source xsltSource, String fileExtension) {
		super();
		this.xsltSource = xsltSource;
		this.fileExtension = fileExtension;
		this.directory = directory;
		this.xmlFileExtension = outputXml;
	}

	public OutputConfiguration(String extension) {
		this(null, null, null, extension);
	}

	public ScriptConfiguration forScript(String name){
		final ScriptConfiguration scriptConfiguration = new ScriptConfiguration(this, name);
		return scriptConfiguration;
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

	public File getDirectory() {
		return directory;
	}

	public OutputConfiguration setDirectory(File directory) {
		this.directory = directory;
		return this;
	}

	public String getXmlFileExtension() {
		return xmlFileExtension;
	}
	
	public void setXsltSource(String xsltSource) {
		File xsltFile = new File(xsltSource);
		if(xsltFile.canRead()) {
			setXsltSource(new StreamSource(xsltFile));
		}
		else{
			InputStream resource = getClass().getResourceAsStream(xsltSource);
			if(resource != null) {
				setXsltSource(new StreamSource(resource));
			}
			else
				throw new IllegalArgumentException("source " + xsltSource + " not available");
		}
	}



	public OutputConfiguration setXmlFileExtension(String xmlFileExtension) {
		this.xmlFileExtension = xmlFileExtension;
		return this;
	}
}