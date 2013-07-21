package ruleengine;

public class Assignment {
    public final Object value;
    public final Rule rule;
    public final String reason;

    Assignment(Rule rule, Object value, String reason) {
        super();
        this.value = value;
        this.rule = rule;
        this.reason = reason;
    }

    public String getTargetedPropertyName() {
        return rule.getTargetedPropertyName();
    }
}