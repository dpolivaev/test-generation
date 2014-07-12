MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  private static int _counter_pre = 1;
  
  public static int somePreconditionIndex = MyFile.pre();
  
  public static int somePreconditionGroupIndex = MyFile.pre(4);
  
  public static int pre() {
    return pre(1);
  }
  
  public static int pre(final int _groupSize) {
    int oldCounterValue = _counter_pre;
    _counter_pre += _groupSize;
    return oldCounterValue;
  }
  
  public static RequirementBasedStrategy first() {
    return new _MyFile_first_StrategyFactory().first();
  }
}

File 2 : _MyFile_first_StrategyFactory.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  private int _name1() {
    return MyFile.somePreconditionIndex;
  }
  
  private int _name2() {
    return MyFile.somePreconditionGroupIndex;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate(new StringBuilder().append("pre#").append(_name1()).toString()).over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return "precondition 1";
    }}));
    _strategy.addRule(RuleBuilder.Factory.iterate(new StringBuilder().append("pre#").append(_name2()).toString()).over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {return "precondition 2";
    }}));
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

