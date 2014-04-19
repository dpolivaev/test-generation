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

import com.google.common.base.Objects;
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
    boolean _equals = Objects.equal(propertyContainer.get("x"), Integer.valueOf(1));
    return Boolean.valueOf(_equals);
  }
  
  private RequirementBasedStrategy _strategy2() {
    RequirementBasedStrategy _other = MyFile.other();
    return _other;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).asTriggeredRule());
    _strategy.addRule(RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.1/@condition/@expr").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Boolean _condition = condition1(propertyContainer);
        return _condition;
    }}).asDefaultRule());
    _strategy.addRules(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        return propertyContainer.<Boolean>get(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.1/@condition/@expr");
    }}).with(StrategyConverter.toStrategy(_strategy2())).asRules());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy).addRequiredItemsFrom(StrategyConverter.toRequirementBasedStrategy(_strategy2()));
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
    _strategy.addRule(RuleBuilder.Factory.iterate("y").over(2).asTriggeredRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

