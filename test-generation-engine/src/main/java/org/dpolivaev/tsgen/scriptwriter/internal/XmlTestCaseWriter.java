package org.dpolivaev.tsgen.scriptwriter.internal;

import java.util.List;
import java.util.Set;

import org.dpolivaev.tsgen.coverage.CheckList;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.internal.PropertyAccessor;

public class XmlTestCaseWriter implements PropertyHandler {

	private static final String TESTCASE_PROPERTY = "testcase";
    private static final String TESTCASE_ELEMENT = "TestCase";
    
    private static final String[] testCaseParts = {
        "pre", "Precondition",
        "foc", "Focus",
        "veri", "Verification",
        "post", "Postprocessing",
    };
    private final XmlWriter xmlWriter;
	private GoalChecker goalChecker;

    public XmlTestCaseWriter(XmlWriter xmlWriter, GoalChecker goalChecker) {
    	this.xmlWriter = xmlWriter;
		this.goalChecker = goalChecker;
	}

	@Override
    public void handlePropertyCombination(PropertyContainer propertyContainer) {
        xmlWriter.beginElement(TESTCASE_ELEMENT);
        addAttributes(propertyContainer, TESTCASE_PROPERTY);
        for(Goal goal : goalChecker.goals())
        	addCoverage(goal.name(), goal.checkList());
        addParts(propertyContainer, testCaseParts);
        xmlWriter.endElement(TESTCASE_ELEMENT);

	}
	
	@Override
	public void generationStarted(PropertyContainer propertyContainer) {}
	
	@Override
	public void generationFinished() {}

	public void addParts(PropertyContainer propertyContainer, String[] parts) {
		for(int i = 0; i < parts.length; i+=2)
            addParts(propertyContainer, parts[i], parts[i+1]);
	}

	private void addCoverage(String name, CheckList checkList) {
		final Set<CoverageEntry> firstTimeCoveredGoals = checkList.firstTimeCoveredEntries();
		final Set<CoverageEntry> repeatedlyCoveredGoals = checkList.repeatedlyCoveredEntries();
		if(! (firstTimeCoveredGoals.isEmpty() && repeatedlyCoveredGoals.isEmpty())){
			xmlWriter.beginElement("Goal");
			xmlWriter.setAttribute("name", name);
			for(final CoverageEntry coverageEntry : firstTimeCoveredGoals){
				final int count = checkList.countReached(coverageEntry);
				addCoverageEntry(coverageEntry, count, true);
			}
			for(final CoverageEntry coverageEntry : repeatedlyCoveredGoals){
				final int count = checkList.countReached(coverageEntry);
				addCoverageEntry(coverageEntry, count, false);
			}
			xmlWriter.endElement("Goal");
		}
	}

	private void addCoverageEntry(final CoverageEntry coverageEntry, int count, boolean firstTime) {
		String reason = coverageEntry.getReason();
		if(reason != null){
			xmlWriter.beginElement("Item");
			xmlWriter.setAttribute("name", coverageEntry.getName());
			xmlWriter.setAttribute("count", Integer.toString(count));
			xmlWriter.setAttribute("firstTime", Boolean.toString(firstTime));
			xmlWriter.addTextContent(reason.toString());
			xmlWriter.endElement("Item");
		}
	}

	private void addParts(PropertyContainer propertyContainer, String property, String element) {
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
