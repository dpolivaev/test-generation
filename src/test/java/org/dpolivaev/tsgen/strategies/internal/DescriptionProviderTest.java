package org.dpolivaev.tsgen.strategies.internal;

import static org.dpolivaev.tsgen.testutils.TestUtils.assignmentMock;

import org.dpolivaev.tsgen.ruleengine.Assignments;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
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

		String description = new DescriptionProvider()
				.describe((PropertyContainer) assignments);
		Assert.assertEquals("name: value\n" + "name2: value2",
				description);
	}


	@Test
	public void excludesRequirements() {
		givenAssignment("[id1]", "reason", "");

		String description = new DescriptionProvider()
				.describe((PropertyContainer) assignments);
		Assert.assertEquals("", description);
	}

	@Test
	public void excludesUndefinedValues() {
		givenAssignment("name", SpecialValue.UNDEFINED,"");

		String description = new DescriptionProvider()
				.describe((PropertyContainer) assignments);
		Assert.assertEquals("", description);
	}

	@Test
	public void excludesTestPartsAndParams() {
		givenAssignment("pre", "pre","");
		givenAssignment("foc", "foc","");
		givenAssignment("foc1", "foc1","");
		givenAssignment("foc.x", "foc.x","");
		givenAssignment("veri", "veri","");
		givenAssignment("post", "post","");
		givenAssignment("script", "script","");

		String description = new DescriptionProvider()
				.describe((PropertyContainer) assignments);
		Assert.assertEquals("", description);
	}

}
