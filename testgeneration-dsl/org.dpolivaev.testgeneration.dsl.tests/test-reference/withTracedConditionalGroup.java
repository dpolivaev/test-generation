MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.CoverageTrackerEnabler;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.Condition;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private Boolean condition1(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((1 < 2));
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate(" /MyFile.sdt#/0/@strategies.0/@ruleGroups.0/@condition/@expr").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        ((CoverageTrackerEnabler)propertyContainer).startTrace(); try{
        Boolean _condition = condition1(propertyContainer);
        return _condition;
        } finally{ ((CoverageTrackerEnabler)propertyContainer).stopTrace();}
    }}).asDefaultRule());
    _strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        return propertyContainer.<Boolean>get(" /MyFile.sdt#/0/@strategies.0/@ruleGroups.0/@condition/@expr");
    }}).iterate("y").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return 3;
    }}));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

