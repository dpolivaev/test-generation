package org.dpolivaev.tsgen.strategies.internal;

import static org.dpolivaev.tsgen.testutils.TestUtils.assignmentMock;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.dpolivaev.tsgen.ruleengine.Assignment;
import org.dpolivaev.tsgen.ruleengine.Assignments;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

public class TestIdProviderTest {
	private Assignments assignments;
	private TestIdProvider testIdProvider;
	
	private void givenAssignment(String name, Object value, String reason) {
		final Assignment assignmentMock = assignmentMock(name, value, reason);
		Mockito.when(assignmentMock.rule.forcesIteration()).thenReturn(true);
		assignments.add(assignmentMock);
	}
	
	@Before
	public void setup(){
		assignments = new Assignments();
		testIdProvider = new TestIdProvider("=", " ");
	}

	@Test
	public void ignoresNonSpecificProperties(){
		givenAssignment("name", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo(""));
		
	}

	@Test
	public void includesFoc(){
		givenAssignment("foc", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
		
	}
	@Test
	public void includesFoc1(){
		givenAssignment("foc1", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
		
	}
	
	@Test
	public void excludesNonIteratedValues(){
		final Assignment assignmentMock = assignmentMock("foc", "value", "");
		Mockito.when(assignmentMock.rule.forcesIteration()).thenReturn(false);
		assignments.add(assignmentMock);
		assertThat((String)testIdProvider.value(assignments), equalTo(""));
	}
	
	@Test
	public void excludesFocFalse(){
		givenAssignment("foc", "focus(x)", "");
		givenAssignment("x", false, "");
		assertThat((String)testIdProvider.value(assignments), equalTo("focus"));
		
	}
	
	@Ignore
	@Test
	public void includesFocTrue(){
		givenAssignment("foc", "focus(x)", "");
		givenAssignment("x", true, "");
		assertThat((String)testIdProvider.value(assignments), equalTo("focus x"));
		
	}
	
	@Test
	public void includesFocNumber(){
		givenAssignment("foc", "focus(x)", "");
		givenAssignment("x", 6, "");
		assertThat((String)testIdProvider.value(assignments), equalTo("focus x=6"));
		
	}
	@Test
	public void includesPre(){
		givenAssignment("pre", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
		
	}
	@Test
	public void includesVeri(){
		givenAssignment("veri", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
		
	}
	@Test
	public void includesPost(){
		givenAssignment("post", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
		
	}
}
