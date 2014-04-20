package org.dpolivaev.tsgen.ruleengine;

import java.util.Collection;

public class StrategyMerger {

	private String[] triggers = {};
	private Condition condition = Condition.TRUE;
	private Collection<RuleBuilder> rules;

	public StrategyMerger moveRuleFrom(Strategy source) {
		rules = source.giveBuilders();
		transformRules(rules);
		return this;
	}

	public void to(Strategy target) {
		for(RuleBuilder rule : rules){
			target.addRule(rule);
		}
	}

	private void transformRules(final Collection<RuleBuilder> rules) {
		for(RuleBuilder rule : rules){
			for(String trigger : triggers)
				rule.addTriggeringProperty(trigger);
			rule.addCondition(condition);
		}
	}

	public StrategyMerger withTrigger(Collection<String> triggers) {
		return withTrigger(triggers.toArray(new String[]{}));
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
		target.with(rules);
	}

}
