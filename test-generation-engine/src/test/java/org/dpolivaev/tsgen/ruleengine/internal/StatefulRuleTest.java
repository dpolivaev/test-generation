package org.dpolivaev.tsgen.ruleengine.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
public class StatefulRuleTest {
	@Test
	public void removesItself() {
		final StatefulRule statefulRule = new DefaultStatefulRule(Condition.TRUE, null, null);
		assertThat(statefulRule.without(statefulRule), equalTo(null));
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void ruleCanNotRemoveOtherRule() {
		final StatefulRule statefulRule = new DefaultStatefulRule(Condition.TRUE, null, null);
		final StatefulRule statefulRule2 = new DefaultStatefulRule(Condition.TRUE, null, null);
		statefulRule.without(statefulRule2);
	}
}
