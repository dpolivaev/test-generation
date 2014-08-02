package org.dpolivaev.testgeneration.engine.strategies.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.junit.Before;
import org.junit.Test;

public class AssignmentPartitionerTest {
	private AssignmentPartitioner assignmentPartitioner;
	private GivenAssignments assignments;

	@Before
	public void setup(){
		assignments = new GivenAssignments();
		assignments.given("testcase.precondition.alias", "precondition");
		assignments.given("testcase.focus.alias", "when");
		assignments.given("testcase.verification.alias", "verification");
		assignments.given("testcase.postprocessing.alias", "postprocessing");
		assignmentPartitioner = new AssignmentPartitioner(assignments.getAssignments());
	}

	@Test
	public void ignoresNonSpecificProperties(){
		assignments.given("name", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("name"), equalTo(false));

	}

	@Test
	public void ignoresUndefinedProperties(){
		assignments.given("when", SpecialValue.UNDEFINED, "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("when"), equalTo(false));

	}

	@Test
	public void includesFoc(){
		assignments.given("when", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("when"), equalTo(true));

	}
	@Test
	public void foc1IsTestIdRelevant(){
		assignments.given("when#1", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("when#1"), equalTo(true));

	}

	@Test
	public void foc1IsTestPartMethodCall(){
		assignments.given("when#1", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestPartMethodCall("when#1"), equalTo(true));

	}

	@Test
	public void includesFoc1(){
		assignments.given("when#1", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("when#1"), equalTo(true));

	}

	@Test
	public void includesPre(){
		assignments.given("precondition", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("precondition"), equalTo(true));

	}
	@Test
	public void includesVeri(){
		assignments.given("verification", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("verification"), equalTo(true));

	}
	@Test
	public void includesPost(){
		assignments.given("postprocessing", "value", "");
		assignmentPartitioner.run();
		assertThat(assignmentPartitioner.isTestIdRelevant("postprocessing"), equalTo(true));
	}
}
