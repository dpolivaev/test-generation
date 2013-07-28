package ruleengine;

import java.util.Set;

public interface PropertyContainer {
    Assignments getAssignments();
    boolean containsPropertyValues(Set<String> names);
    Object get(String name);
    int getCombinationCounter();

}
