MULTIPLE FILES WERE GENERATED

File 1 : /myProject/src-gen/MyFile.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.coverage.StrategyConverter;
import org.dpolivaev.testgeneration.engine.coverage.TrackingRuleEngine;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleEngine;
import org.dpolivaev.testgeneration.engine.scriptwriter.OutputConfiguration;
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public MyFile() {
    this.string = _init_string();
    
  }
  
  private String _init_string() {
    return "string";
  }
  
  private final String string;
  
  private RequirementBasedStrategy _strategy1() {
    RequirementBasedStrategy _first = MyFile.first(this.string);
    return _first;
  }
  
  public static RequirementBasedStrategy first(final String string) {
    return new _MyFile_first_StrategyFactory0(string).first();
  }
  
  void run1() {
    
    OutputConfiguration _outputConfiguration = new OutputConfiguration();
    OutputConfiguration _reportConfiguration = new OutputConfiguration();
    CoverageTracker _coverageTracker = new CoverageTracker();
    WriterFactory _writerFactory = new WriterFactory(_outputConfiguration, _reportConfiguration);
    _writerFactory.addCoverageTracker(_coverageTracker);
    RequirementBasedStrategy _strategy1 = StrategyConverter.toRequirementBasedStrategy(_strategy1());
    RuleEngine _ruleEngine = new TrackingRuleEngine(_coverageTracker);
    _writerFactory.configureEngine(_ruleEngine);
    new RequirementBasedStrategy().with(_strategy1).run(_ruleEngine);
  }
  
  void run2() {
    
    OutputConfiguration _outputConfiguration = new OutputConfiguration();
    OutputConfiguration _reportConfiguration = new OutputConfiguration();
    CoverageTracker _coverageTracker = new CoverageTracker();
    WriterFactory _writerFactory = new WriterFactory(_outputConfiguration, _reportConfiguration);
    _writerFactory.addCoverageTracker(_coverageTracker);
    RequirementBasedStrategy _strategy1 = StrategyConverter.toRequirementBasedStrategy(_strategy1());
    RuleEngine _ruleEngine = new TrackingRuleEngine(_coverageTracker);
    _writerFactory.configureEngine(_ruleEngine);
    new RequirementBasedStrategy().with(_strategy1).run(_ruleEngine);
  }
  
  public static void main(final String[] args) {
    try{
    	new MyFile().runAll();
    }
    catch(Exception e){
    	e.printStackTrace();
    	System.exit(1);
    }
  }
  
  void runAll() {
    run1();
    run2();
  }
}

File 2 : /myProject/src-gen/_MyFile_first_StrategyFactory0.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;

@SuppressWarnings("all")
class _MyFile_first_StrategyFactory0 {
  private static int _instanceCounter = 0;
  
  private int _instanceId = _instanceCounter++;
  
  private final String string;
  
  public _MyFile_first_StrategyFactory0(final String string) {
    this.string = string;
    
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}

