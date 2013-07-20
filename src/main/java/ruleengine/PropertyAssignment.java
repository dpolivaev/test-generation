package ruleengine;

public class PropertyAssignment {
    public final Object value;
    public final Rule rule;
    public final String reason;

    PropertyAssignment(Rule rule, Object value, String reason) {
        super();
        this.value = value;
        this.rule = rule;
        this.reason = reason;
    }

    public String getTargetedPropertyName() {
        return rule.getTargetedPropertyName();
    }
}