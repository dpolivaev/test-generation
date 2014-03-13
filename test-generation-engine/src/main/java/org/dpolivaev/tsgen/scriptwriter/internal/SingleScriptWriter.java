package org.dpolivaev.tsgen.scriptwriter.internal;

import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;

public class SingleScriptWriter implements PropertyHandler {

    private XmlWriter xmlWriter;
    private PropertyHandler scriptProducer;
    private static final String[] scriptParts = {
        "scriptprecondition", "ScriptPrecondition",
        "scriptpostprocessing", "ScriptPostprocessing",
    };

	@Override
	public void generationStarted(PropertyContainer propertyContainer) {}
	
    @Override
    public void handlePropertyCombination(PropertyContainer propertyContainer) {
        scriptProducer.handlePropertyCombination(propertyContainer);
    }

    public SingleScriptWriter(PropertyContainer propertyContainer, ScriptConfiguration scriptConfiguration, ResultFactory resultFactory, GoalChecker goalChecker) {
    	xmlWriter = scriptConfiguration.xmlWriter(resultFactory);
        xmlWriter.startDocument();
        XmlTestCaseWriter testCaseProducer = new XmlTestCaseWriter(xmlWriter, goalChecker);
        xmlWriter.beginElement("Script");
        Object scriptValue = propertyContainer.get("script");
        if(scriptValue.equals(SpecialValue.UNDEFINED)){
        	scriptValue = "script";
        	xmlWriter.setAttribute("id", "script");
        }
        Object driverValue = propertyContainer.get("driver");
        if(driverValue.equals(SpecialValue.UNDEFINED))
        	driverValue = scriptValue + "Driver";
        xmlWriter.setAttribute("driver", driverValue.toString());
        testCaseProducer.addAttributes(propertyContainer, "script");
        testCaseProducer.addParts(propertyContainer, scriptParts);
        scriptProducer = testCaseProducer;
    }

	public void generationFinished() {
        xmlWriter.endElement("Script");
        xmlWriter.endDocument();
    }
}
