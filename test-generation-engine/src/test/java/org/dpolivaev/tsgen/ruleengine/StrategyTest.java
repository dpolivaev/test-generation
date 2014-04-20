package org.dpolivaev.tsgen.ruleengine;

import static org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory.iterate;
import static org.dpolivaev.tsgen.testutils.TestUtils.rulePropertyNameMatches;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.internal.AlternatingRule;
import org.dpolivaev.tsgen.testutils.TestUtils;
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
        Rule rule = iterate("x").over("a").create();
        strategy.addRule(rule);
        assertThat(strategy.topRules(), hasItem(rule));
    }

    @Test
    public void afterRemovingTopRule_doesNotReturnIt() throws Exception {
        Rule rule = iterate("x").over("a").create();
        strategy.addRule(rule);
        strategy.removeRule(rule);
        assertThat(strategy.topRules(), not(hasItem(rule)));
    }

    @Test
    public void afterAddingTopRule_doesNotReturnDefaultRule() throws Exception {
        Rule rule = iterate("x").over("a").create();
        strategy.addRule(rule);
        assertThat(strategy.defaultRules(), not(hasItem(rule)));
    }

    @Test
    public void afterAddingSecondTopRuleForTheSameProperty_containsAlternatingRule() throws Exception {
        Rule rule1 = iterate("x").over("a").create();
        Rule rule2 = iterate("x").over("a").create();
        strategy.addRule(rule1);
        strategy.addRule(rule2);
        assertThat(strategy.topRules(), hasItem(isA(AlternatingRule.class)));
    }

    @Test
    public void afterAddingDefaultRule_returnsDefaultRule() throws Exception {
        Rule rule = iterate("x").over("a").asDefaultRule().create();
        strategy.addRule(rule);
        assertThat(strategy.defaultRules(), hasItem(rule));
    }

    @Test
    public void afterAddingDefaultRule_doesNotReturnTopRule() throws Exception {
        Rule rule = iterate("x").over("a").asDefaultRule().create();
        strategy.addRule(rule);
        assertThat(strategy.topRules(), not(hasItem(rule)));
    }

    @Test
    public void afterAddingTriggeringRulesWithDifferentTriggeringProperties_returnsBothRules() throws Exception {
        Rule ruleP = iterate("x").over("a").when("p").create();
        strategy.addRule(ruleP);
        Rule ruleQ = iterate("x").over("a").when("q").create();
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
        RuleBuilder ruleP = iterate("p").over("a");
        strategy.addRule(ruleP);
        RuleBuilder defaultRuleP = iterate("p").over("a").asDefaultRule();
        strategy2.addRule(defaultRuleP);
        RuleBuilder ruleQ = iterate("x").over("a").when("q");
        strategy2.addRule(ruleQ);
        
        Strategy combinedStrategy = strategy.with(strategy2);
        combinedStrategy.initialize();
        assertThat(combinedStrategy.topRules(), hasItem(rulePropertyNameMatches("p")));
        assertThat(combinedStrategy.triggeredRules(), hasItem(rulePropertyNameMatches("x")));
        assertThat(combinedStrategy.defaultRules(), hasItem(rulePropertyNameMatches("p")));
    }
}