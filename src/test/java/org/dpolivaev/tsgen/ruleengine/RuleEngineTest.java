package org.dpolivaev.tsgen.ruleengine;
import static org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory.iterate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.ScriptProducer;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.internal.PropertyAssignedEvent;
import org.dpolivaev.tsgen.ruleengine.internal.StatefulRule;
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

    @Test
	public void ruleEngineResetsAssignmentReasonAfterPropertySetEventIsFired() {
		Strategy strategy = Mockito.mock(Strategy.class);
		final RuleEngine ruleEngine = new RuleEngine(new ScriptProducer() {
			
			@Override
			public void makeScriptFor(PropertyContainer propertyContainer) {
				StatefulRule triggeredRule = iterate("name").over("value2").when("triggeredBy").asRule();
				RuleEngine ruleEngine = (RuleEngine) propertyContainer;
				ruleEngine.setPropertyValue(triggeredRule, "value2", true);
				assertThat(ruleEngine.getAssignmentReason(), equalTo("->"));
				
			}
		});
		ruleEngine.run(strategy);
	}
}
