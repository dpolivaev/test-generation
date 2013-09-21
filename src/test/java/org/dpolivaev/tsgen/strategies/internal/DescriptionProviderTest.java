package org.dpolivaev.tsgen.strategies.internal;

import static org.dpolivaev.tsgen.testutils.TestUtils.ruleMock;

import org.dpolivaev.tsgen.ruleengine.Assignment;
import org.dpolivaev.tsgen.ruleengine.Assignments;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.Rule;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.strategies.internal.DescriptionProvider;
import org.junit.Assert;
import org.junit.Test;

public class DescriptionProviderTest {

	@Test
	public void includesUsualRulesWithReasons() {
        Rule rule1 = ruleMock("name");
        Rule rule2 = ruleMock("name2");
        Rule ruleRequirement = ruleMock("requirement.id1");
        
        Assignments assignments = new Assignments();
        assignments.add(new Assignment(rule1, "value", ""));
        assignments.add(new Assignment(rule2, "value2", "name->"));
        assignments.add(new Assignment(ruleRequirement, "reason", ""));
        
		String description = new DescriptionProvider().describe((PropertyContainer)assignments);
		Assert.assertEquals("name: value\n"+
						"name->name2: value2", description);
	}

	@Test
	public void excludesRequirements() {
        Rule ruleRequirement = ruleMock("requirement.id1");
        
        Assignments assignments = new Assignments();
        assignments.add(new Assignment(ruleRequirement, "reason", ""));
        
		String description = new DescriptionProvider().describe((PropertyContainer)assignments);
		Assert.assertEquals("", description);
	}

	@Test
	public void excludesUndefinedValues() {
        Rule ruleRequirement = ruleMock("name");
        
        Assignments assignments = new Assignments();
        assignments.add(new Assignment(ruleRequirement, SpecialValue.UNDEFINED, ""));
        
		String description = new DescriptionProvider().describe((PropertyContainer)assignments);
		Assert.assertEquals("", description);
	}

	@Test
	public void excludesDescriptions() {
        Rule ruleRequirement = ruleMock("xxxDescription");
        
        Assignments assignments = new Assignments();
        assignments.add(new Assignment(ruleRequirement, "value", ""));
        
		String description = new DescriptionProvider().describe((PropertyContainer)assignments);
		Assert.assertEquals("", description);
	}

}
