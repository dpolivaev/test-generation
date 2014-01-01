package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;

public class PropertyAccessor{
	final private PropertyContainer propertyContainer;

	public PropertyAccessor(PropertyContainer propertyContainer) {
		this.propertyContainer = propertyContainer;
	}

	public List<String> sortedPropertiesForPrefix(String prefix) {
		Set<String> availableProperties = propertyContainer.availableProperties(prefix);
        List<String> sortedProperties = new ArrayList<>(availableProperties);
        Collections.sort(sortedProperties);
		return sortedProperties;
	}

}