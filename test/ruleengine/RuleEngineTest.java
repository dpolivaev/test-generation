package ruleengine;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static ruleengine.Combinations.*;
import static ruleengine.StatefulRuleBuilder.Factory.*;

import org.junit.*;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngineTest {

    private RuleEngine ruleEngine = new RuleEngine();
    private LoggingScriptProducerMock scriptProducerMock;
    private static final Condition FALSE = new Condition() {
        @Override
        public boolean isSatisfied() {
            return false;
        }
    };

    public RuleEngineTest() {
    }

    private void generateCombinations() {
        ruleEngine.run(scriptProducerMock);
    }

    private void expect(Combinations expectedCombinations) {
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
    public void singleRuleWithValuesA_B() {
        ruleEngine.addRule(iterate("property").over("a", "b"));

        generateCombinations();

        expect(combination("property", "a").followedBy("property", "b"));
    }

    @Test
    public void twoRulesWithValuesAandB() {
        ruleEngine.addRule(iterate("x").over("a"));
        ruleEngine.addRule(iterate("y").over("b"));

        generateCombinations();

        expect(combination("x", "a", "y", "b"));
    }

    @Test
    public void twoRulesWithValuesA1_A2andB1_B2() {

        ruleEngine.addRule(iterate("x").over("a1", "a2"));
        ruleEngine.addRule(iterate("y").over("b1", "b2"));

        generateCombinations();

        expect(combination("x", "a1", "y", "b1") //
            .followedBy("x", "a2", "y", "b2"));
    }

    @Test
    public void twoRulesWithValuesA1_A2_A3andB1_B2() {

        ruleEngine.addRule(iterate("x").over("a1", "a2", "a3"));
        ruleEngine.addRule(iterate("y").over("b1", "b2"));

        generateCombinations();

        expect(combination("x", "a1", "y", "b1") //
            .followedBy("x", "a2", "y", "b2").followedBy("x", "a3", "y", "b1"));
    }

    @Test
    public void triggeringAndTriggeredRulesWithSingleValues() {

        ruleEngine.addRule(iterate("x").over("a"));
        ruleEngine.addRule(when("x").iterate("y").over("b"));

        generateCombinations();

        expect(combination("x", "a", "y", "b"));
    }

    @Test
    public void triggeringAndTriggeredRulesWithValuesA_B_and_C_D() {
        ruleEngine.addRule(iterate("x").over("a", "b"));
        ruleEngine.addRule(when("x").iterate("y").over("c", "d"));

        generateCombinations();

        expect(combination("x", "a", "y", "c")
            .followedBy("x", "a", "y", "d").followedBy("x", "b", "y", "c")
            .followedBy("x", "b", "y", "d"));

    }

    @Test
    public void triggeringValueAndConditionallyTriggeredValues() {
        ruleEngine.addRule(iterate("x").over("a", "b", "c"));
        ruleEngine.addRule(when("x").iterate("y").over("A", "B")._if( //
            new Condition() {
                @Override
                public boolean isSatisfied() {
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
        ruleEngine.addRule(iterate("x").over("a")._if(FALSE));

        generateCombinations();

        expect(combination());
    }

    @Test
    public void newRuleForTheSamePropertyWithNotFulfilledCondition_IsIgnored() {
        ruleEngine.addRule(iterate("x").over("a"));
        ruleEngine.addRule(iterate("x").over("b")._if(FALSE));

        generateCombinations();

        expect(combination("x", "a"));
    }

    @Test
    public void thirdRuleForTheSameProperty_HidesOldRules() {
        ruleEngine.addRule(iterate("x").over("a"));
        ruleEngine.addRule(iterate("x").over("b"));
        ruleEngine.addRule(iterate("x").over("c"));

        generateCombinations();

        expect(combination("x", "c"));
    }

    @Test
    public void thirdRuleForTheSamePropertyWithNotFulfilledCondition_IsIgnored() {
        ruleEngine.addRule(iterate("x").over("a"));
        ruleEngine.addRule(iterate("x").over("b"));
        ruleEngine.addRule(iterate("x").over("c")._if(FALSE));

        generateCombinations();

        expect(combination("x", "b"));
    }

    @Test
    public void triggeringAndOverwrittenTriggeredRulesWithSingleValues() {

        ruleEngine.addRule(iterate("x").over("a"));
        ruleEngine.addRule(when("x").iterate("y").over("b"));
        ruleEngine.addRule(when("x").iterate("y").over("c"));
        ruleEngine.addRule(when("x").iterate("y").over("d"));

        generateCombinations();

        expect(combination("x", "a", "y", "d"));
    }

    @Test
    public void ruleAndRuleWithDependentCondition() {

        ruleEngine.addRule(iterate("x").over("a1", "a2"));
        ruleEngine.addRule(iterate("y").over("b1", "b2")._if(new Condition() {

            @Override
            public boolean isSatisfied() {
                ruleEngine.get("x");
                return true;
            }
        }));

        generateCombinations();

        expect(combination("x", "a1", "y", "b1") //
            .followedBy("x", "a1", "y", "b2").followedBy("x", "a2", "y", "b1").followedBy("x", "a2", "y", "b2"));
    }
}
