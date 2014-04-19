MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _First_StrategyFactory {
  RequirementBasedStrategy First() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("a").over(1).with(
      RuleBuilder.Factory.iterate("b").over(2).asTriggeredRule()
    ).over(3).asTriggeredRule());
    _strategy.addRule(RuleBuilder.Factory.iterate("d").over(1).over(3).asDefaultRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

