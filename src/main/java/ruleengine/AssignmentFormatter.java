package ruleengine;

import java.util.Map;

import ruleengine.impl.Assignment;

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

    public AssignmentFormatter() {
        appendsReasons = true;
        propertySeparator = "\t";
        nameValueSeparator = "=";
    }

    public String format(PropertyContainer assignments) {
        StringBuilder assignedPropertiesStringBuilder = new StringBuilder();
        Map<String, Assignment> assignmentsAsMap = assignments.getAssignmentsAsMap();
        for (Map.Entry<String, Assignment> assignment : assignmentsAsMap.entrySet()) {
            if(! shouldFormatIteratingRulesOnly || assignment.getValue().rule.forcesIteration()){
                appendSeparator(assignedPropertiesStringBuilder);
                append(assignedPropertiesStringBuilder, assignment);
            }
        }
        return assignedPropertiesStringBuilder.toString();
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

    public static AssignmentFormatter formatter(String propertySeparator, String nameValueSeparator) {
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
}