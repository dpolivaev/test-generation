MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy other() {
    return new _MyFile_other_StrategyFactory0().other();
  }
  
  public static RequirementBasedStrategy first() {
    return new _MyFile_first_StrategyFactory1().first();
  }
}

File 2 : _MyFile_first_StrategyFactory1.java

import com.google.common.base.Objects;
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.coverage.StrategyConverter;
import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory1 {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  private Boolean condition1(final PropertyContainer propertyContainer) {
    boolean _equals = Objects.equal(propertyContainer.get("x"), Integer.valueOf(1));
    return Boolean.valueOf(_equals);
  }
  
  private RequirementBasedStrategy _strategy2() {
    RequirementBasedStrategy _other = MyFile.other();
    return _other;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    RequirementBasedStrategy _applied_strategy2;
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return 1;
    }}));
    _strategy.addRule(RuleBuilder.Factory.iterate(" _MyFile_first_StrategyFactory1.ConditionAtOffset63#" +  _instanceId).over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Boolean _condition = condition1(propertyContainer);
        return _condition;
    }}).asLazyRule());
    _strategy.addRules(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        return propertyContainer.<Boolean>get(" _MyFile_first_StrategyFactory1.ConditionAtOffset63#" +  _instanceId);
    }}).with((_applied_strategy2 = StrategyConverter.toRequirementBasedStrategy(_strategy2())).getStrategy()).asRules());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy).addRequiredItemsFrom(_applied_strategy2);
  }
}

File 3 : _MyFile_other_StrategyFactory0.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _MyFile_other_StrategyFactory0 {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  RequirementBasedStrategy other() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("y").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return 2;
    }}));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

