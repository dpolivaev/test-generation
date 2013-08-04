package ruleengine;

import java.util.Set;

public interface PropertyContainer {
    Assignments getAssignments();
    boolean containsPropertyValues(Set<String> names);
    boolean containsPropertyValue(String string);
    Object get(String name);
    int getCombinationCounter();
    Set<String> availableProperties(String startWith);

}
