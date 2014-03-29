package org.dpolivaev.tsgen.scriptwriter.internal;

import java.io.File;

import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.scriptwriter.AliasedPropertyAccessor;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.xml.sax.ContentHandler;


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

	XmlWriter xmlWriter(ResultFactory resultFactory) {
		ContentHandler handler = new HandlerFactory(resultFactory).newHandler(this);
	    return new SaxXmlWriter(handler);
	}

	XmlTestCaseWriter testCaseWriter(XmlWriter xmlWriter, GoalChecker goalChecker) {
		return new XmlTestCaseWriter(xmlWriter, goalChecker);
	}

	String[] getScriptParts(PropertyContainer propertyContainer) {
		return new AliasedPropertyAccessor(propertyContainer).getScriptParts();
	}

	String[] getTestCaseParts(PropertyContainer propertyContainer) {
		return new AliasedPropertyAccessor(propertyContainer).getTestCaseParts();
	}
}