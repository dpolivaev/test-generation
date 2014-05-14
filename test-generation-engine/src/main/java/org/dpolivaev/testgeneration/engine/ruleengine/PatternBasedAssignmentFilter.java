package org.dpolivaev.testgeneration.engine.ruleengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class PatternBasedAssignmentFilter implements AssignmentFilter {
	private final List<Pattern> patterns;

	public PatternBasedAssignmentFilter() {
		patterns = new ArrayList<>();
	}

	@Override
	public boolean matches(Assignment assignment) {
		for(Pattern pattern:patterns){
			if(pattern.matcher(assignment.getTargetedPropertyName()).matches())
				return true;
		}
		return false;
	}

	public void addPattern(final Pattern pattern) {
		patterns.add(pattern);
	}

	public void addPattern(String regex) {
		final Pattern pattern = Pattern.compile(regex);
		addPattern(pattern);
	}

	public void addPatterns(Collection<String> patterns) {
		for(String pattern : patterns)
			addPattern(pattern);
	}

}