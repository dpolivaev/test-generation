package org.dpolivaev.tsgen.scriptproducer.internal;

import javax.xml.transform.sax.TransformerHandler;

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ScriptProducer;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.scriptproducer.ScriptConfiguration;

public class SingleScriptProducer implements ScriptProducer {

    private XmlWriter xmlWriter;
    private TestCaseProducer testCaseProducer;

    @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        testCaseProducer.makeScriptFor(propertyContainer);
    }

    public SingleScriptProducer(PropertyContainer propertyContainer, ScriptConfiguration scriptConfiguration, ResultFactory resultFactory, CoverageTracker coverageTracker) {
        TransformerHandler handler = new HandlerFactory(resultFactory).newHandler(scriptConfiguration);
        xmlWriter = new XmlWriterUsingTransformerHandler(handler);
        xmlWriter.startDocument();
        testCaseProducer = new TestCaseProducer(xmlWriter, coverageTracker);
        xmlWriter.beginElement("Script");
        Object scriptValue = propertyContainer.get("script");
        if(scriptValue.equals(SpecialValue.UNDEFINED))
        	xmlWriter.setAttribute("id", "script");
        testCaseProducer.addAttributes(propertyContainer, "script");
    }
    
    public SingleScriptProducer(PropertyContainer propertyContainer, ScriptConfiguration scriptConfiguration, ResultFactory resultFactory) {
        this(propertyContainer, scriptConfiguration, resultFactory, null);
    }

    public void endScript() {
        xmlWriter.endElement("Script");
        xmlWriter.endDocument();
    }
}
