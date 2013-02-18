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

import org.junit.Test;

/**
 * @author Dimitry Polivaev
 * 18.02.2013
 */
public class RuleEngineTest {
	
	@Test
	public void ruleEngineWithoutRules_callsScriptProducerOnce() {
		RuleEngine ruleEngine = new RuleEngine();
		ScriptProducerMock scriptProducerMock = new ScriptProducerMock(){};
		ruleEngine.run(scriptProducerMock);
		
		assertThat(scriptProducerMock.callCount(), is(1));
	}

	@Test
	public void ruleEngineWithOneIterationRule_hasRuleForItsTargetedProperty() {
		RuleEngine ruleEngine = new RuleEngine();
		String targetedPropertyName = "property";
		ruleEngine.addRule(new Rule(targetedPropertyName)); 
		
		assertThat(ruleEngine.hasRuleForProperty(targetedPropertyName), is(true));
	}


	@Test
	public void ruleEngineWithTwoIterationRules_hasRulesForItsTargetedProperties() {
		RuleEngine ruleEngine = new RuleEngine();
		String firstTargetedPropertyName = "property1";
		ruleEngine.addRule(new Rule(firstTargetedPropertyName)); 
		String secondTargetedPropertyName = "property2";
		ruleEngine.addRule(new Rule(secondTargetedPropertyName)); 
		
		assertThat(ruleEngine.hasRuleForProperty(firstTargetedPropertyName), is(true));
		assertThat(ruleEngine.hasRuleForProperty(secondTargetedPropertyName), is(true));
	}

}
