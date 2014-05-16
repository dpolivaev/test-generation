MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first() {
    return new _MyFile_first_StrategyFactory().first();
  }
}

File 2 : _MyFile_first_StrategyFactory.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory {
  private static int _instanceCounter = 0;

  private int _instanceId = _instanceCounter++;

  private Object _name1(final PropertyContainer propertyContainer) {
    return propertyContainer.get("x");
  }

  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return "x";
    }}));
    _strategy.addRule(RuleBuilder.Factory.iterate(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return new StringBuilder().append(_name1(propertyContainer)).toString();
    }}).over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return "y";
    }}));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}
