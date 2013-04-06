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

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class ConstantValuesRule implements Rule {

	private final String targetedPropertyName;
	private final Object[] values;
	private boolean finished;
	private int valueIndex;
	private final Set<String> triggeredBy;

	@SuppressWarnings("unchecked")
	public ConstantValuesRule(String targetedPropertyName, Object... values) {
		this(Collections.EMPTY_SET, targetedPropertyName, values);
	}

	public ConstantValuesRule(Set<String> triggeredBy,
			String targetedPropertyName, Object... values) {
		this.triggeredBy = triggeredBy;
		this.targetedPropertyName = targetedPropertyName;
		this.values = values;
		this.finished = false;
		this.valueIndex = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ruleengine.Rule#getTargetedPropertyName()
	 */
	@Override
	public String getTargetedPropertyName() {
		return targetedPropertyName;
	}

	@Override
	public boolean hasFinished() {
		return finished;
	}

	private Object nextValue() {
		Object value = values[valueIndex++];
		return value;
	}

	@Override
	public void nextIteration(State state) {
		if (triggeredBy.isEmpty()) {
			iterate(state);
		}
	}

	private void iterate(State state) {
		String targetedPropertyName = getTargetedPropertyName();
		Object nextValue = nextValue();
		state.setPropertyValue(targetedPropertyName, nextValue);
	}

	@Override
	public void propertyValueSet(PropertyAssignedEvent event) {
		if (triggeredBy.contains(event.getTargetedPropertyName())
				&& event.getState().containsPropertyValues(triggeredBy))
			iterate(event.getState());
	}

	public void finishIteration(State state) {
		finished = finished || valueIndex == values.length;
		if (valueIndex == values.length)
			valueIndex = 0;
	}
}
