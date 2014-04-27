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
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _First_StrategyFactory {
  private int _value1(final PropertyContainer propertyContainer) {
    return 1;
  }
  
  private int _value2(final PropertyContainer propertyContainer) {
    return 2;
  }
  
  private int _value3(final PropertyContainer propertyContainer) {
    return 3;
  }
  
  RequirementBasedStrategy First() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("a").over(_value1(null)).with(
      RuleBuilder.Factory.iterate("b").over(_value2(null))
    ).over(_value3(null)));
    _strategy.addRule(RuleBuilder.Factory.iterate("d").over(_value1(null), _value3(null)).asDefaultRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

