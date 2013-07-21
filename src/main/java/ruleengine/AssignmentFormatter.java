package ruleengine;

import java.util.Map;

class AssignmentFormatter {
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
    private boolean appendReasons;

    AssignmentFormatter() {
        appendReasons = true;
        propertySeparator = "\t";
        nameValueSeparator = "=";
    }

    public String format(Assignments assignments) {
        StringBuilder assignedPropertiesStringBuilder = new StringBuilder();
        for (Map.Entry<String, Assignment> assignment : assignments.entrySet()) {
            append(assignedPropertiesStringBuilder, assignment);
        }
        return assignedPropertiesStringBuilder.toString();
    }

    public AssignmentFormatter append(StringBuilder assignedPropertiesStringBuilder,
        Map.Entry<String, Assignment> assignment) {
        if (assignedPropertiesStringBuilder.length() > 0) {
            assignedPropertiesStringBuilder.append(propertySeparator);
        }
        String targetedPropertyName = assignment.getKey();
        Assignment assignmentProperties = assignment.getValue();
        if (appendReasons)
            assignedPropertiesStringBuilder.append(assignmentProperties.reason);
        assignedPropertiesStringBuilder.append(targetedPropertyName)
            .append(nameValueSeparator)
            .append(assignmentProperties.value);
        return this;
    }

    public static AssignmentFormatter formatter(String propertySeparator, String nameValueSeparator) {
        return new AssignmentFormatter().setPropertySeparator(propertySeparator).setNameValueSeparator(nameValueSeparator);
    }

    public void appendReasons(boolean appendReasons) {
        this.appendReasons = appendReasons;
    }

}