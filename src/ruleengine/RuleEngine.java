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
	
	public void run(ScriptProducer scriptProducer) {
		do{
			state.nextIteration();
			for (Rule rule : rules())
				state.addProperty(rule.getTargetedPropertyName(), rule.nextValue());

			scriptProducer.makeScriptFor(this);
		}while(! allRulesHaveFinished());
	}

	private boolean allRulesHaveFinished() {
		for (Rule rule : rules())
			if (!rule.hasFinished())
				return false;
		return true;
	}

	private List<Rule> rules() {
		return rules.values();
	}

	public String getAssignedPropertiesAsString() {
		return state.getAssignedPropertiesAsString();
	}

}
