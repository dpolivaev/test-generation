/*
 *  Freeplane - mind map editor
 *  Copyright (C) 2013 Dimitry
 *
 *  This file author is Dimitry
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ruleengine;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

/**
 * @author Dimitry Polivaev
 * 18.02.2013
 */
public class RuleEngineTest {

	private RuleEngine ruleEngine = new RuleEngine();
	private ScriptProducerMock scriptProducerMock = new ScriptProducerMock();

	void addIterationRuleWithoutAction(String targetedPropertyName) {
		addIterationRuleWithoutTriggeringProperties(targetedPropertyName, "value");
	}

	private void addIterationRuleWithoutTriggeringProperties(
			String targetedPropertyName, String... value) {
		ruleEngine.addRule(new ConstantValuesRule(targetedPropertyName, value));
	}

	@Test
	public void ruleEngineWithoutRules_callsScriptProducerOnce() {
		ruleEngine.run(scriptProducerMock);
		assertThat(scriptProducerMock.callCount(), is(1));
	}

	@Test
	public void ruleEngineWithOneIterationRule_hasRuleForItsTargetedProperty() {
		String targetedPropertyName = "property";
		addIterationRuleWithoutAction(targetedPropertyName); 
		
		assertThat(ruleEngine.hasRuleForProperty(targetedPropertyName), is(true));
	}


	@Test
	public void ruleEngineWithTwoIterationRules_hasRulesForItsTargetedProperties() {
		String firstTargetedPropertyName = "property1";
		addIterationRuleWithoutAction(firstTargetedPropertyName); 
		String secondTargetedPropertyName = "property2";
		addIterationRuleWithoutAction(secondTargetedPropertyName); 
		
		assertThat(ruleEngine.hasRuleForProperty(firstTargetedPropertyName), is(true));
		assertThat(ruleEngine.hasRuleForProperty(secondTargetedPropertyName), is(true));
	}

	@Test
	public void singleRuleWithValueA_callsScriptProducerWithValueA() {
		addIterationRuleWithoutTriggeringProperties("property", "a");
		
		ruleEngine.run(scriptProducerMock);
		
		String expectedScriptPropertyCombinations = "1 : property=a\n";
		assertEquals(expectedScriptPropertyCombinations, 
				scriptProducerMock.getAllScriptPropertyCombinations());
		
	}
	

	@Test
	public void singleRuleWithValueB_callsScriptProducerWithValueB() {
		addIterationRuleWithoutTriggeringProperties("property", "b");
		
		ruleEngine.run(scriptProducerMock);
		
		String expectedScriptPropertyCombinations = "1 : property=b\n";
		assertEquals(expectedScriptPropertyCombinations, 
				scriptProducerMock.getAllScriptPropertyCombinations());
	}
	

	@Test
	public void singleRuleWithValuesA_B_callsScriptProducerTwice() {
		addIterationRuleWithoutTriggeringProperties("property", "a", "b");
		ruleEngine.run(scriptProducerMock);
		assertThat(scriptProducerMock.callCount(), is(2));
	}

	@Test
	public void singleRuleWithValuesA_B_callsScriptProducerWithValuesA_B() {
		addIterationRuleWithoutTriggeringProperties("property", "a", "b");
		
		ruleEngine.run(scriptProducerMock);
		
		String expectedScriptPropertyCombinations = "1 : property=a\n" +
				"2 : property=b\n";
		assertEquals(expectedScriptPropertyCombinations, 
				scriptProducerMock.getAllScriptPropertyCombinations());
		
	}
	
	@Test
	public void singleRuleWithPropertyNamedXValueA_callsScriptProducerWithValueA() {
		addIterationRuleWithoutTriggeringProperties("x", "a");
		
		ruleEngine.run(scriptProducerMock);
		
		String expectedScriptPropertyCombinations = "1 : x=a\n";
		assertEquals(expectedScriptPropertyCombinations, 
				scriptProducerMock.getAllScriptPropertyCombinations());
		
	}
	
//	
//	@Test
//	public void twoRulesWithValuesAandB_callsScriptProducerWithTheirValues() {
//		addIterationRuleWithoutTriggeringProperties("property1", "a");
//		addIterationRuleWithoutTriggeringProperties("property2", "b");
//		
//		ruleEngine.run(scriptProducerMock);
//		
//		String expectedScriptPropertyCombinations = "1 : property1=a\tproperty2=b\n";
//		assertEquals(expectedScriptPropertyCombinations, 
//				scriptProducerMock.getAllScriptPropertyCombinations());
//		
//	}
//
//	

}
