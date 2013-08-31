package scriptproducer;

import ruleengine.RuleEngine;
import ruleengine.ScriptProducer;
import ruleengine.Strategy;

public class StrategyRunner {
	  public void run(Strategy strategy){
		StreamResultFactory resultFactory = new StreamResultFactory();
		ScriptProducer scriptProducer = new MultipleScriptsProducer(resultFactory);
		RuleEngine ruleEngine = new RuleEngine(scriptProducer);
		ruleEngine.run(strategy);
	  }
	
}

