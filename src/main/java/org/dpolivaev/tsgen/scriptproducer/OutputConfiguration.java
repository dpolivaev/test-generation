package org.dpolivaev.tsgen.scriptproducer;

import javax.xml.transform.Source;

public class OutputConfiguration {
	final public Source xsltSource;
	final public String fileExtension;
	public static final OutputConfiguration OUTPUT_XML = new OutputConfiguration(null, "xml");

	public OutputConfiguration(Source xsltSource, String fileExtension) {
		super();
		this.xsltSource = xsltSource;
		this.fileExtension = fileExtension;
	}

	public ScriptConfiguration forScript(String name){
		final ScriptConfiguration scriptConfiguration = new ScriptConfiguration(this, name);
		return scriptConfiguration;
	}
}