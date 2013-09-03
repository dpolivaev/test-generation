package org.dpolivaev.tsgen.ruleengine;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.ScriptProducer;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.utils.Utils;
import org.junit.Test;
import org.mockito.Mockito;


public class RuleEngineTest {

	@Test
	public void containsAllDefaultProperties() {
		Strategy strategy = Mockito.mock(Strategy.class);
		Mockito.when(strategy.availableDefaultProperties()).thenReturn(Utils.set("name"));
		final RuleEngine ruleEngine = new RuleEngine(new ScriptProducer() {
			
			@Override
			public void makeScriptFor(PropertyContainer propertyContainer) {
				assertThat(propertyContainer.containsPropertyValue("name"), equalTo(true));
				
			}
		});
		ruleEngine.run(strategy);
	}

}
