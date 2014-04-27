MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import com.google.common.base.Objects;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.ruleengine.ValueProviderHelper;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private String _value1(final PropertyContainer propertyContainer) {
    return "a";
  }
  
  private String _value2(final PropertyContainer propertyContainer) {
    return "b";
  }
  
  private String _value3(final PropertyContainer propertyContainer) {
    return "A";
  }
  
  private String _value4(final PropertyContainer propertyContainer) {
    return "B";
  }
  
  private Boolean condition5(final PropertyContainer propertyContainer) {
    boolean _equals = Objects.equal(propertyContainer.get("x"), "a");
    return Boolean.valueOf(_equals);
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(_value1(null), _value2(null)).with(
      RuleBuilder.Factory.iterate("y").over(new ValueProvider(){
        @Override public Object value(PropertyContainer propertyContainer) {
          if(!condition5(propertyContainer)) return SpecialValue.SKIP;
          Object _value = ValueProviderHelper.toValue(_value3(propertyContainer), propertyContainer);
          return _value;
      }}, _value4(null)).ordered()
    ));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

