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

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
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
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object _value = new StringBuilder().append(ValueProviderHelper.toValue(_value1(propertyContainer), propertyContainer)).append(ValueProviderHelper.toValue(_value2(propertyContainer), propertyContainer)).toString();
        return _value;
    }}));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

