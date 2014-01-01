package org.dpolivaev.tsgen.ruleengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

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
    
    public AssignmentFormatter(String propertySeparator,
			String valueNameSeparator) {
		this();
		this.propertySeparator = propertySeparator;
		nameValueSeparator = valueNameSeparator;
	}

	public String format(PropertyContainer assignments) {
        StringBuilder assignedPropertiesStringBuilder = new StringBuilder();
        for (Assignment assignment : assignments.getAssignments()) {
            if(includesAssignment(assignment)){
                appendSeparator(assignedPropertiesStringBuilder);
                append(assignedPropertiesStringBuilder, assignment);
            }
        }
        return assignedPropertiesStringBuilder.toString();
    }

	private boolean includesAssignment(Assignment assignment) {
		return  !(shouldFormatIteratingRulesOnly && !assignment.rule.forcesIteration() || excludedByValue(assignment))
				&& ! isExcludedByExcludePatterns(assignment) && isIncludedByIncludePatterns(assignment);
	}
	
	private boolean excludedByValue(Assignment assignment) {
		return excludeUndefined && SpecialValue.UNDEFINED.equals(assignment.value);
	}

	private boolean isExcludedByExcludePatterns(Assignment assignment) {
		return matches(assignment, excludePatterns);
	}

	private boolean isIncludedByIncludePatterns(Assignment assignment) {
		return includePatterns.isEmpty() || matches(assignment, includePatterns);
	}

	private boolean matches(Assignment assignment,
			List<Pattern> patterns) {
		for(Pattern pattern:patterns){
			if(pattern.matcher(assignment.getTargetedPropertyName()).matches())
				return true;
		}
		return false;
	}
	
    protected AssignmentFormatter append(StringBuilder assignedPropertiesStringBuilder,
    		Assignment assignment) {
        appendReason(assignedPropertiesStringBuilder, assignment);
        appendName(assignedPropertiesStringBuilder, assignment);
        appendNameValueSeparator(assignedPropertiesStringBuilder, assignment);
        appendValue(assignedPropertiesStringBuilder, assignment);
        return this;
    }

	protected void appendValue(StringBuilder assignedPropertiesStringBuilder,
			Assignment assignment) {
		assignedPropertiesStringBuilder.append(assignment.value);
	}

    protected void appendNameValueSeparator(
			StringBuilder assignedPropertiesStringBuilder, Assignment assignment) {
		assignedPropertiesStringBuilder.append(nameValueSeparator);
	}

    protected void appendName(StringBuilder assignedPropertiesStringBuilder,
			Assignment assignment) {
		String targetedPropertyName = assignment.getTargetedPropertyName();
        assignedPropertiesStringBuilder.append(targetedPropertyName);
	}

    protected void appendReason(StringBuilder assignedPropertiesStringBuilder,
			Assignment assignmentProperties) {
		if (appendsReasons)
            assignedPropertiesStringBuilder.append(assignmentProperties.reason);
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