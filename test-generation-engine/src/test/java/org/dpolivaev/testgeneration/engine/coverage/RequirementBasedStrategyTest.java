package org.dpolivaev.testgeneration.engine.coverage;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.dpolivaev.testgeneration.engine.coverage.RequirementBasedStrategy;
import org.dpolivaev.testgeneration.engine.coverage.internal.RequirementChecker;
import org.dpolivaev.testgeneration.engine.ruleengine.RuleEngine;
import org.dpolivaev.testgeneration.engine.ruleengine.Strategy;
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory;
import org.junit.Before;
import org.junit.Test;

public class RequirementBasedStrategyTest {

	private RequirementChecker requirementChecker;
	private Strategy strategy;
	private RequirementChecker anotherRequirementChecker;
	private Strategy anotherStrategy;

	@Before
	public void setup(){
		strategy = mock(Strategy.class);
		anotherStrategy = mock(Strategy.class);
		
		requirementChecker = mock(RequirementChecker.class);
		anotherRequirementChecker = mock(RequirementChecker.class);
	}
	
	@Test
	public void combinesRequirementItems() {
		RequirementBasedStrategy requirementBasedStrategy = new RequirementBasedStrategy(requirementChecker, strategy);
		RequirementBasedStrategy anotherRequirementBasedStrategy = new RequirementBasedStrategy(anotherRequirementChecker, anotherStrategy);
		RequirementChecker combinedRequirementChecker = mock(RequirementChecker.class);
		when(requirementChecker.with(anotherRequirementChecker)).thenReturn(combinedRequirementChecker);
		RequirementBasedStrategy combinedRequirementBasedStrategy = requirementBasedStrategy.with(anotherRequirementBasedStrategy);
		WriterFactory writerFactory = mock(WriterFactory.class);
		combinedRequirementBasedStrategy.registerRequiredItems(writerFactory);
		verify(combinedRequirementChecker).registerRequiredItems(writerFactory);
	}

	@Test
	public void combinesStrategies() {
		Strategy combinedStrategy = mock(Strategy.class);
		when(strategy.with(anotherStrategy)).thenReturn(combinedStrategy);
		RequirementBasedStrategy requirementBasedStrategy = new RequirementBasedStrategy(requirementChecker, strategy);
		RequirementBasedStrategy combinedRequirementBasedStrategy = requirementBasedStrategy.with(anotherStrategy);
		RuleEngine ruleEngine = mock(RuleEngine.class);
		
		combinedRequirementBasedStrategy.run(ruleEngine);
		
		verify(ruleEngine).run(combinedStrategy);
		
	}
}
