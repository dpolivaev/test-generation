package org.dpolivaev.tsgen.scriptwriter;

import java.io.File;

import javax.xml.transform.Source;

public class OutputConfiguration {
	final public Source xsltSource;
	final public String fileExtension;
	final public File directory;
	final public boolean outputXml;
	public static final OutputConfiguration OUTPUT_XML = new OutputConfiguration(null, null, "xml", false);

	public OutputConfiguration(Source xsltSource, File directory, String fileExtension, boolean outputXml) {
		super();
		this.xsltSource = xsltSource;
		this.fileExtension = fileExtension;
		this.directory = directory;
		this.outputXml = outputXml;
	}

	public ScriptConfiguration forScript(String name){
		final ScriptConfiguration scriptConfiguration = new ScriptConfiguration(this, name);
		return scriptConfiguration;
	}
}