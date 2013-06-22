package ruleengine;

public interface RuleContainer {
    public void addRule(Rule rule);
    public void addRule(StatefulRuleBuilder rule);
    public void removeRule(Rule rule);
}
