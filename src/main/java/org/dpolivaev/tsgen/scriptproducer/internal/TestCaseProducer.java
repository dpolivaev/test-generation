package org.dpolivaev.tsgen.scriptproducer.internal;

import java.util.List;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.ScriptProducer;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.internal.PropertyAccessor;

public class TestCaseProducer implements ScriptProducer {

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
    	if(coverageTracker != null){
    		for(final CoverageEntry coverageEntry : coverageTracker.firstTimeCoveredGoals()){
    			addCoverageEntry(coverageEntry, true);
    		}
    		for(final CoverageEntry coverageEntry : coverageTracker.repeatedlyCoveredGoals()){
    			addCoverageEntry(coverageEntry, false);
    		}
    	}
	}

	private void addCoverageEntry(final CoverageEntry coverageEntry, boolean firstTime) {
		final int count = coverageTracker.count(coverageEntry);
		xmlWriter.beginElement("Goal");
		xmlWriter.setAttribute("name", coverageEntry.getGoal());
		xmlWriter.setAttribute("count", Integer.toString(count));
		xmlWriter.setAttribute("firstTime", Boolean.toString(firstTime));
		xmlWriter.addTextContent(coverageEntry.getReason().toString());
		xmlWriter.endElement("Goal");
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
        addAttribute(propertyContainer, property, "id");
        addDescription(propertyContainer, property);
        addParameters(propertyContainer, property);
    }

	private void addParameters(PropertyContainer propertyContainer,
			String property) {
		final String prefix = property + '.';
        List<String> sortedProperties = new PropertyAccessor(propertyContainer).sortedPropertiesForPrefix(prefix);
        for(String attributeProperty : sortedProperties){
            Object attributeValue = propertyContainer.get(attributeProperty);
            if(attributeValue != SpecialValue.UNDEFINED){
                String attributeName = attributeProperty.substring(prefix.length());
                xmlWriter.beginElement("Parameter");
                xmlWriter.setAttribute("name", attributeName);
                xmlWriter.addTextContent(attributeValue.toString());
                xmlWriter.endElement("Parameter");
            }
        }
	}

	private void addDescription(PropertyContainer propertyContainer,
			String property) {
		Object value = propertyContainer.get(property+"Description");
        if(!value.equals(SpecialValue.UNDEFINED)) {
        	xmlWriter.beginElement("Description");
        	xmlWriter.addTextContent(value.toString());
        	xmlWriter.endElement("Description");
		}
	}

	private void addAttribute(PropertyContainer propertyContainer, String property, String attributeName) {
		Object value = propertyContainer.get(property);
        if(!value.equals(SpecialValue.UNDEFINED))
            xmlWriter.setAttribute(attributeName, value.toString());
	}

}
