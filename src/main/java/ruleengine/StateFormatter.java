package ruleengine;

import java.util.Map;

class StateFormatter {
    public String getAssignedPropertiesAsString(MapBasedState state) {
        StringBuilder assignedPropertiesAsString = new StringBuilder();
        for (Map.Entry<String, Object> property : state.getProperties().entrySet()) {
            if (assignedPropertiesAsString.length() > 0)
                assignedPropertiesAsString.append('\t');
            String targetedPropertyName = property.getKey();
            assignedPropertiesAsString.append(targetedPropertyName).append("=").append(property.getValue());
        }
        return assignedPropertiesAsString.toString();
    }

}