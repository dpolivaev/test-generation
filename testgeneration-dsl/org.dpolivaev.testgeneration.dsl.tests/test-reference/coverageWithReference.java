MULTIPLE FILES WERE GENERATED

File 1 : /myProject/src-gen/MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy First() {
    return new _MyFile_First_StrategyFactory0().First();
  }
}

File 2 : /myProject/src-gen/_MyFile_First_StrategyFactory0.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProviderHelper;

@SuppressWarnings("all")
class _MyFile_First_StrategyFactory0 {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  private Object _value1(final PropertyContainer propertyContainer) {
    return propertyContainer.get("a");
  }
  
  RequirementBasedStrategy First() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{
      new CoverageEntry("req1", CoverageEntry.ANY),};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("a").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return 123;
    }}).shuffled().asLazyRule());
    _strategy.addRule(RuleBuilder.Factory.iterate("[req1]").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object _value = ValueProviderHelper.toValue(_value1(propertyContainer), propertyContainer);
        return _value;
    }}));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

