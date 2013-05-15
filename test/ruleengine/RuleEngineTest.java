package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


import org.junit.Test;


/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngineTest {

    private RuleEngine ruleEngine = new RuleEngine();

    @Test
    public void ruleEngineWithoutRules_callsScriptProducerOnce() {
        CountingScriptProducerMock scriptProducerMock = new CountingScriptProducerMock();
        ruleEngine.run(scriptProducerMock);
        assertThat(scriptProducerMock.callCount(), is(1));
    }

    @Test
    public void ruleEngineWithOneIterationRule_hasRuleForItsTargetedProperty() {
        String targetedPropertyName = "property";
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty(targetedPropertyName) //
            .withValues("value").build());

        assertThat(ruleEngine.hasRuleForProperty(targetedPropertyName),
            is(true));
    }

    public StatefulRuleBuilder ruleBuilder() {
        return new StatefulRuleBuilder();
    }

    @Test
    public void ruleEngineWithTwoIterationRules_hasRulesForItsTargetedProperties() {
        String firstTargetedPropertyName = "property1";
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty(firstTargetedPropertyName) //
            .withValues("value").build());
        String secondTargetedPropertyName = "property2";
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty(secondTargetedPropertyName) //
            .withValues("value").build());

        assertThat(ruleEngine.hasRuleForProperty(firstTargetedPropertyName),
            is(true));
        assertThat(ruleEngine.hasRuleForProperty(secondTargetedPropertyName),
            is(true));
    }

    @Test
    public void singleRuleWithPropertyNamedXValueA_callsScriptProducerWithValueA() {
        LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("x") //
            .withValues("a").build());

        ruleEngine.run(scriptProducerMock);

        String expectedScriptPropertyCombinations = "1 : x=a\n";
        assertEquals(expectedScriptPropertyCombinations,
            scriptProducerMock.getAllScriptPropertyCombinations());

    }

    @Test
    public void singleRuleWithValuesA_B_callsScriptProducerWithValuesA_B() {
        LoggingScriptProducerMock loggingScriptProducerMock = new LoggingScriptProducerMock();
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("property") //
            .withValues("a", "b").build());

        ruleEngine.run(loggingScriptProducerMock);

        String expectedScriptPropertyCombinations = "1 : property=a\n"
            + "2 : property=b\n";
        assertEquals(expectedScriptPropertyCombinations,
            loggingScriptProducerMock.getAllScriptPropertyCombinations());

    }

    @Test
    public void twoRulesWithValuesAandB_callsScriptProducerWithTheirValues() {
        LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("x") //
            .withValues("a").build());
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("y") //
            .withValues("b").build());

        ruleEngine.run(scriptProducerMock);

        String expectedScriptPropertyCombinations = "1 : x=a\ty=b\n";
        assertEquals(expectedScriptPropertyCombinations,
            scriptProducerMock.getAllScriptPropertyCombinations());

    }

    @Test
    public void twoRulesWithValuesA1_A2andB1_B2_callsScriptProducerWithTheirValues() {
        LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("x") //
            .withValues("a1", "a2").build());
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("y") //
            .withValues("b1", "b2").build());

        ruleEngine.run(scriptProducerMock);

        String expectedScriptPropertyCombinations = "1 : x=a1\ty=b1\n"
            + "2 : x=a2\ty=b2\n";
        assertEquals(expectedScriptPropertyCombinations,
            scriptProducerMock.getAllScriptPropertyCombinations());

    }

    @Test
    public void twoRulesWithValuesA1_A2_A3andB1_B2_callsScriptProducerWithTheirValues() {
        LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("x") //
            .withValues("a1", "a2", "a3").build());
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("y") //
            .withValues("b1", "b2").build());

        ruleEngine.run(scriptProducerMock);

        String expectedScriptPropertyCombinations = "1 : x=a1\ty=b1\n"
            + "2 : x=a2\ty=b2\n" + "3 : x=a3\ty=b1\n";
        assertEquals(expectedScriptPropertyCombinations,
            scriptProducerMock.getAllScriptPropertyCombinations());

    }

    @Test
    public void triggeringAndTriggeredRulesWithSingleValues_callsScriptProducerWithTheirValues() {
        LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("x") //
            .withValues("a").build());
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("y") //
            .withValues("b") //
            .withTriggeringProperties("x").build());

        ruleEngine.run(scriptProducerMock);

        String expectedScriptPropertyCombinations = "1 : x=a\ty=b\n";
        assertEquals(expectedScriptPropertyCombinations,
            scriptProducerMock.getAllScriptPropertyCombinations());

    }

    @Test
    public void triggeringAndTriggeredRulesWithValuesA_B_and_C_D_callsScriptProducerWithTheirValues() {
        LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("x") //
            .withValues("a", "b").build());
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("y") //
            .withValues("c", "d").withTriggeringProperties("x").build());

        ruleEngine.run(scriptProducerMock);

        String expectedScriptPropertyCombinations = "1 : x=a\ty=c\n"
            + "2 : x=a\ty=d\n" + "3 : x=b\ty=c\n" + "4 : x=b\ty=d\n";
        assertEquals(expectedScriptPropertyCombinations,
            scriptProducerMock.getAllScriptPropertyCombinations());

    }

    @Test
    public void triggeringValueAndConditionallyTriggeredValues_callsScriptProducerWithTheirValues() {
        LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("x") //
            .withValues("a", "b", "c").build());
        ruleEngine.addRule(ruleBuilder() //
            .withTargetedPropterty("y") //
            .withValues("A", "B") //
            .withTriggeringProperties("x") //
            .withCondition( //
                new Condition() {
                    @Override
                    public boolean calculate() {
                        return ruleEngine.get("x").equals("c");
                    };
                }).build());
        ruleEngine.run(scriptProducerMock);

        String expectedScriptPropertyCombinations = "1 : x=a\n" + "2 : x=b\n"
            + "3 : x=c\ty=A\n" + "4 : x=c\ty=B\n";
        assertEquals(expectedScriptPropertyCombinations,
            scriptProducerMock.getAllScriptPropertyCombinations());

    }
}
