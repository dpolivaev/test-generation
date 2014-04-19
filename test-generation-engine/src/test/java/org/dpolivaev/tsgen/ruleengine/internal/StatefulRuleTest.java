package org.dpolivaev.tsgen.ruleengine.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.junit.Test;
public class StatefulRuleTest {

	@Test
	public void addConditionToTrueCondition() {
		final StatefulRule statefulRule = new DefaultStatefulRule(Condition.TRUE, null, null);
		Condition condition = mock(Condition.class);
		statefulRule.addCondition(condition);
		statefulRule.propertyRequired(null);
		verify(condition).isSatisfied(null);
	}

	@Test
	public void addConditionToOtherCondition() {
		Condition condition = mock(Condition.class);
		Condition otherCondition = mock(Condition.class);
		final StatefulRule statefulRule = new DefaultStatefulRule(condition, null, null);
		statefulRule.addCondition(otherCondition);
		statefulRule.propertyRequired(null);
		verify(otherCondition).isSatisfied(null);
	}
}
