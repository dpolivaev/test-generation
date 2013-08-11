package scriptproducer;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;
import ruleengine.SpecialValues;

public class MultipleScriptsProducer implements ScriptProducer{
    private Source xsltSource;    
    private final Map<String, SingleScriptProducer> singleScriptProducers;
    private final ResultFactory resultFactory;

    public MultipleScriptsProducer(ResultFactory resultFactory) {
        this.resultFactory = resultFactory;
        singleScriptProducers = new HashMap<String, SingleScriptProducer>();
    }

     @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        Object scriptValue = propertyContainer.get("script");
        if(scriptValue == SpecialValues.UNDEFINED)
            return;
        String scriptName = (String) scriptValue;
        if(! singleScriptProducers.containsKey(scriptName)) {
            setSingleScriptProducer(scriptName, newSingleScriptProducer(propertyContainer));
        }
        getSingleScriptProducer(scriptName).makeScriptFor(propertyContainer);
    }

    private SingleScriptProducer newSingleScriptProducer(PropertyContainer propertyContainer) {
        return new SingleScriptProducer(propertyContainer, xsltSource, resultFactory.newResult(propertyContainer.<String>get("script")));
    }

    public void endScripts() {
        for(SingleScriptProducer singleScriptProducer:singleScriptProducers.values())
            singleScriptProducer.endScript();
        
    }

    public Result getResult(String scriptName) {
        return getSingleScriptProducer(scriptName).result();
    }

    private SingleScriptProducer getSingleScriptProducer(String scriptName) {
        return singleScriptProducers.get(scriptName);
    }

    private void setSingleScriptProducer(String scriptName, SingleScriptProducer singleScriptProducer) {
        singleScriptProducers.put(scriptName, singleScriptProducer);
    }

    public Source getXsltSource() {
        return xsltSource;
    }

    public void setXsltSource(Source xsltSource) {
        this.xsltSource = xsltSource;
    }

}
