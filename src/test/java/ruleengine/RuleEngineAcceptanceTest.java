package ruleengine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static ruleengine.Combinations.combination;
import static ruleengine.SpecialValues.UNDEFINED;
import static ruleengine.RuleBuilder.Factory.iterate;
import static ruleengine.RuleBuilder.Factory.when;
import static ruleengine.TestUtils.set;

import static ruleengine.ConstantValue.Instruction.SKIP;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngineAcceptanceTest {

    private RuleEngine ruleEngine;
    private Strategy strategy;
    private CollectingScriptProducer scriptProducerMock;
    private static final Condition FALSE = new Condition() {
        @Override
        public boolean isSatisfied(PropertyContainer propertyContainer) {
            return false;
        }
    };

    private void generateCombinationsForStrategy() {
        ruleEngine.run(strategy);
    }

    private void expect(Combinations expectedCombinations) {
        assertEquals(expectedCombinations.toString(),
            scriptProducerMock.getAllScriptPropertyCombinations());
    }

    private void initializeRuleEngine(CollectingScriptProducer loggingScriptProducerMock) {
        scriptProducerMock = loggingScriptProducerMock;
        ruleEngine = new RuleEngine(scriptProducerMock);
    }
    
    @Before
    public void setup() {
        CollectingScriptProducer loggingScriptProducerMock = new CollectingScriptProducer();
        initializeRuleEngine(loggingScriptProducerMock);
        strategy = new Strategy();
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
    public void twoTriggeredRulesWithValuesA1_A2_A3andB1_B2() {

        strategy.addRule(iterate("x").over("1"));
        strategy.addRule(iterate("y").over("a1", "a2", "a3").when("x"));
        strategy.addRule(iterate("z").over("b1", "b2").when("x"));

        generateCombinationsForStrategy();

        expect(combination("x", "1", "y", "a1", "z", "b1") //
            .followedBy("x", "1", "y", "a2", "z", "b2").followedBy("x", "1", "y", "a3", "z", "b1"));
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
                public boolean isSatisfied(PropertyContainer propertyContainer) {
                    return propertyContainer.get("x").equals("c");
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
        strategy.addRule(iterate("y").over("b1", "b2").when("x")._if(new Condition() {

            @Override
            public boolean isSatisfied(PropertyContainer propertyContainer) {
                propertyContainer.get("x");
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
        RuleBuilder temporaryRule = iterate("y").over("1", "2");
        strategy.addRule(iterate("x").over("a", "b").with(temporaryRule));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "1").followedBy("x", "a", "y", "2") //
            .followedBy("x", "b", "y", "1").followedBy("x", "b", "y", "2"));
    }

    @Test
    public void valueSpecificTemporaryRulesAreRemovedAfterTheRelatedValueIsFinished() {
        RuleBuilder temporaryRule = iterate("y").over("1").when("x");
        strategy.addRule(iterate("x").over("a").with(temporaryRule.asRule()).over("b"));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "1").followedBy("x", "b"));
    }

    @Test
    public void ruleEngineWithOneDefaultRule_doesNotIterateOverItsValueIfPropertyIsNotRequested() {
        strategy.addRule(iterate("property").over("value").asDefaultRule());
        generateCombinationsForStrategy();

        expect(combination());
    }

    @Test
    public void ruleEngineWithOneDefaultRule_iteratesOverItsValuesWhenPropertyIsRequested() {
        strategy.addRule(iterate("x").over("1", "2"));
        strategy.addRule(iterate("y").over("1", "2", "3").asDefaultRule());
        initializeRuleEngine(new CollectingScriptProducer() {

            @Override
            public void makeScriptFor(PropertyContainer propertyContainer) {
                propertyContainer.get("y");
                super.makeScriptFor(propertyContainer);
            }

        });
        generateCombinationsForStrategy();

        expect(combination("x", "1", "y", "1").followedBy("x", "2", "y", "2"));
    }

    @Test
    public void defaultRule_doesNotTriggerOtherIterations() {
        strategy.addRule(iterate("x").over("1", "2").asDefaultRule());
        strategy.addRule(iterate("y").over("1", "2").when("x"));
        initializeRuleEngine(new CollectingScriptProducer() {

            @Override
            public void makeScriptFor(PropertyContainer propertyContainer) {
                propertyContainer.get("x");
                super.makeScriptFor(propertyContainer);
            }

        });
        generateCombinationsForStrategy();

        expect(combination("x", "1"));
    }

    @Test
    public void triggeredRuleWithSkippingValues() {

        strategy.addRule(iterate("x").over("a1", "a2"));
        strategy.addRule(when("x").iterate("y").over(SKIP)._if(new Condition() {

            @Override
            public boolean isSatisfied(PropertyContainer propertyContainer) {
                return propertyContainer.get("x").equals("a1");
            }
        }));

        generateCombinationsForStrategy();

        expect(new Combinations().skip().followedBy("x", "a2"));
    }

    @Test(expected = PropertyAlreadyAssignedException.class)
    public void assigningAlreadyAssignedProperty_throwsException() {
        strategy.addRule(iterate("x").over("a1"));
        strategy.addRule(when("x").iterate("x").over("a2"));
        generateCombinationsForStrategy();
    }

    @Test
    public void singleRuleWithPropertyNamedXValueAWithReason() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("a"));

        generateCombinationsForStrategy();

        expect(combination("->x", "a"));
    }

    @Test
    public void triggeringAndTriggeredRulesWithReasons() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(when("x").iterate("y").over("b"));

        generateCombinationsForStrategy();

        expect(combination("->x", "a", "x->y", "b"));
    }

    @Test
    public void defaultRuleCalledFromScriptProducerWithReason() {
        strategy.addRule(iterate("x").over("1").asDefaultRule());
        CollectingScriptProducer loggingScriptProducerMock = new CollectingScriptProducer() {

            @Override
            public void makeScriptFor(PropertyContainer propertyContainer) {
                propertyContainer.get("x");
                super.makeScriptFor(propertyContainer);
            }

        };
        initializeRuleEngine(loggingScriptProducerMock);
        loggingScriptProducerMock.appendReasons(true);
        generateCombinationsForStrategy();

        expect(combination("<-x", "1"));
    }

    @Test
    public void defaultRuleCalledFromTriggeredRuleWithReason() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("1").asDefaultRule());
        strategy.addRule(iterate("y").over(new ValueProvider() {
            @Override
            public Object value(PropertyContainer propertyContainer) {
                return propertyContainer.get("x");
            }
        }).asRule());
        generateCombinationsForStrategy();

        expect(combination("y<-x", "1", "->y", "1"));
    }
    
    @Test
    public void returnsUndefined_ifNoRuleIsSatisfied(){
        ScriptProducer scriptProducer = mock(ScriptProducer.class);
        final RuleEngine ruleEngine = new RuleEngine(scriptProducer);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                PropertyContainer propertyContainer = (PropertyContainer) invocation.getArguments()[0];
                assertThat(propertyContainer.get("name"), equalTo((Object)UNDEFINED));
                return null;
            }
        }).when(scriptProducer).makeScriptFor(ruleEngine);
        ruleEngine.run(strategy);
    }
    
    @Test
    public void returnsNamesOfAssignedAndDefaultProperties(){
        strategy.addRule(iterate("x1").over("1"));
        strategy.addRule(iterate("x2").over("2").asDefaultRule());
        strategy.addRule(iterate("y").over("2").asDefaultRule());
        ScriptProducer scriptProducer = mock(ScriptProducer.class);
        final RuleEngine ruleEngine = new RuleEngine(scriptProducer);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                PropertyContainer propertyContainer = (PropertyContainer) invocation.getArguments()[0];
                assertThat(propertyContainer.availableProperties("x"), equalTo(set("x1", "x2")));
                return null;
            }
        }).when(scriptProducer).makeScriptFor(ruleEngine);
        ruleEngine.run(strategy);
    }
}
