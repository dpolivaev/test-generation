package org.dpolivaev.tsgen.scriptwriter;

import java.io.File;


public class ScriptConfiguration {
	final public OutputConfiguration outputConfiguration;
	final public String scriptName;

	public ScriptConfiguration(OutputConfiguration outputConfiguration, String scriptName) {
		super();
		this.outputConfiguration = outputConfiguration;
		this.scriptName = scriptName;
	}
	
	public File xmlFile() {
		final String xmlName = scriptName != null ? scriptName + "." + outputConfiguration.getXmlFileExtension() : outputConfiguration.getXmlFileExtension();
		return new File(outputConfiguration.getDirectory(), xmlName);
	}

	public File outputFile() {
		final String xmlName = scriptName != null ? scriptName + "." + outputConfiguration.getFileExtension() : outputConfiguration.getFileExtension();
		return new File(outputConfiguration.getDirectory(), xmlName);
	}
}