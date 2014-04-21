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
  private Boolean condition1(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((1 < 2));
  }
  
  private Boolean condition2(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((2 < 3));
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.0/@ruleGroups.0/@condition/@expr").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Boolean _condition = condition1(propertyContainer);
        return _condition;
    }}).asDefaultRule());
    _strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        return propertyContainer.<Boolean>get(" /MyFile.tsgen#/0/@strategies.0/@ruleGroups.0/@condition/@expr");
    }}).iterate("x").over(1).with(
      RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.0/@ruleGroups.0/@rule/@values/@actions.0/@ruleGroups.0/@condition/@expr").over(new ValueProvider(){
        @Override public Object value(PropertyContainer propertyContainer) {
          Boolean _condition = condition2(propertyContainer);
          return _condition;
      }}).asDefaultRule(),
      RuleBuilder.Factory._if(new Condition(){
        @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
          return propertyContainer.<Boolean>get(" /MyFile.tsgen#/0/@strategies.0/@ruleGroups.0/@rule/@values/@actions.0/@ruleGroups.0/@condition/@expr");
      }}).iterate("y").over(3)
    ));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

