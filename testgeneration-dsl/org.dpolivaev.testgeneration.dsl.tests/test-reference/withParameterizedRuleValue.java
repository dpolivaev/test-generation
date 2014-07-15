MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first(final boolean p) {
    return new _MyFile_first_StrategyFactory0(p).first();
  }
}

File 2 : _MyFile_first_StrategyFactory0.java

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
  
  private final boolean p;
  
  public _MyFile_first_StrategyFactory0(final boolean p) {
    this.p = p;
    
  }
  
  private int _value1(final PropertyContainer propertyContainer) {
    int _xifexpression = (int) 0;
    if (this.p) {
      _xifexpression = 1;
    } else {
      _xifexpression = 0;
    }
    return _xifexpression;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object _value = ValueProviderHelper.toValue(_value1(propertyContainer), propertyContainer);
        return _value;
    }}).shuffled().asDefaultRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

