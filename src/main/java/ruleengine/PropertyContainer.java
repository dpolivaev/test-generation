package ruleengine;

import java.util.Map;
import java.util.Set;

public interface PropertyContainer {
    boolean containsPropertyValues(Set<String> names);
    boolean containsPropertyValue(String name);
	boolean containsTriggeringPropertyValue(String name);
    <T> T get(String name);
    int getCombinationCounter();
    Set<String> availableProperties(String startWith);
    Map<String, Assignment> getAssignmentsAsMap();

}
