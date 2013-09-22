package org.dpolivaev.tsgen.scriptwriter;

import java.io.File;

import javax.xml.transform.Source;

public class OutputConfiguration {
	final public Source xsltSource;
	final public String fileExtension;
	final public File directory;
	public static final OutputConfiguration OUTPUT_XML = new OutputConfiguration(null, "xml");

	public OutputConfiguration(Source xsltSource, String fileExtension) {
		this(xsltSource, null, fileExtension);
	}
	
	public OutputConfiguration(Source xsltSource, File directory, String fileExtension) {
		super();
		this.xsltSource = xsltSource;
		this.fileExtension = fileExtension;
		this.directory = directory;
	}

	public ScriptConfiguration forScript(String name){
		final ScriptConfiguration scriptConfiguration = new ScriptConfiguration(this, name);
		return scriptConfiguration;
	}
}