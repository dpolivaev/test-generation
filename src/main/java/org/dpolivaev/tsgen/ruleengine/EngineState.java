package org.dpolivaev.tsgen.ruleengine;

import org.dpolivaev.tsgen.coverage.CoverageTracker;


public interface EngineState extends PropertyContainer{
    Strategy currentStrategy();
    CoverageTracker coverage();
    void setPropertyValue(Rule rule, Object value, boolean useNextValue);
}
