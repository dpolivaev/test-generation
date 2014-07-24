package org.dpolivaev.testgeneration.engine.ruleengine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.EngineState;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.StrategyMerger;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.TriggeredStatefulRule;
import org.dpolivaev.testgeneration.engine.utils.internal.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class StrategyMergerTest {
	
	private Strategy source;
	private Strategy target;

	private void assertRulesContainRuleForProperty(Iterable<Rule> rules, String name) {
		assertTrue(rules.iterator().next().getTargetedPropertyName().equals(name));
	}

	@Before
	public void setup(){
		source = new Strategy();
		target = new Strategy();
	}

	@Test
	public void addsTopRuleToTarget() {
		RuleBuilder triggeredRule = RuleBuilder.Factory.iterate("a").over(1);
		source.addRule(triggeredRule);
		new StrategyMerger().moveRuleFrom(source).to(target);
		target.initialize(null);
		assertRulesContainRuleForProperty(target.triggeredRules(), "a");
	}

	@Test
	public void addsLazyRuleToTarget() {
		RuleBuilder lazyRule = RuleBuilder.Factory.iterate("a").over(1).asLazyRule();
		source.addRule(lazyRule);
		new StrategyMerger().moveRuleFrom(source).to(target);
		target.initialize(null);
		assertRulesContainRuleForProperty(target.lazyRules(), "a");
	}

	@Test
	public void removesTopRuleFromSource() {
		RuleBuilder triggeredRule = RuleBuilder.Factory.iterate("a").over(1);
		source.addRule(triggeredRule);
		new StrategyMerger().moveRuleFrom(source).to(target);
		assertFalse(source.triggeredRules().iterator().hasNext());
	}

	@Test
	public void addsTriggersToTriggeredRules() {
		RuleBuilder triggeredRule = RuleBuilder.Factory.iterate("a").over(1);
		source.addRule(triggeredRule);
		new StrategyMerger().withTrigger("b").moveRuleFrom(source).to(target);
		assertThat(((TriggeredStatefulRule)triggeredRule.create()).getTriggeringProperties(), equalTo (Utils.set("b")));
	}

	@Test
	public void addsNoTriggersToLazyRules() {
		RuleBuilder lazyRule = RuleBuilder.Factory.iterate("a").over(1).asLazyRule();
		source.addRule(lazyRule);
		new StrategyMerger().withTrigger("b").moveRuleFrom(source).to(target);
	}

	@Test
	public void addsConditionsToTriggeredRules() {
		RuleBuilder triggeredRule = RuleBuilder.Factory.iterate("a").over(1);
		source.addRule(triggeredRule);
		Condition condition = mock(Condition.class);
		new StrategyMerger().withCondition(condition).moveRuleFrom(source).to(target);
		EngineState engineState = mock(EngineState.class);
		triggeredRule.create().propertyCombinationStarted(engineState);
		verify(condition).isSatisfied(engineState);
	}


	@Test
	public void addsTopRuleToRuleBuilder() {
		RuleBuilder triggeredRule = RuleBuilder.Factory.iterate("a").over(1);
		source.addRule(triggeredRule);
		RuleBuilder target = mock(RuleBuilder.class);
		new StrategyMerger().moveRuleFrom(source).to(target);
		verify(target).with(Mockito.anyCollectionOf(RuleBuilder.class));
	}
}
