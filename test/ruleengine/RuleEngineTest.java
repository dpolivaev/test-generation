package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import static ruleengine.StatefulRuleBuilder.Factory.*;
import static ruleengine.StateFormatter.*;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngineTest {

    private RuleEngine ruleEngine = new RuleEngine();
    private LoggingScriptProducerMock scriptProducerMock;

    public RuleEngineTest() {
    }

    private void assertCombinations(StateFormatter expectedCombinations) {
        assertEquals(expectedCombinations.toString(),
            scriptProducerMock.getAllScriptPropertyCombinations());

    }

    @Before
    public void setup() {
        scriptProducerMock = new LoggingScriptProducerMock();
    }

    @Test
    public void ruleEngineWithoutRules_callsScriptProducerOnce() {
        CountingScriptProducerMock scriptProducerMock = new CountingScriptProducerMock();
        ruleEngine.run(scriptProducerMock);
        assertThat(scriptProducerMock.callCount(), is(1));
    }

    @Test
    public void ruleEngineWithOneIterationRule_hasRuleForItsTargetedProperty() {
        ruleEngine.addRule(targeting("property").with("value"));
        assertThat(ruleEngine.hasRuleForProperty("property"), is(true));
    }

    @Test
    public void ruleEngineWithTwoIterationRules_hasRulesForItsTargetedProperties() {
        ruleEngine.addRule(targeting("property1").with("value"));
        ruleEngine.addRule(targeting("property2").with("value"));

        assertThat(ruleEngine.hasRuleForProperty("property1"), is(true));
        assertThat(ruleEngine.hasRuleForProperty("property2"), is(true));
    }

    @Test
    public void singleRuleWithPropertyNamedXValueA_callsScriptProducerWithValueA() {
        ruleEngine.addRule(targeting("x").with("a"));

        generateCombinations();

        assertCombinations(combination("x", "a"));
    }

    @Test
    public void singleRuleWithValuesA_B_callsScriptProducerWithValuesA_B() {
        ruleEngine.addRule(targeting("property").with("a", "b"));

        generateCombinations();

        assertCombinations(combination("property", "a").with("property", "b"));
    }

    @Test
    public void twoRulesWithValuesAandB_callsScriptProducerWithTheirValues() {
        ruleEngine.addRule(targeting("x").with("a"));
        ruleEngine.addRule(targeting("y").with("b"));

        generateCombinations();

        assertCombinations(combination("x", "a", "y", "b"));
    }

    @Test
    public void twoRulesWithValuesA1_A2andB1_B2_callsScriptProducerWithTheirValues() {

        ruleEngine.addRule(targeting("x").with("a1", "a2"));
        ruleEngine.addRule(targeting("y").with("b1", "b2"));

        generateCombinations();

        assertCombinations(combination("x", "a1", "y", "b1") //
            .with("x", "a2", "y", "b2"));
    }

    @Test
    public void twoRulesWithValuesA1_A2_A3andB1_B2_callsScriptProducerWithTheirValues() {

        ruleEngine.addRule(targeting("x").with("a1", "a2", "a3"));
        ruleEngine.addRule(targeting("y").with("b1", "b2"));

        generateCombinations();

        assertCombinations(combination("x", "a1", "y", "b1") //
            .with("x", "a2", "y", "b2").with("x", "a3", "y", "b1"));
    }

    @Test
    public void triggeringAndTriggeredRulesWithSingleValues_callsScriptProducerWithTheirValues() {

        ruleEngine.addRule(targeting("x").with("a"));
        ruleEngine.addRule(targeting("y").with("b").when("x"));

        generateCombinations();

        assertCombinations(combination("x", "a", "y", "b"));
    }

    @Test
    public void triggeringAndTriggeredRulesWithValuesA_B_and_C_D_callsScriptProducerWithTheirValues() {
        ruleEngine.addRule(targeting("x").with("a", "b"));
        ruleEngine.addRule(targeting("y").with("c", "d").when("x"));

        generateCombinations();

        assertCombinations(combination("x", "a", "y", "c")
            .with("x", "a", "y", "d").with("x", "b", "y", "c")
            .with("x", "b", "y", "d"));

    }

    @Test
    public void triggeringValueAndConditionallyTriggeredValues_callsScriptProducerWithTheirValues() {
        ruleEngine.addRule(targeting("x").with("a", "b", "c"));
        ruleEngine.addRule(when("x").targeting("y").with("A", "B")._if( //
            new Condition() {
                @Override
                public boolean calculate() {
                    return ruleEngine.get("x").equals("c");
                };
            }));
        generateCombinations();

        assertCombinations(combination("x", "a").with("x", "b")
            .with("x", "c", "y", "A").with("x", "c", "y", "B"));
    }

    @Test
    public void newRuleForTheSameProperty_HidesOldRule() {
        ruleEngine.addRule(targeting("x").with("a"));
        ruleEngine.addRule(targeting("x").with("b"));

        generateCombinations();

        assertCombinations(combination("x", "b"));
    }

    @Test
    public void topRuleWithNotFulfilledCondition_IsIgnored() {

        ruleEngine.addRule(targeting("x").with("a")._if(new Condition() {
            @Override
            public boolean calculate() {
                return false;
            }
        }));

        generateCombinations();

        assertCombinations(combination());
    }

    private void generateCombinations() {
        ruleEngine.run(scriptProducerMock);
    }

    @Test
    public void newRuleForTheSamePropertyWithNotFulfilledCondition_IsIgnored() {

        ruleEngine.addRule(targeting("x").with("a"));
        ruleEngine.addRule(targeting("x").with("b")._if(new Condition() {
            @Override
            public boolean calculate() {
                return false;
            }
        }));

        generateCombinations();

        assertCombinations(combination("x", "a"));
    }
}
