package org.dpolivaev.testgeneration.engine.ruleengine;
import static org.dpolivaev.testgeneration.engine.ruleengine.RuleBuilder.Factory.iterate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.dpolivaev.testgeneration.engine.ruleengine.PropertyCombinationHandler;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;
import org.dpolivaev.testgeneration.engine.ruleengine.Rule;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleEngine;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.utils.internal.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;


public class RuleEngineTest {
	
	private Strategy strategy;
	private RuleEngine ruleEngine;

	@Before
	public void setup(){
		strategy = mock(Strategy.class);
		when(strategy.triggeredRules()).thenReturn(Collections.<Rule>emptyList());
		ruleEngine = new RuleEngine();
	}

	@Test
	public void containsAllDefaultProperties() {
		Mockito.when(strategy.availableDefaultProperties()).thenReturn(Utils.set("name"));
		ruleEngine.addHandler(new PropertyCombinationHandler() {
			
			@Override
			public void handlePropertyCombination(PropertyContainer propertyContainer) {
				assertThat(propertyContainer.isPropertyAvailable("name"), equalTo(true));
				
			}
		});
		ruleEngine.run(strategy);
	}

    @Test
	public void ruleEngineResetsAssignmentReasonAfterPropertySetEventIsFired() {
		ruleEngine.addHandler(new PropertyCombinationHandler() {
			
			@Override
			public void handlePropertyCombination(PropertyContainer propertyContainer) {
				Rule triggeredRule = iterate("name").over("value2").when("triggeredBy").create();
				RuleEngine ruleEngine = (RuleEngine) propertyContainer;
				ruleEngine.setPropertyValue(triggeredRule, "value2", true);
				assertThat(ruleEngine.getAssignmentReason(), equalTo("->"));
				
			}
		});
		ruleEngine.run(strategy);
	}
    

	@Test
	public void firesEvents() {
		final PropertyHandler propertyHandler = Mockito.mock(PropertyHandler.class);
		ruleEngine.addHandler(propertyHandler);
		ruleEngine.run(strategy);
		
		InOrder inOrder = Mockito.inOrder(propertyHandler);
		inOrder.verify(propertyHandler).handlePropertyCombination(ruleEngine);
		inOrder.verify(propertyHandler).generationFinished();
	}

 }
