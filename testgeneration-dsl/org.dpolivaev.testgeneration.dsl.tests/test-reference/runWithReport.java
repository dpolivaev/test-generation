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
    RequirementBasedStrategy _First = MyFile.First();
    return _First;
  }
  
  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
  
  public static void run1() {
    
    OutputConfiguration _outputConfiguration = new OutputConfiguration();
    OutputConfiguration _reportConfiguration = new OutputConfiguration();
    _reportConfiguration.setXmlDirectory("testoutput").setXmlExtension("xml").setXsltSource("my.xslt").setFileDirectory("testoutput").setFileExtension("report");
    CoverageTracker _coverageTracker = new CoverageTracker();
    WriterFactory _writerFactory = new WriterFactory(_outputConfiguration, _reportConfiguration);
    _writerFactory.addCoverageTracker(_coverageTracker);
    RuleEngine _ruleEngine = new TrackingRuleEngine(_coverageTracker);
    _writerFactory.configureEngine(_ruleEngine);
    new RequirementBasedStrategy().with(_strategy1()).run(_ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;

@SuppressWarnings("all")
class _First_StrategyFactory {
  RequirementBasedStrategy First() {
    CoverageEntry[] _requiredItems = new CoverageEntry[]{};
    Strategy _strategy = new Strategy();
    return new RequirementBasedStrategy(_requiredItems).with(_strategy);
  }
}
