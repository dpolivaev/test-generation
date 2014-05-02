package org.dpolivaev.testgeneration.engine.ruleengine;

import java.util.Collection;
import java.util.Set;

public interface PropertyContainer {
    boolean isPropertyAvailable(String name);
	boolean containsTriggeringPropertyValue(String name);
    <T> T get(String name);
    Assignment getAssignment(String name);
    int getCombinationCounter();
    Set<String> availableProperties(String startWith);
    Collection<Assignment> getAssignments();

}
