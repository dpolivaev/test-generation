package org.dpolivaev.tsgen.ruleengine;

import java.util.Collection;

import org.dpolivaev.tsgen.coverage.Goal;


public interface EngineState extends PropertyContainer{
    Strategy currentStrategy();
    Collection<Goal> goals();
    void setPropertyValue(Rule rule, Object value, boolean useNextValue);
}
