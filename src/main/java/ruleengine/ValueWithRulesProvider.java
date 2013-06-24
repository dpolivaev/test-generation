package ruleengine;

import java.util.Collection;

public interface ValueWithRulesProvider {
    Object value();
    Collection<Rule> rules();
}