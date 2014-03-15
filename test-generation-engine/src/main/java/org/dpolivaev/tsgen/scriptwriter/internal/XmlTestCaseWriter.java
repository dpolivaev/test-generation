package org.dpolivaev.tsgen.scriptwriter.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dpolivaev.tsgen.coverage.CheckList;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.internal.PropertyAccessor;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.utils.internal.StringWithNumbersComparator;

public class XmlTestCaseWriter implements PropertyHandler {

	private static final String TESTCASE_PROPERTY = "testcase";
    private static final String TESTCASE_ELEMENT = "TestCase";
    
    private final XmlWriter xmlWriter;
	private GoalChecker goalChecker;
	final private OutputConfiguration outputConfiguration;

    public XmlTestCaseWriter(XmlWriter xmlWriter, OutputConfiguration outputConfiguration, GoalChecker goalChecker) {
    	this.xmlWriter = xmlWriter;
		this.goalChecker = goalChecker;
		this.outputConfiguration = outputConfiguration;
	}

	@Override
    public void handlePropertyCombination(PropertyContainer propertyContainer) {
        xmlWriter.beginElement(TESTCASE_ELEMENT);
        addAttribute(propertyContainer, TESTCASE_PROPERTY, "id");
		addParameters(propertyContainer, TESTCASE_PROPERTY);
        addParts(propertyContainer, outputConfiguration.getTestCaseParts());
        addDescription(propertyContainer, TESTCASE_PROPERTY);
        for(Goal goal : goalChecker.goals())
        	addCoverage(goal.name(), goal.checkList());
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
		final Collection<CoverageEntry> firstTimeCoveredGoals = sort(checkList.firstTimeCoveredEntries());
		final Collection<CoverageEntry> repeatedlyCoveredGoals = sort(checkList.repeatedlyCoveredEntries());
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

	public Collection<CoverageEntry> sort(Collection<CoverageEntry> collection) {
		ArrayList<CoverageEntry> list = new ArrayList<>(collection);
		Collections.sort(list, new Comparator<CoverageEntry>(){
			@Override
			public int compare(CoverageEntry entry1, CoverageEntry entry2) {
					return StringWithNumbersComparator.compare(entry1.getName(), entry2.getName());
			}});
		return list;
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
            addPart(propertyContainer, property, element);
        for(int i = 1; i <= 99; i++)
            if(propertyContainer.containsPropertyValue(property + '#' + i))
                addPart(propertyContainer, property + '#' + i, element);
    }

    private void addPart(PropertyContainer propertyContainer, String property, String element) {
        xmlWriter.beginElement(element);
        addPartAttributes(propertyContainer, property);
        xmlWriter.endElement(element);
    }

     public void addPartAttributes(PropertyContainer propertyContainer, String property) {
        String value = propertyContainer.get(property).toString().trim();
        final PartValueParser partValueParser = new PartValueParser(value);
        xmlWriter.setAttribute("id", partValueParser.getCalledMethod());
        addDescription(propertyContainer, property);
        if(partValueParser.isArgumentListFound()){
        	addPartArguments(propertyContainer, partValueParser.getArgumentList());
        }
    }

     private void addPartArguments(PropertyContainer propertyContainer,
    		 String[] arguments ) {
		for(String argumentName:arguments){
			Object argumentValue = propertyContainer.get(argumentName);
			if(!argumentValue.equals(SpecialValue.UNDEFINED))
				addParameterElement(argumentName, argumentValue.toString());
		}
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
        	if(! attributeProperty.equals(prefix + "description") && ! isPart(attributeProperty)){
        		Object attributeValue = propertyContainer.get(attributeProperty);
        		if(attributeValue != SpecialValue.UNDEFINED){
        			String attributeName = attributeProperty.substring(prefix.length());
        			addParameterElement(attributeName, attributeValue);
        		}
        	}
        }
	}

	static private Pattern PART_PATERN = Pattern.compile("(.*?)(?:#\\d{1,2})?"); 
	private boolean isPart(String attributeProperty) {
		final Matcher matcher = PART_PATERN.matcher(attributeProperty);
		return matcher.matches() && outputConfiguration.isPart(matcher.group(1));
	}

	private void addParameterElement(String attributeName, Object attributeValue) {
		xmlWriter.beginElement("Parameter");
		xmlWriter.setAttribute("name", attributeName);
		xmlWriter.addTextContent(attributeValue.toString());
		xmlWriter.endElement("Parameter");
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

	private void addAttribute(PropertyContainer propertyContainer, String property, String attributeName) {
		Object value = propertyContainer.get(property);
        if(!value.equals(SpecialValue.UNDEFINED))
            xmlWriter.setAttribute(attributeName, value.toString());
	}
}
