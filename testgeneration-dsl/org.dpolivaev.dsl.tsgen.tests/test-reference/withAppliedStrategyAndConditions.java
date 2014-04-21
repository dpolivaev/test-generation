MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy other() {
    return new _other_StrategyFactory().other();
  }
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.StrategyConverter;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private Boolean condition1(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((((int) (propertyContainer.<Integer>get("z")).intValue()) > 0));
  }
  
  private Boolean condition2(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((((int) (propertyContainer.<Integer>get("z2")).intValue()) < 0));
  }
  
  private RequirementBasedStrategy _strategy3() {
    RequirementBasedStrategy _other = MyFile.other();
    return _other;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.0/@condition/@expr").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Boolean _condition = condition1(propertyContainer);
        return _condition;
    }}).asDefaultRule());
    _strategy.addRule(RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.0/@ruleGroups.0/@condition/@expr").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Boolean _condition = propertyContainer.<Boolean>get(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.0/@condition/@expr") && condition2(propertyContainer);
        return _condition;
    }}).asDefaultRule());
    _strategy.addRule(RuleBuilder.Factory.when("z", "z2")._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        return propertyContainer.<Boolean>get(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.0/@ruleGroups.0/@condition/@expr");
    }}).iterate("x").over(1));
    _strategy.addRules(RuleBuilder.Factory.when("z", "z2")._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        return propertyContainer.<Boolean>get(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.0/@ruleGroups.0/@condition/@expr");
    }}).with(StrategyConverter.toStrategy(_strategy3())).asRules());
    _strategy.addRule(RuleBuilder.Factory.when("z", "z2")._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        return propertyContainer.<Boolean>get(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.0/@ruleGroups.0/@condition/@expr");
    }}).iterate("z2").over(4));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy).addRequiredItemsFrom(StrategyConverter.toRequirementBasedStrategy(_strategy3()));
  }
}

File 3 : _other_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _other_StrategyFactory {
  RequirementBasedStrategy other() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("y").over(2));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

