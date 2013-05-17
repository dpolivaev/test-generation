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

    private void expect(StateFormatter expectedCombinations) {
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
        ruleEngine.addRule(iterate("property").over("value"));
        assertThat(ruleEngine.hasRuleForProperty("property"), is(true));
    }

    @Test
    public void ruleEngineWithTwoIterationRules_hasRulesForItsTargetedProperties() {
        ruleEngine.addRule(iterate("property1").over("value"));
        ruleEngine.addRule(iterate("property2").over("value"));

        assertThat(ruleEngine.hasRuleForProperty("property1"), is(true));
        assertThat(ruleEngine.hasRuleForProperty("property2"), is(true));
    }

    @Test
    public void singleRuleWithPropertyNamedXValueA_callsScriptProducerWithValueA() {
        ruleEngine.addRule(iterate("x").over("a"));

        generateCombinations();

        expect(combination("x", "a"));
    }

    @Test
    public void singleRuleWithValuesA_B_callsScriptProducerWithValuesA_B() {
        ruleEngine.addRule(iterate("property").over("a", "b"));

        generateCombinations();

        expect(combination("property", "a").followedBy("property", "b"));
    }

    @Test
    public void twoRulesWithValuesAandB_callsScriptProducerWithTheirValues() {
        ruleEngine.addRule(iterate("x").over("a"));
        ruleEngine.addRule(iterate("y").over("b"));

        generateCombinations();

        expect(combination("x", "a", "y", "b"));
    }

    @Test
    public void twoRulesWithValuesA1_A2andB1_B2_callsScriptProducerWithTheirValues() {

        ruleEngine.addRule(iterate("x").over("a1", "a2"));
        ruleEngine.addRule(iterate("y").over("b1", "b2"));

        generateCombinations();

        expect(combination("x", "a1", "y", "b1") //
            .followedBy("x", "a2", "y", "b2"));
    }

    @Test
    public void twoRulesWithValuesA1_A2_A3andB1_B2_callsScriptProducerWithTheirValues() {

        ruleEngine.addRule(iterate("x").over("a1", "a2", "a3"));
        ruleEngine.addRule(iterate("y").over("b1", "b2"));

        generateCombinations();

        expect(combination("x", "a1", "y", "b1") //
            .followedBy("x", "a2", "y", "b2").followedBy("x", "a3", "y", "b1"));
    }

    @Test
    public void triggeringAndTriggeredRulesWithSingleValues_callsScriptProducerWithTheirValues() {

        ruleEngine.addRule(iterate("x").over("a"));
        ruleEngine.addRule(when("x").iterate("y").over("b"));

        generateCombinations();

        expect(combination("x", "a", "y", "b"));
    }

    @Test
    public void triggeringAndTriggeredRulesWithValuesA_B_and_C_D_callsScriptProducerWithTheirValues() {
        ruleEngine.addRule(iterate("x").over("a", "b"));
        ruleEngine.addRule(when("x").iterate("y").over("c", "d"));

        generateCombinations();

        expect(combination("x", "a", "y", "c")
            .followedBy("x", "a", "y", "d").followedBy("x", "b", "y", "c")
            .followedBy("x", "b", "y", "d"));

    }

    @Test
    public void triggeringValueAndConditionallyTriggeredValues_callsScriptProducerWithTheirValues() {
        ruleEngine.addRule(iterate("x").over("a", "b", "c"));
        ruleEngine.addRule(when("x").iterate("y").over("A", "B")._if( //
            new Condition() {
                @Override
                public boolean calculate() {
                    return ruleEngine.get("x").equals("c");
                };
            }));
        generateCombinations();

        expect(combination("x", "a").followedBy("x", "b")
            .followedBy("x", "c", "y", "A").followedBy("x", "c", "y", "B"));
    }

    @Test
    public void newRuleForTheSameProperty_HidesOldRule() {
        ruleEngine.addRule(iterate("x").over("a"));
        ruleEngine.addRule(iterate("x").over("b"));

        generateCombinations();

        expect(combination("x", "b"));
    }

    @Test
    public void topRuleWithNotFulfilledCondition_IsIgnored() {

        ruleEngine.addRule(iterate("x").over("a")._if(new Condition() {
            @Override
            public boolean calculate() {
                return false;
            }
        }));

        generateCombinations();

        expect(combination());
    }

    private void generateCombinations() {
        ruleEngine.run(scriptProducerMock);
    }

    @Test
    public void newRuleForTheSamePropertyWithNotFulfilledCondition_IsIgnored() {

        ruleEngine.addRule(iterate("x").over("a"));
        ruleEngine.addRule(iterate("x").over("b")._if(new Condition() {
            @Override
            public boolean calculate() {
                return false;
            }
        }));

        generateCombinations();

        expect(combination("x", "a"));
    }
}
