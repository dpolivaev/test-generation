package org.dpolivaev.tsgen.scriptwriter.internal;

import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;
import org.xml.sax.ContentHandler;

public class SingleScriptWriter implements PropertyHandler {

    private XmlWriter xmlWriter;
    private PropertyHandler scriptProducer;

    @Override
    public void handlePropertyCombination(PropertyContainer propertyContainer) {
        scriptProducer.handlePropertyCombination(propertyContainer);
    }

    public SingleScriptWriter(PropertyContainer propertyContainer, ScriptConfiguration scriptConfiguration, ResultFactory resultFactory, GoalChecker goalChecker) {
        ContentHandler handler = new HandlerFactory(resultFactory).newHandler(scriptConfiguration);
        xmlWriter = new SaxXmlWriter(handler);
        xmlWriter.startDocument();
        XmlTestCaseWriter testCaseProducer = new XmlTestCaseWriter(xmlWriter, goalChecker);
        xmlWriter.beginElement("Script");
        Object scriptValue = propertyContainer.get("script");
        if(scriptValue.equals(SpecialValue.UNDEFINED))
        	xmlWriter.setAttribute("id", "script");
        testCaseProducer.addAttributes(propertyContainer, "script");
        scriptProducer = testCaseProducer;
    }
    
     public void endScript() {
        xmlWriter.endElement("Script");
        xmlWriter.endDocument();
    }
}
