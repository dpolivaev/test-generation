MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.ruleengine.ValueProviderHelper;

@SuppressWarnings("all")
class _First_StrategyFactory {
  private Object _value1(final PropertyContainer propertyContainer) {
    return propertyContainer.get("a");
  }
  
  RequirementBasedStrategy First() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{
      new CoverageEntry("req1", CoverageEntry.ANY),};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("a").over(123).asDefaultRule());
    _strategy.addRule(RuleBuilder.Factory.iterate("[req1]").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object _value = ValueProviderHelper.toValue(_value1(propertyContainer), propertyContainer);
        return _value;
    }}));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

