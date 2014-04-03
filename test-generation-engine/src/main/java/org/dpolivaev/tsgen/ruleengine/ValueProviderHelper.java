package org.dpolivaev.tsgen.ruleengine;

public class ValueProviderHelper {
    public static Object toValue(final Object value, final PropertyContainer propertyContainer) {
        return value;
      }

    public static Object toValue(final ValueProvider valueProvider, final PropertyContainer propertyContainer) {
        return valueProvider.value(propertyContainer);
      }
}
