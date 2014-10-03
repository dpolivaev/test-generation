MULTIPLE FILES WERE GENERATED

File 1 : /myProject/src-gen/MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first() {
    return new _MyFile_first_StrategyFactory0().first();
  }
  
  public static RequirementBasedStrategy first(final int p) {
    return new _MyFile_first_StrategyFactory1(p).first();
  }
}

File 2 : /myProject/src-gen/_MyFile_first_StrategyFactory0.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.coverage.StrategyConverter;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory0 {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  private RequirementBasedStrategy _strategy1() {
    RequirementBasedStrategy _first = MyFile.first(1);
    return _first;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    RequirementBasedStrategy _applied_strategy1;
    Strategy _strategy = new Strategy();
    _strategy.addRules(RuleBuilder.Factory.with((_applied_strategy1 = StrategyConverter.toRequirementBasedStrategy(_strategy1())).getStrategy()).asRules());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy).addRequiredItemsFrom(_applied_strategy1);
  }
}

File 3 : /myProject/src-gen/_MyFile_first_StrategyFactory1.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProviderHelper;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory1 {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  private final int p;
  
  public _MyFile_first_StrategyFactory1(final int p) {
    this.p = p;
    
  }
  
  private int _value1(final PropertyContainer propertyContainer) {
    return this.p;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object _value = ValueProviderHelper.toValue(_value1(propertyContainer), propertyContainer);
        return _value;
    }}).shuffled().asLazyRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

