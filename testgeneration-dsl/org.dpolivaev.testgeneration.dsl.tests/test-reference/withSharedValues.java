MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _First_StrategyFactory {
  RequirementBasedStrategy First() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("a").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return 1;
    }}).with(
      RuleBuilder.Factory.iterate("b").over(new ValueProvider(){
        @Override public Object value(PropertyContainer propertyContainer) {return 2;
      }})
    ).over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return 3;
    }}));
    _strategy.addRule(RuleBuilder.Factory.iterate("d").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return 1;
    }}, new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return 3;
    }}).asDefaultRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

