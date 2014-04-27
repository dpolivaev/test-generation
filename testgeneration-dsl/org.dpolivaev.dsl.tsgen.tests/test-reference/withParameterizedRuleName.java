MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first(final int p) {
    return new _first_StrategyFactory(p).first();
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
  private final int p;
  
  public _first_StrategyFactory(final int p) {
    this.p = p;
    
  }
  
  private String _name1() {
    return "x#";
  }
  
  private int _name2() {
    return this.p;
  }
  
  private int _value3(final PropertyContainer propertyContainer) {
    return this.p;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate(new StringBuilder().append(_name1()).append(_name2()).toString()).over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object _value = ValueProviderHelper.toValue(_value3(propertyContainer), propertyContainer);
        return _value;
    }}).asDefaultRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

