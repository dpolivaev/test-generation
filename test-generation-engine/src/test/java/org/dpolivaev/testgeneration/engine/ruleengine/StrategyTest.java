package org.dpolivaev.testgeneration.engine.ruleengine;

import static org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder.Factory.iterate;
import static org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder.Factory.rule;
import static org.dpolivaev.testgeneration.engine.testutils.TestUtils.rulePropertyNameMatches;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.internal.AlternatingRule;
import org.junit.Before;
import org.junit.Test;

public class StrategyTest {
    private Strategy strategy;

    @Before
    public void setup() {
        strategy = new Strategy();
    }

    @Test
    public void afterAddingTopRule_returnsTopRule(){
        Rule rule = iterate("x").over("a").create();
        strategy.addRule(rule);
        assertThat(strategy.triggeredRules(), hasItem(rule));
    }

    @Test
    public void afterRemovingTopRule_doesNotReturnIt(){
        Rule rule = iterate("x").over("a").create();
        strategy.addRule(rule);
        strategy.removeRule(rule);
        assertThat(strategy.triggeredRules(), not(hasItem(rule)));
    }

    @Test
    public void afterAddingTopRule_doesNotReturnLazyRule(){
        Rule rule = iterate("x").over("a").create();
        strategy.addRule(rule);
        assertThat(strategy.lazyRules(), not(hasItem(rule)));
    }

    @Test
    public void afterAddingSecondTopRuleForTheSameProperty_containsAlternatingRule(){
        Rule rule1 = rule("x").iterate("x").over("a").create();
        Rule rule2 = rule("x").iterate("x").over("a").create();
        strategy.addRule(rule1);
        strategy.addRule(rule2);
        assertThat(strategy.triggeredRules(), hasItem(isA(AlternatingRule.class)));
    }

    @Test
    public void afterAddingLazyRule_returnsLazyRule(){
        Rule rule = iterate("x").over("a").asLazyRule().create();
        strategy.addRule(rule);
        assertThat(strategy.lazyRules(), hasItem(rule));
    }

    @Test
    public void afterAddingLazyRule_doesNotReturnTopRule(){
        Rule rule = iterate("x").over("a").asLazyRule().create();
        strategy.addRule(rule);
        assertThat(strategy.triggeredRules(), not(hasItem(rule)));
    }

    @Test
    public void afterAddingTriggeringRulesWithDifferentTriggeringProperties_returnsBothRules(){
        Rule ruleP = iterate("x").over("a").when("p").create();
        strategy.addRule(ruleP);
        Rule ruleQ = iterate("x").over("a").when("q").create();
        strategy.addRule(ruleQ);
        assertThat(strategy.triggeredRules(), hasItem(ruleP));
        assertThat(strategy.triggeredRules(), hasItem(ruleQ));
    }
    
    @Test
    public void requestedUnknownLazyProperty_returnsSpecialRule(){
        assertThat(strategy.getLazyRulesForProperty("unknownProperty"), notNullValue());
    }
    
    @Test
    public void stategyCombination(){
        Strategy strategy2 = new Strategy();
        RuleBuilder ruleP = iterate("p").over("a");
        strategy.addRule(ruleP);
        RuleBuilder lazyRuleP = iterate("p").over("a").asLazyRule();
        strategy2.addRule(lazyRuleP);
        RuleBuilder ruleQ = iterate("x").over("a").when("q");
        strategy2.addRule(ruleQ);
        
        Strategy combinedStrategy = strategy.with(strategy2);
        combinedStrategy.initialize(null);
        assertThat(combinedStrategy.triggeredRules(), hasItem(rulePropertyNameMatches("p")));
        assertThat(combinedStrategy.triggeredRules(), hasItem(rulePropertyNameMatches("x")));
        assertThat(combinedStrategy.lazyRules(), hasItem(rulePropertyNameMatches("p")));
    }
}