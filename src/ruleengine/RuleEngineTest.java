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
	
	private static final RuleAction DO_NOTHING_ACTION = new RuleAction() {
	};
	private RuleEngine ruleEngine = new RuleEngine();
	private ScriptProducerMock scriptProducerMock = new ScriptProducerMock();

	@SuppressWarnings("unchecked")
	void addIterationRuleWithoutAction(String targetedPropertyName) {
		ruleEngine.addRule(new Rule(targetedPropertyName, (Set<String>)Collections.EMPTY_SET, 
				Condition.TRUE, IterationOrder.ORDERED, true, DO_NOTHING_ACTION));
	}



	private void addIterationRuleWithoutTriggeringProperties(
			String targetedPropertyName, String[] values) {
		ruleEngine.addRule(new Rule(targetedPropertyName, (Set<String>)Collections.EMPTY_SET, 
				Condition.TRUE, IterationOrder.ORDERED, true, createSetValueAction(values)));
	}
	
	private RuleAction createSetValueAction(String[] values) {
		return null;
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
	public void singleIterationRuleWithValueA_producesSingeIterationWithValueA() {
		addIterationRuleWithoutTriggeringProperties("property", new String[] {"a"});
		
		ruleEngine.run(scriptProducerMock);
		
		String expectedScriptPropertyCombinations = "1 : property=a\n";
		assertEquals(expectedScriptPropertyCombinations, 
				scriptProducerMock.getScriptPropertyCombinations());
		
	}
	

	@Test
	public void singleIterationRuleWithValueB_producesSingeIterationWithValueB() {
		addIterationRuleWithoutTriggeringProperties("property", new String[] {"b"});
		
		ruleEngine.run(scriptProducerMock);
		
		String expectedScriptPropertyCombinations = "1 : property=b\n";
		assertEquals(expectedScriptPropertyCombinations, 
				scriptProducerMock.getScriptPropertyCombinations());
		
	}

}
