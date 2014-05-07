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

import com.google.common.base.Objects;
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  private Boolean condition1(final PropertyContainer propertyContainer) {
    boolean _equals = Objects.equal(propertyContainer.get("x"), "a");
    return Boolean.valueOf(_equals);
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return "a";
    }}, new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return "b";
    }}).with(
      RuleBuilder.Factory.iterate("y").over(new ValueProvider(){
        @Override public Object value(PropertyContainer propertyContainer) {
          if(!condition1(propertyContainer)) return SpecialValue.SKIP;
          Object _value = "A";
          return _value;
      }}, new ValueProvider(){
        @Override public Object value(PropertyContainer propertyContainer) {return "B";
      }}).ordered()
    ));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

