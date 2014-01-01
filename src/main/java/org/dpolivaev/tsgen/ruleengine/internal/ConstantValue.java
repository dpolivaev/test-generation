package org.dpolivaev.tsgen.ruleengine.internal;

import java.util.Collection;
import java.util.Collections;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.Rule;

public class ConstantValue implements ValueWithRulesProvider{
    private final Object value;

    public ConstantValue(Object value) {
        super();
        this.value = value;
    }

    /* (non-Javadoc)
     * @see ruleengine.Value#rules()
     */
    @Override
    public Collection<Rule> rules(PropertyContainer propertyContainer) {
        return Collections.<Rule> emptyList();
    }

    /* (non-Javadoc)
     * @see ruleengine.Value#value()
     */
    @Override
    public Object value(PropertyContainer propertyContainer) {
        return value;
    }

	@Override
	public String toString() {
		return value.toString();
	}

}