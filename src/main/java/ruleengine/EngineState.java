package ruleengine;

import java.util.Set;

public interface EngineState {
    Strategy currentStrategy();
    boolean containsPropertyValues(Set<String> names);
    Object get(String name);
    void setPropertyValue(Rule rule, Object value, boolean useNextValue);
}
