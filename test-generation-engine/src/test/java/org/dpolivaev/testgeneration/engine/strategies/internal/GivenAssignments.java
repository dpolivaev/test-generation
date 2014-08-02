package org.dpolivaev.testgeneration.engine.strategies.internal;

import static org.dpolivaev.testgeneration.engine.testutils.TestUtils.assignmentMock;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.Assignments;
import org.mockito.Mockito;

public class GivenAssignments {
	private final Assignments assignments = new Assignments();

	public Assignments getAssignments() {
		return assignments;
	}

	public void given(String name, Object value) {
		given(name, value, "");
	}

	public void given(String name, Object value, String reason) {
		final Assignment assignmentMock = assignmentMock(name, value, reason);
		Mockito.when(assignmentMock.rule.forcesIteration()).thenReturn(true);
		assignments.add(assignmentMock);
	}


}
