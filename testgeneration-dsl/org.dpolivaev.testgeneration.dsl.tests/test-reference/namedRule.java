MULTIPLE FILES WERE GENERATED

File 1 : /myProject/src-gen/MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
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

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory0 {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.rule("y").iterate("y").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return "A";
    }}, new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return "B";
    }}));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

