package org.dpolivaev.tsgen.ruleengine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.EngineState;
import org.dpolivaev.tsgen.ruleengine.Rule;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.StrategyMerger;
import org.dpolivaev.tsgen.ruleengine.internal.TriggeredStatefulRule;
import org.dpolivaev.tsgen.utils.internal.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class StrategyMergerTest {
	
	private Strategy source;
	private Strategy target;

	private void assertRulesContainRuleForProperty(Collection<Rule> rules, String name) {
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
		target.initialize();
		assertRulesContainRuleForProperty(target.triggeredRules(), "a");
	}

	@Test
	public void addsDefaultRuleToTarget() {
		RuleBuilder defaultRule = RuleBuilder.Factory.iterate("a").over(1).asDefaultRule();
		source.addRule(defaultRule);
		new StrategyMerger().moveRuleFrom(source).to(target);
		target.initialize();
		assertRulesContainRuleForProperty(target.defaultRules(), "a");
	}

	@Test
	public void removesTopRuleFromSource() {
		RuleBuilder triggeredRule = RuleBuilder.Factory.iterate("a").over(1);
		source.addRule(triggeredRule);
		new StrategyMerger().moveRuleFrom(source).to(target);
		assertFalse(source.triggeredRules().contains(triggeredRule));
	}

	@Test
	public void addsTriggersToTriggeredRules() {
		RuleBuilder triggeredRule = RuleBuilder.Factory.iterate("a").over(1);
		source.addRule(triggeredRule);
		new StrategyMerger().withTrigger("b").moveRuleFrom(source).to(target);
		assertThat(((TriggeredStatefulRule)triggeredRule.create()).getTriggeringProperties(), equalTo (Utils.set("b")));
	}

	@Test
	public void addsNoTriggersToDefaultRules() {
		RuleBuilder defaultRule = RuleBuilder.Factory.iterate("a").over(1).asDefaultRule();
		source.addRule(defaultRule);
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
