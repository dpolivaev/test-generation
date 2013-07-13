package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ruleengine.StatefulRuleBuilder.Factory.iterate;

import org.junit.Before;
import org.junit.Test;

public class StrategyTest {
    private Strategy strategy;

    @Before
    public void setup() {
        strategy = new Strategy();
    }

    @Test
    public void strategyWithOneIterationRule_hasRuleForItsTargetedProperty() {
        strategy.addRule(iterate("property").over("value"));
        assertThat(strategy.hasRuleForProperty("property"), is(true));
    }

    @Test
    public void strategyWithTwoIterationRules_hasRulesForItsTargetedProperties() {
        strategy.addRule(iterate("property1").over("value"));
        strategy.addRule(iterate("property2").over("value"));

        assertThat(strategy.hasRuleForProperty("property1"), is(true));
        assertThat(strategy.hasRuleForProperty("property2"), is(true));
    }

    @Test
    public void strategyWithOneDefaultRule_hasRuleForItsTargetedProperty() {
        strategy.addRule(iterate("property").over("value").byDefault());
        assertThat(strategy.hasRuleForProperty("property"), is(true));
    }
}