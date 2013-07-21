package ruleengine;

import java.util.Map;

class AssignmentFormatter {
    private String propertySeparator;

    public String getPropertySeparator() {
        return propertySeparator;
    }

    public void setPropertySeparator(String propertySeparator) {
        this.propertySeparator = propertySeparator;
    }

    public String getNameValueSeparator() {
        return nameValueSeparator;
    }

    public void setNameValueSeparator(String keyValueSeparator) {
        this.nameValueSeparator = keyValueSeparator;
    }

    private String nameValueSeparator;

    AssignmentFormatter() {
        propertySeparator = "\t";
        nameValueSeparator = "=";
    }

    public String format(Assignments assignments) {
        StringBuilder assignedPropertiesStringBuilder = new StringBuilder();
        for (Map.Entry<String, Assignment> assignment : assignments.entrySet()) {
            if (assignedPropertiesStringBuilder.length() > 0) {
                assignedPropertiesStringBuilder.append(propertySeparator);
            }
            append(assignedPropertiesStringBuilder, assignment);
        }
        return assignedPropertiesStringBuilder.toString();
    }

    public void append(StringBuilder assignedPropertiesStringBuilder, Map.Entry<String, Assignment> assignment) {
        String targetedPropertyName = assignment.getKey();
        Assignment assignmentProperties = assignment.getValue();
        assignedPropertiesStringBuilder.append(targetedPropertyName).append(nameValueSeparator)
            .append(assignmentProperties.value);
    }

}