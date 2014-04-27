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
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private int _value1(final PropertyContainer propertyContainer) {
    return 1;
  }
  
  private Boolean condition2(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((1 < 2));
  }
  
  private int _value3(final PropertyContainer propertyContainer) {
    return 3;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(_value1(null)).with(
      RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.0/@ruleGroups.0/@rule/@values/@actions.0/@ruleGroups.0/@condition/@expr").over(new ValueProvider(){
        @Override public Object value(PropertyContainer propertyContainer) {
          Boolean _condition = condition2(propertyContainer);
          return _condition;
      }}).asDefaultRule(),
      RuleBuilder.Factory._if(new Condition(){
        @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
          return propertyContainer.<Boolean>get(" /MyFile.tsgen#/0/@strategies.0/@ruleGroups.0/@rule/@values/@actions.0/@ruleGroups.0/@condition/@expr");
      }}).iterate("y").over(_value3(null))
    ));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

