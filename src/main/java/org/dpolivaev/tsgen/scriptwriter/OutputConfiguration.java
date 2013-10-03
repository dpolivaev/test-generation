package org.dpolivaev.tsgen.scriptwriter;

import java.io.File;

import javax.xml.transform.Source;

public class OutputConfiguration {
	final public Source xsltSource;
	final public String fileExtension;
	final public File directory;
	final public String xmlFileExtension;
	public static final OutputConfiguration OUTPUT_XML = new OutputConfiguration(null, null, null, "xml");

	public OutputConfiguration(File directory, String outputXml, Source xsltSource, String fileExtension) {
		super();
		this.xsltSource = xsltSource;
		this.fileExtension = fileExtension;
		this.directory = directory;
		this.xmlFileExtension = outputXml;
	}

	public ScriptConfiguration forScript(String name){
		final ScriptConfiguration scriptConfiguration = new ScriptConfiguration(this, name);
		return scriptConfiguration;
	}
}