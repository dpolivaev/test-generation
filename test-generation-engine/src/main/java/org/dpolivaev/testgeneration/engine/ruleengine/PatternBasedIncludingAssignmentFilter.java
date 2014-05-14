package org.dpolivaev.testgeneration.engine.ruleengine;

import java.util.regex.Pattern;

public class PatternBasedIncludingAssignmentFilter implements AssignmentFilter {
	private final PatternBasedAssignmentFilter filter = new PatternBasedAssignmentFilter();
	private boolean empty = true;

	public boolean matches(Assignment assignment) {
		return empty || filter.matches(assignment);
	}

	public void addPattern(Pattern pattern) {
		filter.addPattern(pattern);
		empty = false;
	}

	public void addPattern(String regex) {
		filter.addPattern(regex);
		empty = false;
	}
}
