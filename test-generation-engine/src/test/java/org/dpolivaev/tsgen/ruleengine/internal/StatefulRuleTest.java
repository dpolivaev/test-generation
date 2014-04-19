package org.dpolivaev.tsgen.ruleengine.internal;

import static org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory.iterate;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.EngineState;
import org.dpolivaev.tsgen.ruleengine.InconsistentRuleException;
import org.dpolivaev.tsgen.ruleengine.Rule;
import org.dpolivaev.tsgen.utils.internal.Utils;
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
