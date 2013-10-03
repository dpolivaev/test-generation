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
		final String xmlName = scriptName == null ? outputConfiguration.xmlFileExtension : scriptName + "." + outputConfiguration.xmlFileExtension;
		return new File(outputConfiguration.directory, xmlName);
	}

	public File outputFile() {
		final String xmlName = scriptName == null ? outputConfiguration.fileExtension : scriptName + "." + outputConfiguration.fileExtension;
		return new File(outputConfiguration.directory, xmlName);
	}
}