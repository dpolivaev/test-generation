package ruleengine;

import java.util.Collection;
import java.util.Collections;

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
    public Collection<Rule> rules() {
        return Collections.<Rule> emptyList();
    }

    /* (non-Javadoc)
     * @see ruleengine.Value#value()
     */
    @Override
    public Object value() {
        return value;
    }

}