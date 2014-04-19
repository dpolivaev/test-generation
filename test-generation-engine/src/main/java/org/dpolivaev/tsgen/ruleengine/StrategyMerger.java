package org.dpolivaev.tsgen.ruleengine;

import java.util.ArrayList;
import java.util.Collection;

public class StrategyMerger {

	private String[] triggers = {};
	private Condition condition = Condition.TRUE;
	private Collection<Rule> rules;

	public StrategyMerger moveRuleFrom(Strategy source) {
		Collection<Rule> topRules = source.topRules();
		Collection<Rule> triggeredRules = source.triggeredRules();
		Collection<Rule> defaultRules = source.defaultRules();
		rules = new ArrayList<Rule>(topRules.size() + triggeredRules.size() + defaultRules.size());
		rules.addAll(topRules);
		rules.addAll(triggeredRules);
		rules.addAll(defaultRules);
		cutRules(source, rules);
		transformRules(rules);
		return this;
	}

	public void to(Strategy target) {
		for(Rule rule : rules){
			target.addRule(rule);
		}
	}

	private void transformRules(final Collection<Rule> rules) {
		for(Rule rule : rules){
			if(! rule.isDefaultRule())
				for(String trigger : triggers){
					rule.addTriggeringProperty(trigger);
				}
			rule.addCondition(condition);
		}
	}

	private void cutRules(Strategy source, final Collection<Rule> rules) {
		for(Rule rule : rules){
			source.removeRule(rule);
		}
	}

	public StrategyMerger withTrigger(String... triggers) {
		this.triggers = triggers;
		return this;
	}

	public StrategyMerger withCondition(Condition condition) {
		this.condition = condition;
		return this;
	}

	public void to(RuleBuilder target) {
		target.with(rules.toArray(new Rule[]{}));
	}

}
