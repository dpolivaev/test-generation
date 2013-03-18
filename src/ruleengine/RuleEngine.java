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

import java.util.Iterator;
import java.util.List;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngine {
	private Rules rules = new Rules();
	private State state = new State();

	public void addRule(Rule rule) {
		rules.addRule(rule);
	}

	public boolean hasRuleForProperty(String propertyName) {
		return rules.hasRuleForProperty(propertyName);
	}
	
	// while (combination.hasNext()) {
	//    while(combilnation.nextRule().nextValue()) {
	//          generate
	//    }

	public void run(ScriptProducer scriptProducer) {
		List<Rule> ruleValues = rules.values();
		if (!ruleValues.isEmpty()) {
			Rule rule0 = ruleValues.get(0);
			ValueIterator value0Iterator = rule0.iterator();
			addPropertyToState(rule0, value0Iterator);
			
			if(ruleValues.size() == 2){
				Rule rule1 = ruleValues.get(1);
				ValueIterator value1Iterator = rule1.iterator();
				addPropertyToState(rule1, value1Iterator);
			}
			
			scriptProducer.makeScriptFor(this);
			if(value0Iterator.hasNext()){
				state.nextIteration();
				addPropertyToState(rule0, value0Iterator);
				if(ruleValues.size() == 2){
					Rule rule1 = ruleValues.get(1);
					ValueIterator value1Iterator = rule1.iterator();
					addPropertyToState(rule1, value1Iterator);
				}
				scriptProducer.makeScriptFor(this);
			}
		}
		else {
			scriptProducer.makeScriptFor(this);
		}
	}

	private void addPropertyToState(Rule rule, ValueIterator valueIterator) {
		{
			Object value = valueIterator.next();
			state.addProperty(rule.getTargetedPropertyName(), value);
		}
	}

	public String getAssignedPropertiesAsString() {
		return state.getAssignedPropertiesAsString();
	}

}
