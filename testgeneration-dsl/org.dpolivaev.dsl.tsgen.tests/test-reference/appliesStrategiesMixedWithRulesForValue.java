MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
  
  public static RequirementBasedStrategy second() {
    return new _second_StrategyFactory().second();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

File 3 : _second_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.StrategyConverter;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _second_StrategyFactory {
  private int _value1(final PropertyContainer propertyContainer) {
    return 1;
  }
  
  private int _value2(final PropertyContainer propertyContainer) {
    return 2;
  }
  
  private RequirementBasedStrategy _strategy3() {
    RequirementBasedStrategy _first = MyFile.first();
    return _first;
  }
  
  RequirementBasedStrategy second() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(_value1(null)).with(
      RuleBuilder.Factory.iterate("y").over(_value2(null))
    ).with(
      RuleBuilder.Factory.with(StrategyConverter.toStrategy(_strategy3())).asRules()
    ).with(
      RuleBuilder.Factory.iterate("y").over(_value2(null))
    ));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy).addRequiredItemsFrom(StrategyConverter.toRequirementBasedStrategy(_strategy3()));
  }
}

