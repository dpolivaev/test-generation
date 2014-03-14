package org.dpolivaev.tsgen.strategies.internal;

import static org.dpolivaev.tsgen.testutils.TestUtils.assignmentMock;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.dpolivaev.tsgen.ruleengine.Assignment;
import org.dpolivaev.tsgen.ruleengine.Assignments;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

public class TestIdProviderTest {
	private Assignments assignments;
	private TestIdProvider testIdProvider;
	private OutputConfiguration outputConfiguration;
	
	private void givenAssignment(String name, Object value, String reason) {
		final Assignment assignmentMock = assignmentMock(name, value, reason);
		Mockito.when(assignmentMock.rule.forcesIteration()).thenReturn(true);
		assignments.add(assignmentMock);
	}
	
	@Before
	public void setup(){
		assignments = new Assignments();
		outputConfiguration = new OutputConfiguration();
		outputConfiguration.setTestCasePropertyNames("precondition", "when", "verification", "postprocessing");
		testIdProvider = new TestIdProvider(outputConfiguration, "=", " ");
	}

	@Test
	public void ignoresNonSpecificProperties(){
		givenAssignment("name", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo(""));
		
	}

	@Test
	public void includesFoc(){
		givenAssignment("when", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
		
	}
	@Test
	public void includesFoc1(){
		givenAssignment("when#1", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
		
	}
	
	@Test
	public void excludesNonIteratedValues(){
		final Assignment assignmentMock = assignmentMock("verification", "value", "");
		Mockito.when(assignmentMock.rule.forcesIteration()).thenReturn(false);
		assignments.add(assignmentMock);
		assertThat((String)testIdProvider.value(assignments), equalTo(""));
	}
	
	@Test
	public void keepswhen(){
		final Assignment assignmentMock = assignmentMock("when", "value", "");
		Mockito.when(assignmentMock.rule.forcesIteration()).thenReturn(false);
		assignments.add(assignmentMock);
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
	}
	
	@Test
	public void excludesFocFalse(){
		givenAssignment("when", "when(x)", "");
		givenAssignment("x", false, "");
		assertThat((String)testIdProvider.value(assignments), equalTo("when"));
		
	}
	
	@Test
	public void includesFocTrue(){
		givenAssignment("when", "when(x)", "");
		givenAssignment("x", true, "");
		assertThat((String)testIdProvider.value(assignments), equalTo("when x"));
		
	}
	
	@Test
	public void includesFocNumber(){
		givenAssignment("when", "when(x)", "");
		givenAssignment("x", 6, "");
		assertThat((String)testIdProvider.value(assignments), equalTo("when x=6"));
		
	}
	@Test
	public void includesPre(){
		givenAssignment("precondition", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
		
	}
	@Test
	public void includesVeri(){
		givenAssignment("verification", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
		
	}
	@Test
	public void includesPost(){
		givenAssignment("postprocessing", "value", "");
		assertThat((String)testIdProvider.value(assignments), equalTo("value"));
		
	}
}
