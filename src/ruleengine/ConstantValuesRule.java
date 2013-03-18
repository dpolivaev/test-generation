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

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Dimitry Polivaev
 * 18.02.2013
 */
public class ConstantValuesRule implements Rule {
	
	private final String targetedPropertyName;
	private final Object[] values;
	private ValueIterator iterator;
	
	public ConstantValuesRule(String targetedPropertyName, Object... values) {
    	this.targetedPropertyName = targetedPropertyName;
		this.values = values;
	}

	/* (non-Javadoc)
	 * @see ruleengine.Rule#getTargetedPropertyName()
	 */
	@Override
	public String getTargetedPropertyName() {
		return targetedPropertyName;
	}

	private Iterable<Object> values() {
		return Arrays.asList(values);
	}

	@Override
	public ValueIterator iterator() {
		if(iterator == null){
			iterator = new ValueIterator() {
				final private Iterator<Object> valueIterator = values().iterator();

				public boolean hasNext() {
					return valueIterator.hasNext();
				}

				public Object next() {
					return valueIterator.next();
				}
				
			};
		}
		return iterator;
	}
	
}
