MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first() {
    return new _MyFile_first_StrategyFactory0().first();
  }
}

File 2 : _MyFile_first_StrategyFactory0.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.coverage.StrategyConverter;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory0 {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  private Strategy _strategy1() {
    Strategy _strategy = new Strategy();
    return _strategy;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    RequirementBasedStrategy _applied_strategy1;
    Strategy _strategy = new Strategy();
    _strategy.addRules(RuleBuilder.Factory.with((_applied_strategy1 = StrategyConverter.toRequirementBasedStrategy(_strategy1())).getStrategy()).asRules());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy).addRequiredItemsFrom(_applied_strategy1);
  }
}

