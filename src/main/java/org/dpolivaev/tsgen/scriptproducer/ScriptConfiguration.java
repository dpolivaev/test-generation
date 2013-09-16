package org.dpolivaev.tsgen.scriptproducer;


public class ScriptConfiguration {
	final public OutputConfiguration outputConfiguration;
	final public String scriptName;

	public ScriptConfiguration(OutputConfiguration outputConfiguration, String scriptName) {
		super();
		this.outputConfiguration = outputConfiguration;
		this.scriptName = scriptName;

	}
}