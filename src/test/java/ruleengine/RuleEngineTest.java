package ruleengine;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static ruleengine.TestUtils.set;

import org.junit.Test;
import org.mockito.Mockito;


public class RuleEngineTest {

	@Test
	public void containsAllDefaultProperties() {
		Strategy strategy = Mockito.mock(Strategy.class);
		Mockito.when(strategy.availableDefaultProperties()).thenReturn(set("name"));
		final RuleEngine ruleEngine = new RuleEngine(new ScriptProducer() {
			
			@Override
			public void makeScriptFor(PropertyContainer propertyContainer) {
				assertThat(propertyContainer.containsPropertyValue("name"), equalTo(true));
				
			}
		});
		ruleEngine.run(strategy);
	}

}
