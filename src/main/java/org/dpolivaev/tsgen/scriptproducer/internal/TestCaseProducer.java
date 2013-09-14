package org.dpolivaev.tsgen.scriptproducer.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ScriptProducer;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;

public class TestCaseProducer implements ScriptProducer {

    private static final String REQUIREMENT_PREFIX = "requirement.";
	private static final String TESTCASE_PROPERTY = "testcase";
    private static final String TESTCASE_ELEMENT = "TestCase";
    
    private static final String[] optionalElements = {
        "pre", "Precondition",
        "state", "EnterState",
        "preInState", "PreconditionInState",
        "foc", "Focus",
        "veriInState", "VerificationInState",
        "stateAfter", "CheckState",
        "veri", "Verification",
        "post", "Postprocessing",
    };
    private final XmlWriter xmlWriter;
	final private CoverageTracker coverageTracker;

    public TestCaseProducer(XmlWriter xmlWriter,
			CoverageTracker coverageTracker) {
    	this.xmlWriter = xmlWriter;
		this.coverageTracker = coverageTracker;
	}

	@Override
    public void makeScriptFor(PropertyContainer propertyContainer) {
        xmlWriter.beginElement(TESTCASE_ELEMENT);
        addAttributes(propertyContainer, TESTCASE_PROPERTY);
        addCoverage(propertyContainer);
        for(int i = 0; i < optionalElements.length; i+=2)
            addOptionalElement(propertyContainer, optionalElements[i], optionalElements[i+1]);
        xmlWriter.endElement(TESTCASE_ELEMENT);

    }

    private void addCoverage(PropertyContainer propertyContainer) {
    	List<String> sortedRequirementProperties = sortedPropertiesForPrefix(REQUIREMENT_PREFIX, propertyContainer);
    	for(String propertyName :  sortedRequirementProperties){
    		final Object value = propertyContainer.get(propertyName);
    		if(value == SpecialValue.UNDEFINED)
    			continue;
    		@SuppressWarnings("unchecked")
			List<Object> coverageItems = (List<Object>) value;
    		String requirementId = propertyName.substring(REQUIREMENT_PREFIX.length());
    		for(Object item : coverageItems){
    			String description = item.toString();
    			final CoverageEntry coverageEntry = new CoverageEntry(requirementId, description);
    			coverageTracker.add(coverageEntry);
    			final int count = coverageTracker.count(coverageEntry);
    			xmlWriter.beginElement("Requirement");
    			xmlWriter.setAttribute("id", requirementId);
    			xmlWriter.setAttribute("count", Integer.toString(count));
    			xmlWriter.addTextContent(description);
    			xmlWriter.endElement("Requirement");
    		}
    	}
		
	}

	private void addOptionalElement(PropertyContainer propertyContainer, String property, String element) {
        if(propertyContainer.containsPropertyValue(property))
            addElement(propertyContainer, property, element);
        for(int i = 1; i <= 9; i++)
            if(propertyContainer.containsPropertyValue(property + i))
                addElement(propertyContainer, property + i, element);
    }

    private void addElement(PropertyContainer propertyContainer, String property, String element) {
        xmlWriter.beginElement(element);
        addAttributes(propertyContainer, property);
        xmlWriter.endElement(element);
    }

    public void addAttributes(PropertyContainer propertyContainer, String property) {
        addSelf(propertyContainer, property);
        addDescription(propertyContainer, property);
        addParameters(propertyContainer, property);
    }

	private void addParameters(PropertyContainer propertyContainer,
			String property) {
		final String prefix = property + '.';
        List<String> sortedProperties = sortedPropertiesForPrefix(prefix, propertyContainer);
        for(String attributeProperty : sortedProperties){
            Object attributeValue = propertyContainer.get(attributeProperty);
            if(attributeValue != SpecialValue.UNDEFINED){
                String attributeName = attributeProperty.substring(prefix.length());
                if(attributeName.equals("self"))
                	xmlWriter.setAttribute("self", attributeValue.toString());
                else if(! attributeName.equals("description")){
                	xmlWriter.beginElement("Parameter");
                	xmlWriter.setAttribute("name", attributeName);
                	xmlWriter.addTextContent(attributeValue.toString());
                	xmlWriter.endElement("Parameter");
                }
            }
        }
	}

	private void addDescription(PropertyContainer propertyContainer,
			String property) {
		Object value = propertyContainer.get(property+".description");
        if(!value.equals(SpecialValue.UNDEFINED)) {
        	xmlWriter.beginElement("Description");
        	xmlWriter.addTextContent(value.toString());
        	xmlWriter.endElement("Description");
		}
	}

	private void addSelf(PropertyContainer propertyContainer, String property) {
		Object value = propertyContainer.get(property);
        if(!value.equals(SpecialValue.UNDEFINED))
            xmlWriter.setAttribute("self", value.toString());
        else {
            value = propertyContainer.get(property+".self");
            if(!value.equals(SpecialValue.UNDEFINED))
                xmlWriter.setAttribute("self", value.toString());
        }
	}

	private List<String> sortedPropertiesForPrefix(String prefix,
			PropertyContainer propertyContainer) {
		Set<String> availableProperties = propertyContainer.availableProperties(prefix);
        List<String> sortedProperties = new ArrayList<>(availableProperties);
        Collections.sort(sortedProperties);
		return sortedProperties;
	}
}
