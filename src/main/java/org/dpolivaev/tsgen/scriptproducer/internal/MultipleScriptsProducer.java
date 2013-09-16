package org.dpolivaev.tsgen.scriptproducer.internal;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ScriptProducer;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.scriptproducer.OutputConfiguration;
import org.dpolivaev.tsgen.scriptproducer.ScriptConfiguration;

public class MultipleScriptsProducer implements ScriptProducer{
    private final Map<String, SingleScriptProducer> singleScriptProducers;
    private final ResultFactory resultFactory;
	private final CoverageTracker coverageTracker;
	private OutputConfiguration outputConfiguration;

    public OutputConfiguration getOutputConfiguration() {
		return outputConfiguration;
	}

	public void setOutputConfiguration(OutputConfiguration outputConfiguration) {
		this.outputConfiguration = outputConfiguration;
	}

	public MultipleScriptsProducer(ResultFactory resultFactory, CoverageTracker coverageTracker) {
        this.resultFactory = resultFactory;
		this.coverageTracker = coverageTracker;
        singleScriptProducers = new HashMap<String, SingleScriptProducer>();
        outputConfiguration = OutputConfiguration.OUTPUT_XML;
        
    }

     @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        Object scriptValue = propertyContainer.get("script");
        String scriptName;
        if(scriptValue == SpecialValue.UNDEFINED)
        	scriptName = "script";
        else
        	scriptName =(String) scriptValue;
        if(! singleScriptProducers.containsKey(scriptName)) {
            setSingleScriptProducer(scriptName, newSingleScriptProducer(scriptName, propertyContainer));
        }
        getSingleScriptProducer(scriptName).makeScriptFor(propertyContainer);
    }

    private SingleScriptProducer newSingleScriptProducer(String scriptName, PropertyContainer propertyContainer) {
        return new SingleScriptProducer(propertyContainer, outputConfiguration.forScript(scriptName), 
        		resultFactory, coverageTracker);
    }

    public void endScripts() {
        for(SingleScriptProducer singleScriptProducer:singleScriptProducers.values())
            singleScriptProducer.endScript();
        
    }

    private SingleScriptProducer getSingleScriptProducer(String scriptName) {
        return singleScriptProducers.get(scriptName);
    }

    private void setSingleScriptProducer(String scriptName, SingleScriptProducer singleScriptProducer) {
        singleScriptProducers.put(scriptName, singleScriptProducer);
    }
}
