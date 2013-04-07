package ruleengine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ruleengine.TestUtils.set;

import org.junit.Test;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngineTest {

	private RuleEngine ruleEngine = new RuleEngine();

	void addIterationRuleWithoutAction(String targetedPropertyName) {
		addIterationRuleWithoutTriggeringProperties(targetedPropertyName,
				"value");
	}

	private void addIterationRuleWithTriggeringProperties(
			String triggeringProperty, String targetedPropertyName,
			String... value) {

		ruleEngine.addRule(new ConstantValuesRule(set(triggeringProperty),
				targetedPropertyName, (Object[]) value));
	}

	private void addIterationRuleWithoutTriggeringProperties(
			String targetedPropertyName, String... value) {
		ruleEngine.addRule(new ConstantValuesRule(targetedPropertyName,
				(Object[]) value));
	}

	@Test
	public void ruleEngineWithoutRules_callsScriptProducerOnce() {
		CountingScriptProducerMock scriptProducerMock = new CountingScriptProducerMock();
		ruleEngine.run(scriptProducerMock);
		assertThat(scriptProducerMock.callCount(), is(1));
	}

	@Test
	public void ruleEngineWithOneIterationRule_hasRuleForItsTargetedProperty() {
		String targetedPropertyName = "property";
		addIterationRuleWithoutAction(targetedPropertyName);

		assertThat(ruleEngine.hasRuleForProperty(targetedPropertyName),
				is(true));
	}

	@Test
	public void ruleEngineWithTwoIterationRules_hasRulesForItsTargetedProperties() {
		String firstTargetedPropertyName = "property1";
		addIterationRuleWithoutAction(firstTargetedPropertyName);
		String secondTargetedPropertyName = "property2";
		addIterationRuleWithoutAction(secondTargetedPropertyName);

		assertThat(ruleEngine.hasRuleForProperty(firstTargetedPropertyName),
				is(true));
		assertThat(ruleEngine.hasRuleForProperty(secondTargetedPropertyName),
				is(true));
	}

	@Test
	public void singleRuleWithPropertyNamedXValueA_callsScriptProducerWithValueA() {
		LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
		addIterationRuleWithoutTriggeringProperties("x", "a");

		ruleEngine.run(scriptProducerMock);

		String expectedScriptPropertyCombinations = "1 : x=a\n";
		assertEquals(expectedScriptPropertyCombinations,
				scriptProducerMock.getAllScriptPropertyCombinations());

	}

	@Test
	public void singleRuleWithValuesA_B_callsScriptProducerWithValuesA_B() {
		LoggingScriptProducerMock loggingScriptProducerMock = new LoggingScriptProducerMock();
		addIterationRuleWithoutTriggeringProperties("property", "a", "b");

		ruleEngine.run(loggingScriptProducerMock);

		String expectedScriptPropertyCombinations = "1 : property=a\n"
				+ "2 : property=b\n";
		assertEquals(expectedScriptPropertyCombinations,
				loggingScriptProducerMock.getAllScriptPropertyCombinations());

	}

	@Test
	public void twoRulesWithValuesAandB_callsScriptProducerWithTheirValues() {
		LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
		addIterationRuleWithoutTriggeringProperties("x", "a");
		addIterationRuleWithoutTriggeringProperties("y", "b");

		ruleEngine.run(scriptProducerMock);

		String expectedScriptPropertyCombinations = "1 : x=a\ty=b\n";
		assertEquals(expectedScriptPropertyCombinations,
				scriptProducerMock.getAllScriptPropertyCombinations());

	}

	@Test
	public void twoRulesWithValuesA1_A2andB1_B2_callsScriptProducerWithTheirValues() {
		LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
		addIterationRuleWithoutTriggeringProperties("x", "a1", "a2");
		addIterationRuleWithoutTriggeringProperties("y", "b1", "b2");

		ruleEngine.run(scriptProducerMock);

		String expectedScriptPropertyCombinations = "1 : x=a1\ty=b1\n"
				+ "2 : x=a2\ty=b2\n";
		assertEquals(expectedScriptPropertyCombinations,
				scriptProducerMock.getAllScriptPropertyCombinations());

	}

	@Test
	public void twoRulesWithValuesA1_A2_A3andB1_B2_callsScriptProducerWithTheirValues() {
		LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
		addIterationRuleWithoutTriggeringProperties("x", "a1", "a2", "a3");
		addIterationRuleWithoutTriggeringProperties("y", "b1", "b2");

		ruleEngine.run(scriptProducerMock);

		String expectedScriptPropertyCombinations = "1 : x=a1\ty=b1\n"
				+ "2 : x=a2\ty=b2\n" + "3 : x=a3\ty=b1\n";
		assertEquals(expectedScriptPropertyCombinations,
				scriptProducerMock.getAllScriptPropertyCombinations());

	}

	@Test
	public void triggeringAndTriggeredRulesWithSingleValues_callsScriptProducerWithTheirValues() {
		LoggingScriptProducerMock scriptProducerMock = new LoggingScriptProducerMock();
		addIterationRuleWithoutTriggeringProperties("x", "a");
		addIterationRuleWithTriggeringProperties("x", "y", "b");

		ruleEngine.run(scriptProducerMock);

		String expectedScriptPropertyCombinations = "1 : x=a\ty=b\n";
		assertEquals(expectedScriptPropertyCombinations,
				scriptProducerMock.getAllScriptPropertyCombinations());

	}

	// @Test
	// public void
	// triggeringAndTriggeredRulesWithValuesA_B_and_C_D_callsScriptProducerWithTheirValues()
	// {
	// LoggingScriptProducerMock scriptProducerMock = new
	// LoggingScriptProducerMock();
	// addIterationRuleWithoutTriggeringProperties("x", "a", "b");
	// addIterationRuleWithTriggeringProperties("x", "y", "c", "d");
	//
	// ruleEngine.run(scriptProducerMock);
	//
	// String expectedScriptPropertyCombinations =
	// "1 : x=a\ty=c\n" +
	// "2 : x=a\ty=d\n" +
	// "3 : x=b\ty=c\n" +
	// "4 : x=b\ty=d\n";
	// assertEquals(expectedScriptPropertyCombinations,
	// scriptProducerMock.getAllScriptPropertyCombinations());
	//
	// }

}
