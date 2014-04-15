MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.TrackingRuleEngine;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  private static MyOracle _oracle1() {
    return MyFile.myOracle;
  }

  private static RequirementBasedStrategy _strategy2() {
    RequirementBasedStrategy _s = MyFile.s();
    return _s;
  }

  public final static MyOracle myOracle = new MyOracle();

  public static RequirementBasedStrategy s() {
    return new _s_StrategyFactory().s();
  }

  public static void run1() {

    OutputConfiguration _outputConfiguration = new OutputConfiguration();
    OutputConfiguration _reportConfiguration = new OutputConfiguration();
    CoverageTracker _coverageTracker = new CoverageTracker();
    WriterFactory _writerFactory = new WriterFactory(_outputConfiguration, _reportConfiguration);
    _writerFactory.addCoverageTracker(_coverageTracker);
    RuleEngine _ruleEngine = new TrackingRuleEngine(_coverageTracker);
    _ruleEngine.addHandler(_oracle1());
    _oracle1().setCoverageTracker(_coverageTracker);
    _oracle1().registerRequiredItems(_writerFactory);
    _writerFactory.configureEngine(_ruleEngine);
    new RequirementBasedStrategy().with(_strategy2()).run(_ruleEngine);
  }

  public static void main(final String[] args) {
    MyFile.run1();
  }
}

File 2 : MyOracle.java

import java.util.Arrays;
import java.util.List;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyOracle implements PropertyHandler {
  public final static List<CoverageEntry> labels = Arrays.asList(new CoverageEntry[]{});

  private PropertyContainer propertyContainer;

  private CoverageTracker coverageTracker = null;

  public void setCoverageTracker(final CoverageTracker coverageTracker) {
    this.coverageTracker = coverageTracker;
  }

  public void registerRequiredItems(final WriterFactory writerFactory) {
    writerFactory.registerRequiredItems(labels);
  }

  @Override
  public void generationStarted(final PropertyContainer propertyContainer) {
    this.propertyContainer=propertyContainer;
  }

  @Override
  public void handlePropertyCombination(final PropertyContainer propertyContainer) {

  }

  @Override
  public void generationFinished() {
    this.propertyContainer=null;
  }

  public boolean calculate() {
    return true;
  }
}

File 3 : _s_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTrackerEnabler;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _s_StrategyFactory {
  private Boolean condition1(final PropertyContainer propertyContainer) {
    Boolean _calculate = MyFile.myOracle.calculate();
    return _calculate;
  }

  RequirementBasedStrategy s() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.0/@ruleGroups.0/@condition/@expr").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        ((CoverageTrackerEnabler)propertyContainer).startTrace(); try{
        Boolean _condition = condition1(propertyContainer);
        return _condition;
        } finally{ ((CoverageTrackerEnabler)propertyContainer).stopTrace();}
    }}).asDefaultRule());
    _strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        return propertyContainer.<Boolean>get(" /MyFile.tsgen#/0/@strategies.0/@ruleGroups.0/@condition/@expr");
    }}).iterate("x").over(1).asRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}
