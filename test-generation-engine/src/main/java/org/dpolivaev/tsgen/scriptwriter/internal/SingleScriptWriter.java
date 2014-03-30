package org.dpolivaev.tsgen.scriptwriter.internal;

import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.scriptwriter.AliasedPropertyAccessor;

public class SingleScriptWriter implements PropertyHandler {

    private static final String SCRIPT_ELEMENT_NAME = "Script";
	XmlWriter xmlWriter;
    private PropertyHandler scriptProducer;
	final private String scriptElementPropertyName;
	@Override
	public void generationStarted(PropertyContainer propertyContainer) {}
	
    @Override
    public void handlePropertyCombination(PropertyContainer propertyContainer) {
        scriptProducer.handlePropertyCombination(propertyContainer);
    }

    public SingleScriptWriter(PropertyContainer propertyContainer, ScriptConfiguration scriptConfiguration, ResultFactory resultFactory, GoalChecker goalChecker) {
    	xmlWriter = scriptConfiguration.xmlWriter(resultFactory);
        xmlWriter.startDocument();
        XmlTestCaseWriter testCaseProducer = scriptConfiguration.testCaseWriter(xmlWriter, goalChecker);
        final AliasedPropertyAccessor aliasedPropertyAccessor = new AliasedPropertyAccessor(propertyContainer);
		scriptElementPropertyName = aliasedPropertyAccessor.getAlias(SCRIPT_ELEMENT_NAME);
        xmlWriter.beginElement(scriptElementPropertyName);
		final String scriptPropertyName = aliasedPropertyAccessor.getAlias(AliasedPropertyAccessor.DEFAULT_SCRIPT_PROPERTY_NAME);

        Object scriptValue = propertyContainer.get(scriptPropertyName);
        if(scriptValue.equals(SpecialValue.UNDEFINED)){
        	scriptValue = scriptPropertyName;
        	xmlWriter.setAttribute("id", scriptPropertyName);
        }
        testCaseProducer.addAttributes(propertyContainer, scriptPropertyName);
        testCaseProducer.addParts(propertyContainer, new AliasedPropertyAccessor(propertyContainer).getScriptParts());
        scriptProducer = testCaseProducer;
    }

	public void generationFinished() {
        xmlWriter.endElement(scriptElementPropertyName);
        xmlWriter.endDocument();
    }
}
