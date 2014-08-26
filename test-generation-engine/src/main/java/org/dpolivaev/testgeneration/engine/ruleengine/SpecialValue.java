package org.dpolivaev.testgeneration.engine.ruleengine;

public enum SpecialValue {
    UNDEFINED, SKIP, SKIP_COMBINATION, DISABLED_RULE;
    static public SpecialValue CONTINUE = UNDEFINED;
}