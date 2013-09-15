package org.dpolivaev.tsgen.ruleengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.dpolivaev.tsgen.ruleengine.internal.Assignment;

public class AssignmentFormatter {
    private String propertySeparator;

    public String getPropertySeparator() {
        return propertySeparator;
    }

    public AssignmentFormatter setPropertySeparator(String propertySeparator) {
        this.propertySeparator = propertySeparator;
        return this;
    }

    public String getNameValueSeparator() {
        return nameValueSeparator;
    }

    public AssignmentFormatter setNameValueSeparator(String keyValueSeparator) {
        this.nameValueSeparator = keyValueSeparator;
        return this;
    }

    private String nameValueSeparator;
    private boolean appendsReasons;
	private boolean shouldFormatIteratingRulesOnly;
	final private List<Pattern> includePatterns;
	final private List<Pattern> excludePatterns;
	private boolean excludeUndefined;

    private AssignmentFormatter() {
        appendsReasons = true;
        includePatterns = new ArrayList<>();
        excludePatterns = new ArrayList<>();
    }

    public String format(PropertyContainer assignments) {
        StringBuilder assignedPropertiesStringBuilder = new StringBuilder();
        Map<String, Assignment> assignmentsAsMap = assignments.getAssignmentsAsMap();
        for (Map.Entry<String, Assignment> assignment : assignmentsAsMap.entrySet()) {
            if(includesAssignment(assignment)){
                appendSeparator(assignedPropertiesStringBuilder);
                append(assignedPropertiesStringBuilder, assignment);
            }
        }
        return assignedPropertiesStringBuilder.toString();
    }

	private boolean includesAssignment(Map.Entry<String, Assignment> assignment) {
		return  !(shouldFormatIteratingRulesOnly && !assignment.getValue().rule.forcesIteration() || excludedByValue(assignment))
				&& isIncludedByExcludePatterns(assignment) && isIncludedByIncludePatterns(assignment);
	}
	
	private boolean excludedByValue(Entry<String, Assignment> assignment) {
		return excludeUndefined && SpecialValue.UNDEFINED.equals(assignment.getValue().value);
	}

	private boolean isIncludedByExcludePatterns(Map.Entry<String, Assignment> assignment) {
		for(Pattern pattern:excludePatterns){
			if(pattern.matcher(assignment.getKey()).matches())
				return false;
		}
		return true;
	}
	
	private boolean isIncludedByIncludePatterns(Map.Entry<String, Assignment> assignment) {
		if(includePatterns.isEmpty())
			return true;
		else {
			for(Pattern pattern:includePatterns){
				if(pattern.matcher(assignment.getKey()).matches())
					return true;
			}
			return false;
		}
	}

    AssignmentFormatter append(StringBuilder assignedPropertiesStringBuilder,
        Map.Entry<String, Assignment> assignment) {
        String targetedPropertyName = assignment.getKey();
        Assignment assignmentProperties = assignment.getValue();
        if (appendsReasons)
            assignedPropertiesStringBuilder.append(assignmentProperties.reason);
        assignedPropertiesStringBuilder.append(targetedPropertyName)
            .append(nameValueSeparator)
            .append(assignmentProperties.value);
        return this;
    }

    private void appendSeparator(StringBuilder assignedPropertiesStringBuilder) {
        if (assignedPropertiesStringBuilder.length() > 0) {
            assignedPropertiesStringBuilder.append(propertySeparator);
        }
    }

    public static AssignmentFormatter create(String nameValueSeparator, String propertySeparator) {
        return new AssignmentFormatter().setPropertySeparator(propertySeparator).setNameValueSeparator(nameValueSeparator);
    }

    public void appendReasons(boolean appendReasons) {
        this.appendsReasons = appendReasons;
    }

    public void shouldFormatIteratingRulesOnly(boolean shouldFormatIteratingRulesOnly) {
        this.shouldFormatIteratingRulesOnly = shouldFormatIteratingRulesOnly;
    }

    public boolean appendsReasons() {
		return appendsReasons;
	}

	public void include(String regex) {
		final Pattern pattern = Pattern.compile(regex);
		include(pattern);
	}

	public void include(final Pattern pattern) {
		includePatterns.add(pattern);
	}

	public void exclude(String regex) {
		final Pattern pattern = Pattern.compile(regex);
		exclude(pattern);
	}

	public void exclude(final Pattern pattern) {
		excludePatterns.add(pattern);
	}

	public void excludeUndefined(boolean exclude) {
		this.excludeUndefined = exclude;
		
	}
}