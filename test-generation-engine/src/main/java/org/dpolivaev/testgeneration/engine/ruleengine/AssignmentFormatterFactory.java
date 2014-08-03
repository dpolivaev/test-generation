package org.dpolivaev.testgeneration.engine.ruleengine;

public class AssignmentFormatterFactory {
	public AssignmentFormatter createShortAssignmentFormatter() {
		AssignmentFormatter assignmentFormatter = AssignmentFormatter.create("=", ", ");
		assignmentFormatter.appendReasons(false);
		assignmentFormatter.excludeUndefined(true);
		return assignmentFormatter;
	}
	
	public AssignmentFormatter createDetailedAssignmentFormatter() {
		AssignmentFormatter assignmentFormatter = AssignmentFormatter.create("=", "\n");
		assignmentFormatter.appendReasons(true);
		assignmentFormatter.excludeUndefined(false);
		return assignmentFormatter;
	}




}
