package org.dpolivaev.tsgen.scriptwriter.internal;

import java.util.HashMap;
import java.util.Map;

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ScriptWriter;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;

public class MultipleScriptsWriter implements ScriptWriter{
    private final Map<String, SingleScriptWriter> singleScriptProducers;
    private final ResultFactory resultFactory;
	private OutputConfiguration outputConfiguration;

    public OutputConfiguration getOutputConfiguration() {
		return outputConfiguration;
	}

	public void setOutputConfiguration(OutputConfiguration outputConfiguration) {
		this.outputConfiguration = outputConfiguration;
	}

	public MultipleScriptsWriter(ResultFactory resultFactory) {
        this.resultFactory = resultFactory;
        singleScriptProducers = new HashMap<String, SingleScriptWriter>();
        outputConfiguration = OutputConfiguration.OUTPUT_XML;
        
    }

     @Override
    public void createScriptFor(PropertyContainer propertyContainer, CoverageTracker coverage) {
        Object scriptValue = propertyContainer.get("script");
        String scriptName;
        if(scriptValue == SpecialValue.UNDEFINED)
        	scriptName = "script";
        else
        	scriptName =(String) scriptValue;
        if(! singleScriptProducers.containsKey(scriptName)) {
            setSingleScriptProducer(scriptName, newSingleScriptProducer(scriptName, propertyContainer));
        }
        getSingleScriptProducer(scriptName).createScriptFor(propertyContainer, null);
    }

    private SingleScriptWriter newSingleScriptProducer(String scriptName, PropertyContainer propertyContainer) {
        return new SingleScriptWriter(propertyContainer, outputConfiguration.forScript(scriptName), 
        		resultFactory);
    }

    public void endScripts() {
        for(SingleScriptWriter singleScriptProducer:singleScriptProducers.values())
            singleScriptProducer.endScript();
        
    }

    private SingleScriptWriter getSingleScriptProducer(String scriptName) {
        return singleScriptProducers.get(scriptName);
    }

    private void setSingleScriptProducer(String scriptName, SingleScriptWriter singleScriptProducer) {
        singleScriptProducers.put(scriptName, singleScriptProducer);
    }
}
