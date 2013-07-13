package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ruleengine.Combinations.combination;
import static ruleengine.StatefulRuleBuilder.Factory.iterate;
import static ruleengine.StatefulRuleBuilder.Factory.when;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngineAcceptanceTest {

    private RuleEngine ruleEngine;
    private Strategy strategy;
    private LoggingScriptProducerMock scriptProducerMock;
    private static final Condition FALSE = new Condition() {
        @Override
        public boolean isSatisfied() {
            return false;
        }
    };

    public RuleEngineAcceptanceTest() {
    }

    private void generateCombinationsForStrategy() {
        ruleEngine.run(strategy);
    }

    private void expect(Combinations expectedCombinations) {
        assertEquals(expectedCombinations.toString(),
            scriptProducerMock.getAllScriptPropertyCombinations());
    }

    @Before
    public void setup() {
        initializeRuleEngine(new LoggingScriptProducerMock());
        strategy = new Strategy();
    }

    private void initializeRuleEngine(LoggingScriptProducerMock loggingScriptProducerMock) {
        scriptProducerMock = loggingScriptProducerMock;
        ruleEngine = new RuleEngine(scriptProducerMock);
    }

    @Test
    public void ruleEngineWithoutRules_createsSingleCombinationWithoutValues() {
        generateCombinationsForStrategy();
        expect(combination());
    }

    @Test
    public void singleRuleWithPropertyNamedXValueA_callsScriptProducerWithValueA() {
        strategy.addRule(iterate("x").over("a"));

        generateCombinationsForStrategy();

        expect(combination("x", "a"));
    }

    @Test
    public void singleRuleWithValuesA_B() {
        strategy.addRule(iterate("property").over("a", "b"));

        generateCombinationsForStrategy();

        expect(combination("property", "a").followedBy("property", "b"));
    }

    @Test
    public void twoRulesWithValuesAandB() {
        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(iterate("y").over("b"));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "b"));
    }

    @Test
    public void twoRulesWithValuesA1_A2andB1_B2() {

        strategy.addRule(iterate("x").over("a1", "a2"));
        strategy.addRule(iterate("y").over("b1", "b2"));

        generateCombinationsForStrategy();

        expect(combination("x", "a1", "y", "b1") //
            .followedBy("x", "a2", "y", "b2"));
    }

    @Test
    public void twoRulesWithValuesA1_A2_A3andB1_B2() {

        strategy.addRule(iterate("x").over("a1", "a2", "a3"));
        strategy.addRule(iterate("y").over("b1", "b2"));

        generateCombinationsForStrategy();

        expect(combination("x", "a1", "y", "b1") //
            .followedBy("x", "a2", "y", "b2").followedBy("x", "a3", "y", "b1"));
    }

    @Test
    public void triggeringAndTriggeredRulesWithSingleValues() {

        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(when("x").iterate("y").over("b"));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "b"));
    }

    @Test
    public void triggeringAndTriggeredRulesWithValuesA_B_and_C_D() {
        strategy.addRule(iterate("x").over("a", "b"));
        strategy.addRule(when("x").iterate("y").over("c", "d"));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "c")
            .followedBy("x", "a", "y", "d").followedBy("x", "b", "y", "c")
            .followedBy("x", "b", "y", "d"));

    }

    @Test
    public void triggeringValueAndConditionallyTriggeredValues() {
        strategy.addRule(iterate("x").over("a", "b", "c"));
        strategy.addRule(when("x").iterate("y").over("A", "B")._if( //
            new Condition() {
                @Override
                public boolean isSatisfied() {
                    return ruleEngine.get("x").equals("c");
                };
            }));
        generateCombinationsForStrategy();

        expect(combination("x", "a").followedBy("x", "b")
            .followedBy("x", "c", "y", "A").followedBy("x", "c", "y", "B"));
    }

    @Test
    public void newRuleForTheSameProperty_HidesOldRule() {
        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(iterate("x").over("b"));

        generateCombinationsForStrategy();

        expect(combination("x", "b"));
    }

    @Test
    public void topRuleWithNotFulfilledCondition_IsIgnored() {
        strategy.addRule(iterate("x").over("a")._if(FALSE));

        generateCombinationsForStrategy();

        expect(combination());
    }

    @Test
    public void newRuleForTheSamePropertyWithNotFulfilledCondition_IsIgnored() {
        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(iterate("x").over("b")._if(FALSE));

        generateCombinationsForStrategy();

        expect(combination("x", "a"));
    }

    @Test
    public void thirdRuleForTheSameProperty_HidesOldRules() {
        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(iterate("x").over("b"));
        strategy.addRule(iterate("x").over("c"));

        generateCombinationsForStrategy();

        expect(combination("x", "c"));
    }

    @Test
    public void thirdRuleForTheSamePropertyWithNotFulfilledCondition_IsIgnored() {
        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(iterate("x").over("b"));
        strategy.addRule(iterate("x").over("c")._if(FALSE));

        generateCombinationsForStrategy();

        expect(combination("x", "b"));
    }

    @Test
    public void triggeringAndOverwrittenTriggeredRulesWithSingleValues() {

        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(when("x").iterate("y").over("b"));
        strategy.addRule(when("x").iterate("y").over("c"));
        strategy.addRule(when("x").iterate("y").over("d"));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "d"));
    }

    @Test
    public void ruleAndRuleWithDependentCondition() {

        strategy.addRule(iterate("x").over("a1", "a2"));
        strategy.addRule(iterate("y").over("b1", "b2")._if(new Condition() {

            @Override
            public boolean isSatisfied() {
                ruleEngine.get("x");
                return true;
            }
        }));

        generateCombinationsForStrategy();

        expect(combination("x", "a1", "y", "b1") //
            .followedBy("x", "a1", "y", "b2").followedBy("x", "a2", "y", "b1").followedBy("x", "a2", "y", "b2"));
    }

    @Test
    public void removedRuleDoesIsNotConsidered() {
        StatefulRule ruleToBeRemoved = iterate("x").over("b").asRule();
        strategy.addRule(ruleToBeRemoved);
        strategy.removeRule(ruleToBeRemoved);

        generateCombinationsForStrategy();

        expect(combination());
    }

    @Test
    public void removedRuleDoesNotHideOtherRules() {
        strategy.addRule(iterate("x").over("a"));
        StatefulRule ruleToBeRemoved = iterate("x").over("b").asRule();
        strategy.addRule(ruleToBeRemoved);
        strategy.removeRule(ruleToBeRemoved);

        generateCombinationsForStrategy();

        expect(combination("x", "a"));
    }

    @Test
    public void ruleManagesValueSpecificTemporaryRules() {
        StatefulRuleBuilder temporaryRule = iterate("y").over("1", "2").when("x");
        strategy.addRule(iterate("x").with("a", temporaryRule.asRule()));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "1").followedBy("x", "a", "y", "2"));
    }

    @Test
    public void valueSpecificTemporaryRulesAreRemovedAfterTheRelatedValueIsFinished() {
        StatefulRuleBuilder temporaryRule = iterate("y").over("1").when("x");
        strategy.addRule(iterate("x").with("a", temporaryRule.asRule()).with("b"));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "1").followedBy("x", "b"));
    }

    @Test
    public void ruleEngineWithOneDefaultRule_doesNotIterateOverItsValueIfPropertyIsNotRequested() {
        strategy.addRule(iterate("property").over("value").byDefault());
        generateCombinationsForStrategy();

        expect(combination());
    }

    @Test
    public void ruleEngineWithOneDefaultRule_iteratesOverItsValuesWhenPropertyIsRequested() {
        strategy.addRule(iterate("x").over("1", "2"));
        strategy.addRule(iterate("y").over("1", "2", "3").byDefault());
        initializeRuleEngine(new LoggingScriptProducerMock() {

            @Override
            public void makeScriptFor(RuleEngine ruleEngine) {
                ruleEngine.get("y");
                super.makeScriptFor(ruleEngine);
            }

        });
        generateCombinationsForStrategy();

        expect(combination("x", "1", "y", "1").followedBy("x", "2", "y", "2"));
    }

    @Test
    public void defaultRule_doesNotTriggerOtherIterations() {
        strategy.addRule(iterate("x").over("1", "2").byDefault());
        strategy.addRule(iterate("y").over("1", "2").when("x"));
        initializeRuleEngine(new LoggingScriptProducerMock() {

            @Override
            public void makeScriptFor(RuleEngine ruleEngine) {
                ruleEngine.get("x");
                super.makeScriptFor(ruleEngine);
            }

        });
        generateCombinationsForStrategy();

        expect(combination("x", "1"));
    }

}
