package ruleengine;

import java.util.Collection;

public interface ValueWithRulesProvider extends ValueProvider {
    Collection<Rule> rules(PropertyContainer propertyContainer);
}