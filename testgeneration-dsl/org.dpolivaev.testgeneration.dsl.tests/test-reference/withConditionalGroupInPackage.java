MULTIPLE FILES WERE GENERATED

File 1 : somepackage/MyFile.java

package somepackage;

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first() {
    return new _MyFile_first_StrategyFactory0().first();
  }
}

File 2 : somepackage/_MyFile_first_StrategyFactory0.java

package somepackage;

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory0 {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  private Boolean condition1(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((1 < 2));
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate(" somepackage._MyFile_first_StrategyFactory0.condition1#" +  _instanceId).over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Boolean _condition = condition1(propertyContainer);
        return _condition;
    }}).asDefaultRule());
    _strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        return propertyContainer.<Boolean>get(" somepackage._MyFile_first_StrategyFactory0.condition1#" +  _instanceId);
    }}).iterate("y").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return 3;
    }}));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

