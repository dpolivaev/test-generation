package scriptproducer;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

import ruleengine.PropertyContainer;
import ruleengine.ScriptProducer;

public class XmlScriptProducer implements ScriptProducer{


    private Map<String, XmlSingleScriptProducer> singleScriptProducers;
    private final ResultFactory resultFactory;

    public XmlScriptProducer(ResultFactory resultFactory) {
        this.resultFactory = resultFactory;
        singleScriptProducers = new HashMap<String, XmlSingleScriptProducer>();
    }

     @Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        String scriptName = propertyContainer.<String>get("script");
        if(! singleScriptProducers.containsKey(scriptName)) {
            setSingleScriptProducer(scriptName, newSingleScriptProducer(propertyContainer));
        }
        getSingleScriptProducer(scriptName).makeScriptFor(propertyContainer);
    }

    private XmlSingleScriptProducer newSingleScriptProducer(PropertyContainer propertyContainer) {
        return new XmlSingleScriptProducer(propertyContainer, resultFactory.newResult(propertyContainer.<String>get("script")));
    }

    public void endScripts() {
        for(XmlSingleScriptProducer singleScriptProducer:singleScriptProducers.values())
            singleScriptProducer.endScript();
        
    }

    public Result getResult(String scriptName) {
        return getSingleScriptProducer(scriptName).result();
    }

    private XmlSingleScriptProducer getSingleScriptProducer(String scriptName) {
        return singleScriptProducers.get(scriptName);
    }

    private void setSingleScriptProducer(String scriptName, XmlSingleScriptProducer singleScriptProducer) {
        singleScriptProducers.put(scriptName, singleScriptProducer);
    }

}
