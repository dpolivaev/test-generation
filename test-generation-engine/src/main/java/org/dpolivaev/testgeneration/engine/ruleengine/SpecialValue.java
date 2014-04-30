package org.dpolivaev.testgeneration.engine.ruleengine;

public enum SpecialValue {
    UNDEFINED, SKIP, DISABLED_RULE;
    static public SpecialValue CONTINUE = UNDEFINED;
}