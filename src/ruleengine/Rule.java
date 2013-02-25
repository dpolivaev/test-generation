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

import java.util.Collections;
import java.util.Set;


//Each Rule contains
//* a Targeted Property name,
//* (Iteration Rules only) a set of Triggering Properties,
//* a precondition as a boolean expression using other property values, true by default,
//* an option indicating whether the values should be assigned in a given or a random (shuffled) order,
//* an option indicating whether all values must be used (mandatory complete iteration),
//* a Rule Action.


/**
 * @author Dimitry Polivaev
 * 18.02.2013
 */
public class Rule {
	
	public final String targetedPropertyName;
	private final Set<String> triggeringProperties;
	private final Condition precondition;
	private final IterationOrder iterationOrder;
	private final boolean useAllValues;
	private final RuleAction ruleAction; 
	
	public Rule(String targetedPropertyName, 
    		final Set<String> triggeringProperties,
    		Condition precondition,
    		IterationOrder iterationOrder,
    		boolean useAllValues,
    		RuleAction ruleAction) {
    	this.targetedPropertyName = targetedPropertyName;
		this.triggeringProperties = triggeringProperties;
		this.precondition = precondition;
		this.iterationOrder = iterationOrder;
		this.useAllValues = useAllValues;
		this.ruleAction = ruleAction;
	}
	
}
