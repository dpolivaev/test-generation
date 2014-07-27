package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import java.util.HashMap;
import java.util.Map;

import org.dpolivaev.testgeneration.engine.coverage.GoalChecker;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor;
import org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration;

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
        outputConfiguration = OutputConfiguration.OUTPUT_NOTHING;
        
    }

     @Override
    public void handlePropertyCombination(PropertyContainer propertyContainer) {
    	
        final AliasedPropertyAccessor aliasedPropertyAccessor = new AliasedPropertyAccessor(propertyContainer);
		final String scriptPropertyName = aliasedPropertyAccessor.getAlias(AliasedPropertyAccessor.DEFAULT_SCRIPT_PROPERTY_NAME)+ ".name";
		Object scriptValue = propertyContainer.get(scriptPropertyName);
        String scriptName;
        if(scriptValue == SpecialValue.UNDEFINED)
        	scriptName = AliasedPropertyAccessor.DEFAULT_SCRIPT_PROPERTY_NAME;
        else
        	scriptName =(String) scriptValue;
        if(! singleScriptProducers.containsKey(scriptName)) {
            setSingleScriptProducer(scriptName, newSingleScriptProducer(scriptName, propertyContainer));
        }
        getSingleScriptProducer(scriptName).handlePropertyCombination(propertyContainer);
    }

    private SingleScriptWriter newSingleScriptProducer(String scriptName, PropertyContainer propertyContainer) {
        return new SingleScriptWriter(propertyContainer, new ScriptConfiguration(outputConfiguration, scriptName), 
        		resultFactory, goalChecker);
    }

	@Override
	public void generationStarted(PropertyContainer propertyContainer) {}
	
	@Override
	public void generationFinished() {
        for(SingleScriptWriter singleScriptProducer:singleScriptProducers.values())
            singleScriptProducer.generationFinished();
        
    }

    private SingleScriptWriter getSingleScriptProducer(String scriptName) {
        return singleScriptProducers.get(scriptName);
    }

    private void setSingleScriptProducer(String scriptName, SingleScriptWriter singleScriptProducer) {
        singleScriptProducers.put(scriptName, singleScriptProducer);
    }
}
