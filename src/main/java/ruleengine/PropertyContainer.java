package ruleengine;

import java.util.Set;

public interface PropertyContainer {
    boolean containsPropertyValues(Set<String> names);
    Object get(String name);

}
