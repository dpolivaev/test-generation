package org.dpolivaev.testgeneration.engine.ruleengine;

public class ValueProviderHelper {
    public static Object toValue(final Object value, final PropertyContainer propertyContainer) {
    	if (value instanceof ValueProvider)
    		return toValue((ValueProvider)value, propertyContainer);
    	return value;
      }

    public static Object toValue(final ValueProvider valueProvider, final PropertyContainer propertyContainer) {
        return valueProvider.value(propertyContainer);
      }
}
