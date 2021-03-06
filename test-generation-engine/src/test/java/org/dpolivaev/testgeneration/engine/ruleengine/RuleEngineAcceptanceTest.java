package org.dpolivaev.testgeneration.engine.ruleengine;

import static org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder.Factory.iterate;
import static org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder.Factory.rule;
import static org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder.Factory.when;
import static org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue.UNDEFINED;
import static org.dpolivaev.testgeneration.engine.testutils.Combinations.combination;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyAlreadyAssignedException;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleEngine;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.testutils.CollectingScriptProducer;
import org.dpolivaev.testgeneration.engine.testutils.Combinations;
import org.dpolivaev.testgeneration.engine.utils.internal.Utils;
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
        ruleEngine = new RuleEngine().addHandler(scriptProducerMock);
    }
    
    @Before
    public void setup() {
        CollectingScriptProducer loggingScriptProducerMock = new CollectingScriptProducer();
        loggingScriptProducerMock.excludeUndefined(false);
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
    public void singleRuleWithUnsatisfiedCondition_doesNotRequireValues() {
        strategy.addRule(iterate("x")._if(FALSE));
        generateCombinationsForStrategy();
        expect(combination());
    }

    @Test
    public void singleRuleWithValuesA_B() {
        strategy.addRule(iterate("property").over("a", "b"));

        generateCombinationsForStrategy();

        expect(combination("property", "a").followedBy("property", "b"));
    }


    @Test
    public void singleRuleWithValuesA_SKIP_B() {
        strategy.addRule(iterate("property").over("a", SpecialValue.SKIP, "b"));

        generateCombinationsForStrategy();

        expect(combination("property", "a").followedBy("property", "b"));
    }

    @Test
    public void singleRuleWithValuesA_B_SKIP() {
        strategy.addRule(iterate("property").over("a", "b", SpecialValue.SKIP).ordered());

        generateCombinationsForStrategy();

        expect(combination("property", "a").followedBy("property", "b").followedBy("property", "a"));
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

        strategy.addRule(iterate("x").over("a1", "a2", "a3").ordered());
        strategy.addRule(iterate("y").over("b1", "b2").ordered());

        generateCombinationsForStrategy();

        expect(combination("x", "a1", "y", "b1") //
            .followedBy("x", "a2", "y", "b2").followedBy("x", "a3", "y", "b1"));
    }

    @Test
    public void twoTriggeredRulesWithValuesA1_A2_A3andB1_B2() {

        strategy.addRule(iterate("x").over("1"));
        strategy.addRule(iterate("y").over("a1", "a2", "a3").ordered().when("x"));
        strategy.addRule(iterate("z").over("b1", "b2").ordered().when("x"));

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
        strategy.addRule(iterate("x").over("a", "b").ordered());
        strategy.addRule(when("x").iterate("y").over("c", "d").ordered());

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "c")
            .followedBy("x", "a", "y", "d").followedBy("x", "b", "y", "c")
            .followedBy("x", "b", "y", "d"));

    }

    @Test
    public void twoTriggeringAndTriggeredRules() {
        strategy.addRule(iterate("x1").over("a", "b").ordered());
        strategy.addRule(when("x1").iterate("x2").over("c").ordered());
        strategy.addRule(when("x1", "x2").iterate("y").over("c", "d").ordered());

        generateCombinationsForStrategy();

        expect(combination("x1", "a", "x2", "c", "y", "c")
            .followedBy("x1", "a", "x2", "c", "y", "d")
            .followedBy("x1", "b", "x2", "c", "y", "c")
            .followedBy("x1", "b", "x2", "c", "y", "d"));

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
        strategy.addRule(rule("x").iterate("x").over("a"));
        strategy.addRule(rule("x").iterate("x").over("b"));

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
        strategy.addRule(rule("x").iterate("x").over("a"));
        strategy.addRule(rule("x").iterate("x").over("b"));
        strategy.addRule(rule("x").iterate("x").over("c"));

        generateCombinationsForStrategy();

        expect(combination("x", "c"));
    }

    @Test
    public void thirdRuleForTheSamePropertyWithNotFulfilledCondition_IsIgnored() {
        strategy.addRule(rule("x").iterate("x").over("a"));
        strategy.addRule(rule("x").iterate("x").over("b"));
        strategy.addRule(rule("x").iterate("x").over("c")._if(FALSE));

        generateCombinationsForStrategy();

        expect(combination("x", "b"));
    }

    @Test
    public void triggeringAndOverwrittenTriggeredRulesWithSingleValues() {

        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(rule("y").when("x").iterate("y").over("b"));
        strategy.addRule(rule("y").when("x").iterate("y").over("c"));
        strategy.addRule(rule("y").when("x").iterate("y").over("d"));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "d"));
    }

    @Test
    public void ruleAndRuleWithDependentCondition() {

        strategy.addRule(iterate("x").over("a1", "a2").ordered());
        strategy.addRule(iterate("y").over("b1", "b2").ordered().when("x")._if(new Condition() {

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
        Rule ruleToBeRemoved = iterate("x").over("b").create();
        strategy.addRule(ruleToBeRemoved);
        strategy.removeRule(ruleToBeRemoved);

        generateCombinationsForStrategy();

        expect(combination());
    }

    @Test
    public void removedRuleDoesNotHideOtherRules() {
        strategy.addRule(iterate("x").over("a"));
        Rule ruleToBeRemoved = iterate("x").over("b").create();
        strategy.addRule(ruleToBeRemoved);
        strategy.removeRule(ruleToBeRemoved);

        generateCombinationsForStrategy();

        expect(combination("x", "a"));
    }

    @Test
    public void ruleManagesValueSpecificTemporaryRules() {
        RuleBuilder temporaryRule = iterate("y").over("1", "2").ordered();
        strategy.addRule(iterate("x").over("a", "b").ordered().with(temporaryRule));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "1").followedBy("x", "a", "y", "2") //
            .followedBy("x", "b", "y", "1").followedBy("x", "b", "y", "2"));
    }

    @Test
    public void valueSpecificTemporaryRulesAreRemovedAfterTheRelatedValueIsFinished() {
        RuleBuilder temporaryRule = iterate("y").over("1").when("x");
        strategy.addRule(iterate("x").over("a").with(temporaryRule).over("b"));

        generateCombinationsForStrategy();

        expect(combination("x", "a", "y", "1").followedBy("x", "b"));
    }

    @Test
    public void ruleEngineWithOneLazyRule_doesNotIterateOverItsValueIfPropertyIsNotRequested() {
        strategy.addRule(iterate("property").over("value").asLazyRule());
        generateCombinationsForStrategy();

        expect(combination());
    }

    @Test
    public void ruleEngineWithOneLazyRule_iteratesOverItsValuesWhenPropertyIsRequested() {
        strategy.addRule(iterate("x").over("1", "2"));
        strategy.addRule(iterate("y").over("1", "2", "3").asLazyRule());
        initializeRuleEngine(new CollectingScriptProducer() {

            @Override
            public void handlePropertyCombination(PropertyContainer propertyContainer) {
                propertyContainer.get("y");
                super.handlePropertyCombination(propertyContainer);
            }

        });
        generateCombinationsForStrategy();

        expect(combination("x", "1", "y", "1").followedBy("x", "2", "y", "2"));
    }

    @Test
    public void lazyRule_doesNotTriggerOtherIterations() {
        strategy.addRule(iterate("x").over("1", "2").asLazyRule());
        strategy.addRule(iterate("y").over("1", "2").when("x"));
        initializeRuleEngine(new CollectingScriptProducer() {

            @Override
            public void handlePropertyCombination(PropertyContainer propertyContainer) {
                propertyContainer.get("x");
                super.handlePropertyCombination(propertyContainer);
            }

        });
        generateCombinationsForStrategy();

        expect(combination("x", "1"));
    }

    @Test
    public void triggeredRuleWithSkippingValues() {

        strategy.addRule(iterate("x").over("a1", "a2"));
        strategy.addRule(when("x").skip()._if(new Condition() {

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
        strategy.addRule(iterate("y").over("a1"));
        strategy.addRule(when("x").iterate("y").over("a2"));
        generateCombinationsForStrategy();
    }

    @Test
    public void disableAlreadyAssignedProperty() {
        strategy.addRule(iterate("x").over("a1"));
        strategy.addRule(rule("y").iterate("y").over("a1"));
        strategy.addRule(rule("y").iterate("y").disable());
        strategy.addRule(rule("y").when("x").iterate("y").over("a2"));
        generateCombinationsForStrategy();
        expect(combination("x", "a1", "y", "a2"));
    }

    @Test
    public void overwriteDisabledRule() {
        strategy.addRule(iterate("x").over("a1"));
        strategy.addRule(iterate("y").disable());
        strategy.addRule(iterate("y").over("a1"));
        generateCombinationsForStrategy();
        expect(combination("x", "a1", "y", "a1"));
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
    public void triggeringAndLazyRules() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("a").asLazyRule());
        strategy.addRule(iterate("askingX").over(new ValueProvider(){

			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }));
        strategy.addRule(iterate("y").over("a"));
        strategy.addRule(when("x", "y").iterate("z").over("b"));

        generateCombinationsForStrategy();
        expect(combination("askingX<-x", "a", "->askingX", "a", "->y", "a"));
    }

    @Test
    public void lazyRuleCalledFromScriptProducerWithReason() {
        strategy.addRule(iterate("x").over("1").asLazyRule());
        CollectingScriptProducer loggingScriptProducerMock = new CollectingScriptProducer() {

            @Override
            public void handlePropertyCombination(PropertyContainer propertyContainer) {
                propertyContainer.get("x");
                super.handlePropertyCombination(propertyContainer);
            }

        };
        initializeRuleEngine(loggingScriptProducerMock);
        loggingScriptProducerMock.appendReasons(true);
        generateCombinationsForStrategy();

        expect(combination("<-x", "1"));
    }

    @Test
    public void lazyRuleCalledFromTriggeredRuleWithReason() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("1").asLazyRule());
        strategy.addRule(iterate("y").over(new ValueProvider() {
		    @Override
		    public Object value(PropertyContainer propertyContainer) {
		        return propertyContainer.get("x");
		    }
		}));
        generateCombinationsForStrategy();

        expect(combination("y<-x", "1", "->y", "1"));
    }
    
    @Test
    public void returnsUndefined_ifNoRuleIsSatisfied(){
        PropertyHandler scriptProducer = mock(PropertyHandler.class);
        final RuleEngine ruleEngine = new RuleEngine().addHandler(scriptProducer);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                PropertyContainer propertyContainer = (PropertyContainer) invocation.getArguments()[0];
                assertThat(propertyContainer.get("name"), equalTo((Object)UNDEFINED));
                return null;
            }
        }).when(scriptProducer).handlePropertyCombination(ruleEngine);
        ruleEngine.run(strategy);
    }
    
    @Test
    public void returnsNamesOfAssignedAndLazyProperties(){
        strategy.addRule(iterate("x1").over("1"));
        strategy.addRule(iterate("x2").over("2").asLazyRule());
        strategy.addRule(iterate("y").over("2").asLazyRule());
        PropertyHandler scriptProducer = mock(PropertyHandler.class);
        final RuleEngine ruleEngine = new RuleEngine().addHandler(scriptProducer);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                PropertyContainer propertyContainer = (PropertyContainer) invocation.getArguments()[0];
                assertThat(propertyContainer.availableProperties("x"), equalTo(Utils.set("x1", "x2")));
                return null;
            }
        }).when(scriptProducer).handlePropertyCombination(ruleEngine);
        ruleEngine.run(strategy);
    }

    @Test
    public void temporaryRuleWithPropertyNameDependentOnCreatingRuleValue() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("y").with(iterate(new ValueProvider() {
		    @Override
		    public Object value(PropertyContainer propertyContainer) {
		        return propertyContainer.get("x");
		    }
		}).over("1")));
        generateCombinationsForStrategy();
        expect(combination("->x", "y", "x->y", "1"));
    }

    @Test(expected=PropertyAlreadyAssignedException.class)
    public void newSatisfiedLazyRules() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("a").asLazyRule());
        strategy.addRule(iterate("askingX").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }).with(iterate("x").over("a").asLazyRule()));
        strategy.addRule(iterate("askingXAgain").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }).with(iterate("x").over("a").asLazyRule()));

        generateCombinationsForStrategy();
    }

    @Test
    public void alreadySatisfiedLazyRules() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("a").asLazyRule());
        strategy.addRule(iterate("askingX").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }));
        strategy.addRule(iterate("askingXAgain").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }).with(iterate("x").over("a").asLazyRule()));

        generateCombinationsForStrategy();
        expect(combination("askingX<-x", "a", "->askingX", "a", "->askingXAgain", "a"));
    }


    @Test
    public void alreadySatisfiedLazyRulesWithAlternatingRules() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("a").asLazyRule());
        strategy.addRule(iterate("x").over("a").asLazyRule());
        strategy.addRule(iterate("askingX").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }));
        strategy.addRule(iterate("askingXAgain").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }).with(iterate("x").over("a").asLazyRule()));

        generateCombinationsForStrategy();
        expect(combination("askingX<-x", "a", "->askingX", "a", "->askingXAgain", "a"));
    }
    
    @Test
    public void alreadySatisfiedTriggeredRuleWithLazyRules() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(iterate("x").over("a").asLazyRule());
        strategy.addRule(iterate("askingX").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }));
        strategy.addRule(iterate("askingXAgain").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }).with(iterate("x").over("a").asLazyRule()));

        generateCombinationsForStrategy();
        expect(combination("->x", "a", "->askingX", "a", "->askingXAgain", "a"));
    }
    @Test
    public void alreadyAskedUndefinedProperties() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("askingX").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }));
        strategy.addRule(iterate("askingXAgain").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }).with(iterate("x").over("a").asLazyRule()));

        generateCombinationsForStrategy();
        expect(combination("askingX<-x", "UNDEFINED", "->askingX", "UNDEFINED", "->askingXAgain", "UNDEFINED"));
    }
    
    @Test(expected=PropertyAlreadyAssignedException.class)
    public void triggeredLazyRuleConflictAlreadySatisfiedLazyRule() {
        scriptProducerMock.appendReasons(true);
        strategy.addRule(iterate("x").over("a").asLazyRule());
        strategy.addRule(when("askingX").iterate("x").over("a").asLazyRule());
        strategy.addRule(iterate("askingX").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }));
        strategy.addRule(iterate("askingXAgain").over(new ValueProvider(){
			@Override
			public Object value(PropertyContainer propertyContainer) {
				return propertyContainer.get("x");
			}

        }).with(iterate("x").over("a").asLazyRule()));

        generateCombinationsForStrategy();
    }
}
