package org.dpolivaev.tsgen.scriptwriter.internal;

import java.util.HashMap;
import java.util.Map;

import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;

public class MultipleScriptsWriter implements PropertyHandler{
    private final Map<String, SingleScriptWriter> singleScriptProducers;
    private final ResultFactory resultFactory;
	private OutputConfiguration outputConfiguration;
	final private GoalChecker goalChecker;

    public OutputConfiguration getOutputConfiguration() {
		return outputConfiguration;
	}

	public void setOutputConfiguration(OutputConfiguration outputConfiguration) {
		this.outputConfiguration = outputConfiguration;
	}

	public MultipleScriptsWriter(ResultFactory resultFactory, GoalChecker goalChecker) {
        this.resultFactory = resultFactory;
		this.goalChecker = goalChecker;
        singleScriptProducers = new HashMap<String, SingleScriptWriter>();
        outputConfiguration = OutputConfiguration.OUTPUT_XML;
        
    }

     @Override
    public void handlePropertyCombination(PropertyContainer propertyContainer) {
        Object scriptValue = propertyContainer.get("script");
        String scriptName;
        if(scriptValue == SpecialValue.UNDEFINED)
        	scriptName = "script";
        else
        	scriptName =(String) scriptValue;
        if(! singleScriptProducers.containsKey(scriptName)) {
            setSingleScriptProducer(scriptName, newSingleScriptProducer(scriptName, propertyContainer));
        }
        getSingleScriptProducer(scriptName).handlePropertyCombination(propertyContainer);
    }

    private SingleScriptWriter newSingleScriptProducer(String scriptName, PropertyContainer propertyContainer) {
        return new SingleScriptWriter(propertyContainer, outputConfiguration.forScript(scriptName), 
        		resultFactory, goalChecker);
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
