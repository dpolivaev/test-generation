package org.dpolivaev.tsgen.ruleengine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.EngineState;
import org.dpolivaev.tsgen.ruleengine.Rule;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.StrategyMerger;
import org.dpolivaev.tsgen.utils.internal.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class StrategyMergerTest {
	
	private Strategy source;
	private Strategy target;

	@Before
	public void setup(){
		source = new Strategy();
		target = new Strategy();
	}

	@Test
	public void addsTopRuleToTarget() {
		Rule triggeredRule = RuleBuilder.Factory.iterate("a").over(1).asTriggeredRule();
		source.addRule(triggeredRule);
		new StrategyMerger().moveRuleFrom(source).to(target);
		assertTrue(target.topRules().contains(triggeredRule));
	}

	@Test
	public void addsDefaultRuleToTarget() {
		Rule defaultRule = RuleBuilder.Factory.iterate("a").over(1).asDefaultRule();
		source.addRule(defaultRule);
		new StrategyMerger().moveRuleFrom(source).to(target);
		assertTrue(target.defaultRules().contains(defaultRule));
	}

	@Test
	public void removesTopRuleFromSource() {
		Rule triggeredRule = RuleBuilder.Factory.iterate("a").over(1).asTriggeredRule();
		source.addRule(triggeredRule);
		new StrategyMerger().moveRuleFrom(source).to(target);
		assertFalse(source.topRules().contains(triggeredRule));
	}

	@Test
	public void addsTriggersToTriggeredRules() {
		Rule triggeredRule = RuleBuilder.Factory.iterate("a").over(1).asTriggeredRule();
		source.addRule(triggeredRule);
		new StrategyMerger().withTrigger("b").moveRuleFrom(source).to(target);
		assertTrue(triggeredRule.hasTriggeringProperties(Utils.set("b")));
	}

	@Test
	public void addsNoTriggersToDefaultRules() {
		Rule defaultRule = RuleBuilder.Factory.iterate("a").over(1).asDefaultRule();
		source.addRule(defaultRule);
		new StrategyMerger().withTrigger("b").moveRuleFrom(source).to(target);
	}

	@Test
	public void addsConditionsToTriggeredRules() {
		Rule triggeredRule = RuleBuilder.Factory.iterate("a").over(1).asTriggeredRule();
		source.addRule(triggeredRule);
		Condition condition = mock(Condition.class);
		new StrategyMerger().withCondition(condition).moveRuleFrom(source).to(target);
		EngineState engineState = mock(EngineState.class);
		triggeredRule.propertyCombinationStarted(engineState);
		verify(condition).isSatisfied(engineState);
	}


	@Test
	public void addsTopRuleToRuleBuilder() {
		Rule triggeredRule = RuleBuilder.Factory.iterate("a").over(1).asTriggeredRule();
		source.addRule(triggeredRule);
		RuleBuilder target = mock(RuleBuilder.class);
		new StrategyMerger().moveRuleFrom(source).to(target);
		verify(target).with(Mockito.anyCollectionOf(Rule.class));
	}
}
