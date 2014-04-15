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

  public int calculate(final int i) {
    return 0;
  }
}

File 3 : _s_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTrackerEnabler;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.ruleengine.ValueProviderHelper;

@SuppressWarnings("all")
class _s_StrategyFactory {
  private Object _value1(final PropertyContainer propertyContainer) {
    Object _calculate = MyFile.myOracle.calculate(0);
    return _calculate;
  }

  RequirementBasedStrategy s() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    _strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        ((CoverageTrackerEnabler)propertyContainer).startTrace(); try{
        Object _value = ValueProviderHelper.toValue(_value1(propertyContainer), propertyContainer);
        return _value;
        } finally{ ((CoverageTrackerEnabler)propertyContainer).stopTrace();}
    }}).asRule());
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}
