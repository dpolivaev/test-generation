package org.dpolivaev.testgeneration.engine.strategies.internal;

import static org.dpolivaev.testgeneration.engine.testutils.TestUtils.assignmentMock;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignment;
import org.dpolivaev.testgeneration.engine.ruleengine.Assignments;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AssignmentPartitionerTest {
	private Assignments assignments;
	private AssignmentPartitioner assignmentPartitioner;

	private void givenAssignment(String name, Object value) {
		givenAssignment(name, value, "");
	}

	private void givenAssignment(String name, Object value, String reason) {
		final Assignment assignmentMock = assignmentMock(name, value, reason);
		Mockito.when(assignmentMock.rule.forcesIteration()).thenReturn(true);
		assignments.add(assignmentMock);
	}


	@Before
	public void setup(){
		assignments = new Assignments();
		givenAssignment("testcase.precondition.alias", "precondition");
		givenAssignment("testcase.focus.alias", "when");
		givenAssignment("testcase.verification.alias", "verification");
		givenAssignment("testcase.postprocessing.alias", "postprocessing");
		assignmentPartitioner = new AssignmentPartitioner(assignments);
	}

	@Test
	public void ignoresNonSpecificProperties(){
		givenAssignment("name", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("name"), equalTo(false));

	}

	@Test
	public void includesFoc(){
		givenAssignment("when", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("when"), equalTo(true));

	}
	@Test
	public void foc1IsTestIdRelevant(){
		givenAssignment("when#1", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("when#1"), equalTo(true));

	}

	@Test
	public void foc1IsTestPartMethodCall(){
		givenAssignment("when#1", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestPartMethodCall("when#1"), equalTo(true));

	}

	@Test
	public void includesFoc1(){
		givenAssignment("when#1", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("when#1"), equalTo(true));

	}

	@Test
	public void includesPre(){
		givenAssignment("precondition", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("precondition"), equalTo(true));

	}
	@Test
	public void includesVeri(){
		givenAssignment("verification", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("verification"), equalTo(true));

	}
	@Test
	public void includesPost(){
		givenAssignment("postprocessing", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("postprocessing"), equalTo(true));
	}
}
