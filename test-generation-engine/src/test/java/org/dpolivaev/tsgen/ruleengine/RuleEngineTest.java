package org.dpolivaev.tsgen.ruleengine;
import static org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory.iterate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.internal.StatefulRule;
import org.dpolivaev.tsgen.utils.internal.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class RuleEngineTest {
	
	private Strategy strategy;
	private RuleEngine ruleEngine;

	@Before
	public void setup(){
		strategy = Mockito.mock(Strategy.class);
		ruleEngine = new RuleEngine();
	}

	@Test
	public void containsAllDefaultProperties() {
		Mockito.when(strategy.availableDefaultProperties()).thenReturn(Utils.set("name"));
		ruleEngine.addHandler(new PropertyCombinationHandler() {
			
			@Override
			public void handlePropertyCombination(PropertyContainer propertyContainer) {
				assertThat(propertyContainer.containsPropertyValue("name"), equalTo(true));
				
			}
		});
		ruleEngine.run(strategy);
	}

    @Test
	public void ruleEngineResetsAssignmentReasonAfterPropertySetEventIsFired() {
		ruleEngine.addHandler(new PropertyCombinationHandler() {
			
			@Override
			public void handlePropertyCombination(PropertyContainer propertyContainer) {
				StatefulRule triggeredRule = iterate("name").over("value2").when("triggeredBy").asRule();
				RuleEngine ruleEngine = (RuleEngine) propertyContainer;
				ruleEngine.setPropertyValue(triggeredRule, "value2", true);
				assertThat(ruleEngine.getAssignmentReason(), equalTo("->"));
				
			}
		});
		ruleEngine.run(strategy);
	}
    

	@Test
	public void callsGenerationFinished() {
		final PropertyHandler propertyHandler = Mockito.mock(PropertyHandler.class);
		ruleEngine.addHandler(propertyHandler);
		ruleEngine.run(strategy);
		
		Mockito.verify(propertyHandler).generationFinished();
	}

 }
