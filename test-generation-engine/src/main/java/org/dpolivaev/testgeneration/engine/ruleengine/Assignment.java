package org.dpolivaev.testgeneration.engine.ruleengine;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.HashSet;


public class Assignment {
	static final AssignmentFormatter assignmentFormatter;
	static {
		assignmentFormatter = new AssignmentFormatter("", "=");
		assignmentFormatter.appendReasons(true);
		assignmentFormatter.includesHidden(true);
	}
    public final Object value;
    public final Rule rule;
    public final String reason;
    public final Collection<String> requiredProperties;
    public final Collection<String> triggeringProperties;

    public Assignment(Rule rule, Object value, String reason, Collection<String> requiredProperties, Collection<String> triggeringProperties) {
        super();
        this.value = value;
        this.rule = rule;
        this.reason = reason;
		this.triggeringProperties = new HashSet<>(triggeringProperties);
		this.requiredProperties = new HashSet<>(requiredProperties);
    }

    public String getTargetedPropertyName() {
        return rule.getTargetedPropertyName();
    }

	@Override
	public String toString() {
		return assignmentFormatter.format(asList(this));
	}
}