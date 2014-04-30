package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.DefaultStatefulRule;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.StatefulRule;
import org.junit.Test;
public class StatefulRuleTest {
	@Test
	public void removesItself() {
		final StatefulRule statefulRule = new DefaultStatefulRule(Collections.<String>emptySet(), Condition.TRUE, null, null);
		assertThat(statefulRule.without(statefulRule), equalTo(null));
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void ruleCanNotRemoveOtherRule() {
		final StatefulRule statefulRule = new DefaultStatefulRule(Collections.<String>emptySet(), Condition.TRUE, null, null);
		final StatefulRule statefulRule2 = new DefaultStatefulRule(Collections.<String>emptySet(),Condition.TRUE, null, null);
		statefulRule.without(statefulRule2);
	}
}
