MULTIPLE FILES WERE GENERATED

File 1 : /myProject/src-gen/MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static int sub1(final int i) {
    return i;
  }
  
  public static RequirementBasedStrategy first() {
    return new _MyFile_first_StrategyFactory0().first();
  }
}

File 2 : /myProject/src-gen/_MyFile_first_StrategyFactory0.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProviderHelper;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory0 {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  private int _value1(final PropertyContainer propertyContainer) {
    int _sub1 = MyFile.sub1(1);
    return _sub1;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object _value = ValueProviderHelper.toValue(_value1(propertyContainer), propertyContainer);
        return _value;
    }}));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

