package org.dpolivaev.tsgen.scriptwriter.internal;

import javax.xml.transform.sax.TransformerHandler;

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ScriptWriter;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;

public class SingleScriptWriter implements ScriptWriter {

    private XmlWriter xmlWriter;
    private ScriptWriter scriptProducer;

    @Override
    public void createScriptFor(PropertyContainer propertyContainer, CoverageTracker coverage) {
        scriptProducer.createScriptFor(propertyContainer, null);
    }

    public SingleScriptWriter(PropertyContainer propertyContainer, ScriptConfiguration scriptConfiguration, ResultFactory resultFactory) {
        TransformerHandler handler = new HandlerFactory(resultFactory).newHandler(scriptConfiguration);
        xmlWriter = new XmlWriterUsingTransformerHandler(handler);
        xmlWriter.startDocument();
        XmlTestCaseWriter testCaseProducer = new XmlTestCaseWriter(xmlWriter);
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
