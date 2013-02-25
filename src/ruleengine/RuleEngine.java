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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dimitry Polivaev
 * 18.02.2013
 */
public class RuleEngine {

	private Map<String, Rule> rules = new HashMap<String, Rule>();
	
	public void run(ScriptProducer scriptProducer) {
		scriptProducer.makeScriptFor(this);
    }
	
	public void addRule(Rule rule) {
		rules.put(rule.targetedPropertyName, rule);
    }
	
	public boolean hasRuleForProperty(String propertyName) {
		return rules.containsKey(propertyName);
	}

	public AssignedProperties getAssignedProperties() {
		return new AssignedPropertiesMap();
	}
	
}

