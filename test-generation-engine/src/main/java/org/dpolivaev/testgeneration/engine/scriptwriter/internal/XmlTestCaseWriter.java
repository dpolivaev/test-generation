package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dpolivaev.testgeneration.engine.coverage.CheckList;
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.Goal;
import org.dpolivaev.testgeneration.engine.coverage.GoalChecker;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.PropertyAccessor;
import org.dpolivaev.testgeneration.engine.scriptwriter.AliasedPropertyAccessor;
import org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration;
import org.dpolivaev.testgeneration.engine.utils.internal.StringWithNumbersComparator;

public class XmlTestCaseWriter implements PropertyHandler {

	private static final String TESTCASE_ELEMENT = "TestCase";
    
    private XmlWriter xmlWriter;
	private GoalChecker goalChecker;

    public XmlTestCaseWriter(XmlWriter xmlWriter, GoalChecker goalChecker) {
    	this.xmlWriter = xmlWriter;
		this.goalChecker = goalChecker;
	}

	@Override
    public void handlePropertyCombination(PropertyContainer propertyContainer) {
		final AliasedPropertyAccessor aliasedPropertyAccessor = new AliasedPropertyAccessor(propertyContainer);
        final String testcaseElementAlias = aliasedPropertyAccessor.getAlias(TESTCASE_ELEMENT);
		xmlWriter.beginElement(testcaseElementAlias);
		calculateParts(propertyContainer, aliasedPropertyAccessor);
        final String testcaseProperty = aliasedPropertyAccessor.getAlias(AliasedPropertyAccessor.DEFAULT_TESTCASE_PROPERTY);
		addAttribute(propertyContainer, testcaseProperty + ".name", "name");
		addParameters(propertyContainer, testcaseProperty);
		addParts(propertyContainer, aliasedPropertyAccessor.getTestCaseParts());
        addDescription(propertyContainer, testcaseProperty);
        for(Goal goal : goalChecker.goals())
        	addCoverage(goal.name(), goal.checkList());
        xmlWriter.endElement(testcaseElementAlias);

	}

	private void calculateParts(PropertyContainer propertyContainer,
			final AliasedPropertyAccessor aliasedPropertyAccessor) {
		final XmlWriter xmlWriter = this.xmlWriter;
		this.xmlWriter = null;
		try{
			addParts(propertyContainer, aliasedPropertyAccessor.getTestCaseParts());
		}
		finally{
			this.xmlWriter = xmlWriter;
		}
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
        if(propertyContainer.isPropertyAvailable(property))
            addPart(propertyContainer, property, element);
        for(int i = 1; i <= OutputConfiguration.TEST_PART_NUMBER_MAXIMUM; i++)
            if(propertyContainer.isPropertyAvailable(property + '#' + i))
                addPart(propertyContainer, property + '#' + i, element);
    }

    private void addPart(PropertyContainer propertyContainer, String property, String element) {
    	if(! propertyContainer.get(property).equals(SpecialValue.UNDEFINED)){
		if (xmlWriter != null) xmlWriter.beginElement(element);
    		addPartAttributes(propertyContainer, property);
		if (xmlWriter != null) xmlWriter.endElement(element);
    	}
    }

     public void addPartAttributes(PropertyContainer propertyContainer, String property) {
        String value = propertyContainer.get(property).toString().trim();
        final PartValueParser partValueParser = new PartValueParser(propertyContainer, value);
        if (xmlWriter != null) {
		xmlWriter.setAttribute("step", partValueParser.getCalledMethod());
		addDescription(propertyContainer, property);
        }
        addPartComment(propertyContainer, partValueParser.getComment());
        addPartArguments(propertyContainer, partValueParser.getArgumentList());
    }

     private void addPartComment(PropertyContainer propertyContainer, String comment) {
	 if(xmlWriter != null && ! comment.isEmpty()){
			xmlWriter.beginElement("Comment");
			xmlWriter.addTextContent(comment);
			xmlWriter.endElement("Comment");
	 }
	}

	private void addPartArguments(PropertyContainer propertyContainer,
    		 String[] arguments ) {
		for(String propertyName:arguments){
			String name;
			Object value;
			String type;
			String separator = ":";
			if(propertyName.startsWith(separator)){
				name = propertyName.substring(1);
				value = propertyContainer.get(name);
				type = value.getClass().getName();
			}
			else {
				type = "";
				if(propertyName.contains(separator)){
					int separatorPosition = propertyName.indexOf(':');
					name = propertyName.substring(0, separatorPosition).trim();
					value = propertyName.substring(separatorPosition + 1).trim();
				}
				else {
					value = name = propertyName;
				}
			}
			if(xmlWriter != null)
				addParameterElement(type, name, value);

		}
	}

    public void addAttributes(PropertyContainer propertyContainer, String property) {
        addAttribute(propertyContainer, property + ".name", "name");
        addDescription(propertyContainer, property);
        addParameters(propertyContainer, property);
    }

	private void addParameters(PropertyContainer propertyContainer,
			String property) {
		final AliasedPropertyAccessor aliasedPropertyAccessor = new AliasedPropertyAccessor(propertyContainer);
		final String prefix = property + '.';
        List<String> sortedProperties = new PropertyAccessor(propertyContainer).sortedPropertiesForPrefix(prefix);
        for(String attributeProperty : sortedProperties){
        	if(! attributeProperty.equals(aliasedPropertyAccessor.getAlias(property + ".description")) 
        			&& ! attributeProperty.equals(aliasedPropertyAccessor.getAlias(property + ".name"))
        			&& isParameter(propertyContainer, attributeProperty)){
        		Object attributeValue = propertyContainer.get(attributeProperty);
			if(attributeValue != SpecialValue.UNDEFINED && xmlWriter != null){
        			String attributeName = attributeProperty.substring(prefix.length());
        			addParameterElement(attributeName, attributeValue);
        		}
        	}
        }
	}

	static private Pattern PART_PATERN = Pattern.compile("(.*?)(?:#\\d{1,2})?"); 
	private boolean isParameter(PropertyContainer propertyContainer, String attributeProperty) {
		final Matcher matcher = PART_PATERN.matcher(attributeProperty);
		final AliasedPropertyAccessor aliasedPropertyAccessor = new AliasedPropertyAccessor(propertyContainer);
		return ! aliasedPropertyAccessor.isAlias(attributeProperty) &&  ! (matcher.matches() && aliasedPropertyAccessor.isPart(matcher.group(1)));
	}

	private void addParameterElement(String attributeName, Object attributeValue) {
		String type = attributeValue.getClass().getName();
		addParameterElement(type, attributeName, attributeValue);
	}

	private void addParameterElement(String type, String name, Object value) {
		xmlWriter.beginElement("Parameter");
		xmlWriter.setAttribute("type", type);
		xmlWriter.setAttribute("name", name);
		xmlWriter.addTextContent(value.toString());
		xmlWriter.endElement("Parameter");
	}

	private void addDescription(PropertyContainer propertyContainer,
			String property) {
		final AliasedPropertyAccessor aliasedPropertyAccessor = new AliasedPropertyAccessor(propertyContainer);
		final String descriptionPropertyName = aliasedPropertyAccessor.getAlias(property+".description");
		Object value = propertyContainer.get(descriptionPropertyName);
        if(!value.equals(SpecialValue.UNDEFINED)) {
			final String descriptionElementName = aliasedPropertyAccessor.getAlias("Description");
			xmlWriter.beginElement(descriptionElementName);
        	xmlWriter.addTextContent(value.toString());
        	xmlWriter.endElement(descriptionElementName);
		}
	}

	private void addAttribute(PropertyContainer propertyContainer, String property, String attributeName) {
		Object value = propertyContainer.get(property);
        if(!value.equals(SpecialValue.UNDEFINED))
            xmlWriter.setAttribute(attributeName, value.toString());
	}
}
