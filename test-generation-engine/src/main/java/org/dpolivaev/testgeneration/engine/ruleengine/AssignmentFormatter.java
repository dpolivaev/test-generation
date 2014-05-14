package org.dpolivaev.testgeneration.engine.ruleengine;

import java.util.regex.Pattern;

public class AssignmentFormatter {
    private String propertySeparator;

    private String nameValueSeparator;
    private boolean appendsReasons;
	private boolean shouldFormatIteratingRulesOnly;
	final private PatternBasedAssignmentFilter patternBasedExcludingAssignmentFilter;
	final private PatternBasedIncludingAssignmentFilter patternBasedIncludingAssignmentFilter;
	private boolean excludeUndefined;
	private boolean includesHidden;

	private AssignmentFormatter() {
        appendsReasons = true;
        patternBasedExcludingAssignmentFilter = new PatternBasedAssignmentFilter();
        patternBasedIncludingAssignmentFilter = new PatternBasedIncludingAssignmentFilter();
        shouldFormatIteratingRulesOnly = false;
        excludeUndefined = false;
        excludeUndefined = false;
    }
    
    public AssignmentFormatter(String propertySeparator,
			String valueNameSeparator) {
		this();
		this.propertySeparator = propertySeparator;
		nameValueSeparator = valueNameSeparator;
	}

	public String format(PropertyContainer assignments) {
		final Iterable<Assignment> iterable = assignments.getAssignments();
        return format(iterable);
    }

	public String format(final Iterable<Assignment> iterable) {
		StringBuilder assignedPropertiesStringBuilder = new StringBuilder();
		for (Assignment assignment : iterable) {
            if(includesAssignment(assignment)){
                appendSeparator(assignedPropertiesStringBuilder);
                append(assignedPropertiesStringBuilder, assignment);
            }
        }
        return assignedPropertiesStringBuilder.toString();
	}

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

    public boolean includesHidden() {
		return includesHidden;
	}

	public void includesHidden(boolean includesHidden) {
		this.includesHidden = includesHidden;
	}

	protected boolean includesAssignment(Assignment assignment) {
		return  !(shouldFormatIteratingRulesOnly && !assignment.rule.forcesIteration() || excludedByValue(assignment))
				&& (includesHidden || ! isHidden(assignment)) && ! patternBasedExcludingAssignmentFilter.matches(assignment) && isIncludedByIncludePatterns(assignment);
	}
	
	private boolean isHidden(Assignment assignment) {
		return assignment.getTargetedPropertyName().startsWith(" ");
	}

	private boolean excludedByValue(Assignment assignment) {
		return excludeUndefined && SpecialValue.UNDEFINED.equals(assignment.value);
	}

	private boolean isIncludedByIncludePatterns(Assignment assignment) {
		return patternBasedIncludingAssignmentFilter.matches(assignment);
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
		patternBasedIncludingAssignmentFilter.addPattern(pattern);
	}

	public void exclude(String regex) {
		patternBasedExcludingAssignmentFilter.addPattern(regex);
	}

	public void exclude(final Pattern pattern) {
		patternBasedExcludingAssignmentFilter.addPattern(pattern);
	}

	public void excludeUndefined(boolean exclude) {
		this.excludeUndefined = exclude;
		
	}
}