package org.dpolivaev.tsgen.ruleengine;

import static org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory.iterate;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.internal.AlternatingRule;
import org.dpolivaev.tsgen.ruleengine.internal.StatefulRule;
import org.junit.Before;
import org.junit.Test;

public class StrategyTest {
    private Strategy strategy;

    @Before
    public void setup() {
        strategy = new Strategy();
    }

    @Test
    public void afterAddingTopRule_returnsTopRule() throws Exception {
        StatefulRule rule = iterate("x").over("a").asRule();
        strategy.addRule(rule);
        assertThat(strategy.topRules(), hasItem(rule));
    }

    @Test
    public void afterRemovingTopRule_doesNotReturnIt() throws Exception {
        StatefulRule rule = iterate("x").over("a").asRule();
        strategy.addRule(rule);
        strategy.removeRule(rule);
        assertThat(strategy.topRules(), not(hasItem(rule)));
    }

    @Test
    public void afterAddingTopRule_doesNotReturnDefaultRule() throws Exception {
        StatefulRule rule = iterate("x").over("a").asRule();
        strategy.addRule(rule);
        assertThat(strategy.defaultRules(), not(hasItem(rule)));
    }

    @Test
    public void afterAddingSecondTopRuleForTheSameProperty_containsAlternatingRule() throws Exception {
        StatefulRule rule1 = iterate("x").over("a").asRule();
        StatefulRule rule2 = iterate("x").over("a").asRule();
        strategy.addRule(rule1);
        strategy.addRule(rule2);
        assertThat(strategy.topRules(), hasItem(isA(AlternatingRule.class)));
    }

    @Test
    public void afterAddingDefaultRule_returnsDefaultRule() throws Exception {
        StatefulRule rule = iterate("x").over("a").asDefaultRule();
        strategy.addRule(rule);
        assertThat(strategy.defaultRules(), hasItem(rule));
    }

    @Test
    public void afterAddingDefaultRule_doesNotReturnTopRule() throws Exception {
        StatefulRule rule = iterate("x").over("a").asDefaultRule();
        strategy.addRule(rule);
        assertThat(strategy.topRules(), not(hasItem(rule)));
    }

    @Test
    public void afterAddingTriggeringRulesWithDifferentTriggeringProperties_returnsBothRules() throws Exception {
        StatefulRule ruleP = iterate("x").over("a").when("p").asRule();
        strategy.addRule(ruleP);
        StatefulRule ruleQ = iterate("x").over("a").when("q").asRule();
        strategy.addRule(ruleQ);
        assertThat(strategy.triggeredRules(), hasItem(ruleP));
        assertThat(strategy.triggeredRules(), hasItem(ruleQ));
    }
    
    @Test
    public void requestedUnknownDefaultProperty_returnsSpecialRule() throws Exception {
        assertThat(strategy.getDefaultRulesForProperty("unknownProperty"), notNullValue());
    }
    
    @Test
    public void stategyCombination(){
        Strategy strategy2 = new Strategy();
        StatefulRule ruleP = iterate("p").over("a").asRule();
        strategy.addRule(ruleP);
        StatefulRule defaultRuleP = iterate("p").over("a").asDefaultRule();
        strategy2.addRule(defaultRuleP);
        StatefulRule ruleQ = iterate("x").over("a").when("q").asRule();
        strategy2.addRule(ruleQ);
        
        Strategy combinedStrategy = strategy.with(strategy2);
        assertThat(combinedStrategy.topRules(), hasItem(ruleP));
        assertThat(combinedStrategy.triggeredRules(), hasItem(ruleQ));
        assertThat(combinedStrategy.defaultRules(), hasItem(defaultRuleP));
    }
}