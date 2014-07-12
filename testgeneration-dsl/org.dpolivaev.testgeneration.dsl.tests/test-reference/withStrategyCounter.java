MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first(final int start) {
    return new _MyFile_first_StrategyFactory(start).first();
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
  
  private final int start;
  
  public _MyFile_first_StrategyFactory(final int start) {
    this.start = start;
    this._counter_pre = _init_pre();
    this.somePreconditionIndex = _init_somePreconditionIndex();
    this.somePreconditionGroupIndex = _init_somePreconditionGroupIndex();
    
  }
  
  private int _init_pre() {
    return this.start;
  }
  
  private int _counter_pre;
  
  private int _init_somePreconditionIndex() {
    return this.pre();
  }
  
  private final int somePreconditionIndex;
  
  private int _init_somePreconditionGroupIndex() {
    return this.pre(4);
  }
  
  private final int somePreconditionGroupIndex;
  
  private int pre() {
    return pre(1);
  }
  
  private int pre(final int _groupSize) {
    int oldCounterValue = _counter_pre;
    _counter_pre += _groupSize;
    return oldCounterValue;
  }
  
  private int _name1() {
    return this.somePreconditionIndex;
  }
  
  private int _name2() {
    return this.somePreconditionGroupIndex;
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

