package ruleengine;

import java.util.Map;

class StateFormatter {
    public String format(MapBasedState state) {
        StringBuilder assignedPropertiesAsString = new StringBuilder();
        for (Map.Entry<String, PropertyAssignment> assignment : state.getAssignments().entrySet()) {
            if (assignedPropertiesAsString.length() > 0)
                assignedPropertiesAsString.append('\t');
            String targetedPropertyName = assignment.getKey();
            assignedPropertiesAsString.append(targetedPropertyName).append("=").append(assignment.getValue().value);
        }
        return assignedPropertiesAsString.toString();
    }

}