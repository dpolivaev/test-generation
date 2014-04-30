package org.dpolivaev.testgeneration.engine.strategies.internal;

import static org.dpolivaev.testgeneration.engine.testutils.TestUtils.assignmentMock;

import org.dpolivaev.testgeneration.engine.ruleengine.Assignments;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.strategies.internal.DescriptionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DescriptionProviderTest {
	private Assignments assignments;
	private void givenAssignment(String name, Object value, String reason) {
		assignments.add(assignmentMock(name, value, reason));
	}
	
	@Before
	public void setup(){
		assignments = new Assignments();
	}

	@Test
	public void includesUsualRulesWithoutReasons() {
		givenAssignment("name", "value", "");
		givenAssignment("name2", "value2", "name->");
		givenAssignment("[id1]", "reason", "");

		String description = new DescriptionProvider(": ", "\n")
				.describe((PropertyContainer) assignments);
		Assert.assertEquals("name: value\n" + "name2: value2",
				description);
	}


	@Test
	public void excludesRequirements() {
		givenAssignment("[id1]", "reason", "");

		String description = new DescriptionProvider(": ", "\n")
				.describe((PropertyContainer) assignments);
		Assert.assertEquals("", description);
	}

	@Test
	public void excludesUndefinedValues() {
		givenAssignment("name", SpecialValue.UNDEFINED,"");

		String description = new DescriptionProvider(": ", "\n")
				.describe((PropertyContainer) assignments);
		Assert.assertEquals("", description);
	}

	@Test
	public void excludesTestPartsAndParams() {
		givenAssignment("testcase.precondition", "precondition","");
		givenAssignment("testcase.focus.alias", "focus","");
		givenAssignment("focus", "focus(:x)","");
		givenAssignment("x", "x","");
		givenAssignment("focus#1", "focus1","");
		givenAssignment("testcase.verification", "verification","");
		givenAssignment("testcase.postprocessing", "postprocessing","");
		givenAssignment("script.postprocessing", "script postprocessing","");
		givenAssignment("script", "script","");

		String description = new DescriptionProvider(": ", "\n")
				.describe((PropertyContainer) assignments);
		Assert.assertEquals("", description);
	}

	@Test
	public void excludesAliasedTestPartsAndParams() {
		givenAssignment("script.alias", "myscript","");
		givenAssignment("testcase.alias", "mytestcase","");
		givenAssignment("mytestcase.precondition", "precondition","");
		givenAssignment("mytestcase.focus.alias", "focus","");
		givenAssignment("focus", "focus(:x)","");
		givenAssignment("x", "x","");
		givenAssignment("focus#1", "focus1","");
		givenAssignment("mytestcase.verification", "verification","");
		givenAssignment("mytestcase.postprocessing", "postprocessing","");
		givenAssignment("myscript.postprocessing", "myscript postprocessing","");
		givenAssignment("myscript", "myscript","");

		String description = new DescriptionProvider(": ", "\n")
				.describe((PropertyContainer) assignments);
		Assert.assertEquals("", description);
	}
}
