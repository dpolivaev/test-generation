MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.coverage.TrackingRuleEngine;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleEngine;
import org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration;
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  private static RequirementBasedStrategy _strategy1() {
    RequirementBasedStrategy _first = MyFile.first();
    return _first;
  }
  
  private static RequirementBasedStrategy _strategy2() {
    RequirementBasedStrategy _second = MyFile.second();
    return _second;
  }
  
  public static RequirementBasedStrategy first() {
    return new _MyFile_first_StrategyFactory().first();
  }
  
  public static RequirementBasedStrategy second() {
    return new _MyFile_second_StrategyFactory().second();
  }
  
  void run1() {
    
    OutputConfiguration _outputConfiguration = new OutputConfiguration();
    OutputConfiguration _reportConfiguration = new OutputConfiguration();
    CoverageTracker _coverageTracker = new CoverageTracker();
    WriterFactory _writerFactory = new WriterFactory(_outputConfiguration, _reportConfiguration);
    _writerFactory.addCoverageTracker(_coverageTracker);
    RuleEngine _ruleEngine = new TrackingRuleEngine(_coverageTracker);
    _writerFactory.configureEngine(_ruleEngine);
    new RequirementBasedStrategy().with(_strategy1()).with(_strategy2()).run(_ruleEngine);
  }
  
  public static void main(final String[] args) {
    new MyFile().runAll();
  }
  
  void runAll() {
    run1();
  }
}

File 2 : _MyFile_first_StrategyFactory.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

File 3 : _MyFile_second_StrategyFactory.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;

@SuppressWarnings("all")
class _MyFile_second_StrategyFactory {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  RequirementBasedStrategy second() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

