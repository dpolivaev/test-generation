MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy other() {
    return new _other_StrategyFactory().other();
  }
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.StrategyConverter;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private RequirementBasedStrategy _strategy1() {
    RequirementBasedStrategy _other = MyFile.other();
    return _other;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).with(
      RuleBuilder.Factory.iterate("z1").over(3).asTriggeredRule(),
      RuleBuilder.Factory.with(StrategyConverter.toStrategy(_strategy1())).asRules(),
      RuleBuilder.Factory.iterate("z2").over(4).asTriggeredRule()
    ).asTriggeredRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy).addRequiredItemsFrom(StrategyConverter.toRequirementBasedStrategy(_strategy1()));
  }
}

File 3 : _other_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _other_StrategyFactory {
  RequirementBasedStrategy other() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("y").over(2).asTriggeredRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

