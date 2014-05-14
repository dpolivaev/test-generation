package org.dpolivaev.testgeneration.engine.ruleengine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PatternBasedAssignmentFilter implements AssignmentFilter {
	private final List<Pattern> patterns;

	public PatternBasedAssignmentFilter() {
		patterns = new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see org.dpolivaev.testgeneration.engine.ruleengine.AssignmentFilter#matches(org.dpolivaev.testgeneration.engine.ruleengine.Assignment)
	 */
	@Override
	public boolean matches(Assignment assignment) {
		return matches(assignment, patterns);
	}

	public void addPattern(final Pattern pattern) {
		patterns.add(pattern);
	}

	public void addPattern(String regex) {
		final Pattern pattern = Pattern.compile(regex);
		addPattern(pattern);
	}

	private boolean matches(Assignment assignment,
			List<Pattern> patterns) {
		for(Pattern pattern:patterns){
			if(pattern.matcher(assignment.getTargetedPropertyName()).matches())
				return true;
		}
		return false;
	}

}