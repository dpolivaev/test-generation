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
import java.util.Set;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngine implements State {
	private Rules rules = new Rules();
	private MapBasedState mapBasedState = new MapBasedState();

	public void addRule(Rule rule) {
		rules.addRule(rule);
	}

	public boolean hasRuleForProperty(String propertyName) {
		return rules.hasRuleForProperty(propertyName);
	}

	public void run(ScriptProducer scriptProducer) {
		do {
			mapBasedState.nextIteration();
			for (Rule rule : rules()) {
				rule.nextIteration(this);
			}

			scriptProducer.makeScriptFor(this);
			for (Rule rule : rules()) {
				rule.finishIteration(this);
			}

		} while (!allRulesHaveFinished());
	}

	@Override
	public void setPropertyValue(String targetedPropertyName, Object nextValue) {
		mapBasedState.setPropertyValue(targetedPropertyName, nextValue);
		PropertyAssignedEvent event = new PropertyAssignedEvent(this,
				null, targetedPropertyName);
		for (Rule rule : rules()) {
			rule.propertyValueSet(event);
		}
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
		return mapBasedState.getAssignedPropertiesAsString();
	}

	@Override
	public boolean containsPropertyValue(String name) {
		return mapBasedState.containsPropertyValue(name);
	}

	@Override
	public boolean containsPropertyValues(Set<String> names) {
		return mapBasedState.containsPropertyValues(names);
	}

}
