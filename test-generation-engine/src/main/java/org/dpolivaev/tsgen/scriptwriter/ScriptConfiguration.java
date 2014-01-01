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
		final String xmlName = scriptName != null ? scriptName + "." + outputConfiguration.getXmlExtension() : outputConfiguration.getXmlExtension();
		return new File(outputConfiguration.getXmlDirectory(), xmlName);
	}

	public File outputFile() {
		final String xmlName = scriptName != null ? scriptName + "." + outputConfiguration.getFileExtension() : outputConfiguration.getFileExtension();
		return new File(outputConfiguration.getFileDirectory(), xmlName);
	}

	public boolean isFileValid() {
		return outputConfiguration.isFileValid();
	}
}