/*******************************************************************************
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.dpolivaev.dsl.tsgen.strategydsl.test

import com.google.inject.Inject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper
import org.junit.Test
import org.junit.runner.RunWith
import org.dpolivaev.dsl.tsgen.StrategyDslInjectorProvider

@RunWith(XtextRunner)
@InjectWith(StrategyDslInjectorProvider)
class CompilationTest {

	@Inject extension CompilationTestHelper
	
	@Test def singlePackageDeclaration() {
		'''
package packagename
		'''.assertCompilesTo('''
package packagename;

@SuppressWarnings("all")
public class MyFile {
}
		''')
	}	
	
	@Test def singleStrategyDeclaration() {
		'''
			package packagename
			strategy first
		'''.assertCompilesTo('''
			package packagename;
			
			import ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    return strategy;
			  }
			}
		''')
	}	
	
	@Test def withOneTopRule() {
		'''
			strategy first
				let x be 1, 2, 3
		'''.assertCompilesTo('''
		import ruleengine.StatefulRuleBuilder.Factory;
		import ruleengine.Strategy;
		
		@SuppressWarnings("all")
		public class MyFile {
		  Strategy first = defineStrategyFirst();
		  
		  private Strategy defineStrategyFirst() {
		    Strategy strategy = new Strategy();
		    strategy.addRule(Factory.iterate("x").over(1, 2, 3));
		    return strategy;
		  }
		}
		''')
	}	
	
	@Test def withTriggeredRules() {
		'''
			strategy first
				for each x1, x2 let y be 1, 2, 3
		'''.assertCompilesTo('''
		import ruleengine.StatefulRuleBuilder.Factory;
		import ruleengine.Strategy;
		
		@SuppressWarnings("all")
		public class MyFile {
		  Strategy first = defineStrategyFirst();
		  
		  private Strategy defineStrategyFirst() {
		    Strategy strategy = new Strategy();
		    strategy.addRule(Factory.when("x1", "x2").iterate("y").over(1, 2, 3));
		    return strategy;
		  }
		}
		''')
	}	
	
	@Test def withCalculation() {
		'''
			strategy first
				let y be :x
		'''.assertCompilesTo('''
			import ruleengine.PropertyContainer;
			import ruleengine.StatefulRuleBuilder.Factory;
			import ruleengine.Strategy;
			import ruleengine.ValueProvider;
			
			@SuppressWarnings("all")
			public class MyFile {
			  private static Object valueProvider1(final PropertyContainer propertyContainer) {
			    return propertyContainer.get("x");
			  }
			  
			  Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory.iterate("y").over(new ValueProvider(){
			      @Override public Object value(PropertyContainer propertyContainer) { return valueProvider1(propertyContainer); }
			    }));
			    return strategy;
			  }
			}
		''')
	}	
	
	@Test def withCondition() {
		'''
			strategy first
				if 1 < 2 then let y be 3
		'''.assertCompilesTo('''
			import ruleengine.Condition;
			import ruleengine.PropertyContainer;
			import ruleengine.StatefulRuleBuilder.Factory;
			import ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  private static Boolean condition1(final PropertyContainer propertyContainer) {
			    boolean _lessThan = (1 < 2);
			    return Boolean.valueOf(_lessThan);
			  }
			  
			  Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory._if(new Condition(){
			      @Override public boolean isSatisfied(PropertyContainer propertyContainer) { return condition1(propertyContainer); }
			    }).iterate("y").over(3));
			    return strategy;
			  }
			}
		''')
	}	
	@Test def withConditionalGroup() {
		'''
			strategy first
				if 1 < 2 then {
					let y be 3
				}
		'''.assertCompilesTo('''
			import ruleengine.Condition;
			import ruleengine.PropertyContainer;
			import ruleengine.StatefulRuleBuilder.Factory;
			import ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  private static Boolean condition1(final PropertyContainer propertyContainer) {
			    boolean _lessThan = (1 < 2);
			    return Boolean.valueOf(_lessThan);
			  }
			  
			  Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory._if(new Condition(){
			      @Override public boolean isSatisfied(PropertyContainer propertyContainer) { return condition1(propertyContainer); }
			    }).iterate("y").over(3));
			    return strategy;
			  }
			}
		''')
	}	
	@Test def withConditionalGroupAndCondition() {
		'''
			strategy first
				if 1 < 2 then {
					if 3 < 4 then let y be 5
				}
			'''.assertCompilesTo('''
			import ruleengine.Condition;
			import ruleengine.PropertyContainer;
			import ruleengine.StatefulRuleBuilder.Factory;
			import ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  private static Boolean condition1(final PropertyContainer propertyContainer) {
			    boolean _lessThan = (1 < 2);
			    return Boolean.valueOf(_lessThan);
			  }
			  
			  private static Boolean condition2(final PropertyContainer propertyContainer) {
			    boolean _lessThan = (3 < 4);
			    return Boolean.valueOf(_lessThan);
			  }
			  
			  Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory._if(new Condition(){
			      @Override public boolean isSatisfied(PropertyContainer propertyContainer) { return condition1(propertyContainer) && condition2(propertyContainer); }
			    }).iterate("y").over(5));
			    return strategy;
			  }
			}
		''')
	}	
	@Test def withOneDefaultRule() {
		'''
			strategy first
				let default x be 1, 2, 3
		'''.assertCompilesTo('''
		import ruleengine.StatefulRuleBuilder.Factory;
		import ruleengine.Strategy;
		
		@SuppressWarnings("all")
		public class MyFile {
		  Strategy first = defineStrategyFirst();
		  
		  private Strategy defineStrategyFirst() {
		    Strategy strategy = new Strategy();
		    strategy.addDefaultRule(Factory.iterate("x").over(1, 2, 3));
		    return strategy;
		  }
		}
		''')
	}	
	
	@Test def withTriggeringGroupAndTriggerRules() {
		'''
			strategy first
				for each x1 {
					for each x2 let y be 1, 2, 3
				}
		'''.assertCompilesTo('''
		import ruleengine.StatefulRuleBuilder.Factory;
		import ruleengine.Strategy;
		
		@SuppressWarnings("all")
		public class MyFile {
		  Strategy first = defineStrategyFirst();
		  
		  private Strategy defineStrategyFirst() {
		    Strategy strategy = new Strategy();
		    strategy.addRule(Factory.when("x1", "x2").iterate("y").over(1, 2, 3));
		    return strategy;
		  }
		}
		''')
	}	
	
}