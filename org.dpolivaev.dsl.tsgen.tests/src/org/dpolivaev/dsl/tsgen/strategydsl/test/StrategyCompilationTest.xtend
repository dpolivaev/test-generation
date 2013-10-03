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
class StrategyCompilationTest {

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
			
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
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
				let "x y" be 1, 2, 3
		'''.assertCompilesTo('''
		import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
		import org.dpolivaev.tsgen.ruleengine.Strategy;
		
		@SuppressWarnings("all")
		public class MyFile {
		  public final Strategy first = defineStrategyFirst();
		  
		  private Strategy defineStrategyFirst() {
		    Strategy strategy = new Strategy();
		    strategy.addRule(Factory.iterate("x y").over(1, 2, 3).asRule());
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
		import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
		import org.dpolivaev.tsgen.ruleengine.Strategy;
		
		@SuppressWarnings("all")
		public class MyFile {
		  public final Strategy first = defineStrategyFirst();
		  
		  private Strategy defineStrategyFirst() {
		    Strategy strategy = new Strategy();
		    strategy.addRule(Factory.when("x1", "x2").iterate("y").over(1, 2, 3).asRule());
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
			import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			import org.dpolivaev.tsgen.ruleengine.ValueProvider;
			
			@SuppressWarnings("all")
			public class MyFile {
			  private Object valueProvider1(final PropertyContainer propertyContainer) {
			    return propertyContainer.get("x");
			  }
			  
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory.iterate("y").over(new ValueProvider(){
			      @Override public Object value(PropertyContainer propertyContainer) { return valueProvider1(propertyContainer); }
			    }).asRule());
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
			import org.dpolivaev.tsgen.ruleengine.Condition;
			import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  private Boolean condition1(final PropertyContainer propertyContainer) {
			    boolean _lessThan = (1 < 2);
			    return Boolean.valueOf(_lessThan);
			  }
			  
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory._if(new Condition(){
			      @Override public boolean isSatisfied(PropertyContainer propertyContainer) { return condition1(propertyContainer); }
			    }).iterate("y").over(3).asRule());
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
			import org.dpolivaev.tsgen.ruleengine.Condition;
			import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  private Boolean condition1(final PropertyContainer propertyContainer) {
			    boolean _lessThan = (1 < 2);
			    return Boolean.valueOf(_lessThan);
			  }
			  
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory._if(new Condition(){
			      @Override public boolean isSatisfied(PropertyContainer propertyContainer) { return condition1(propertyContainer); }
			    }).iterate("y").over(3).asRule());
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
			import org.dpolivaev.tsgen.ruleengine.Condition;
			import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  private Boolean condition1(final PropertyContainer propertyContainer) {
			    boolean _lessThan = (1 < 2);
			    return Boolean.valueOf(_lessThan);
			  }
			  
			  private Boolean condition2(final PropertyContainer propertyContainer) {
			    boolean _lessThan = (3 < 4);
			    return Boolean.valueOf(_lessThan);
			  }
			  
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory._if(new Condition(){
			      @Override public boolean isSatisfied(PropertyContainer propertyContainer) { return condition1(propertyContainer) && condition2(propertyContainer); }
			    }).iterate("y").over(5).asRule());
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
		import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
		import org.dpolivaev.tsgen.ruleengine.Strategy;
		
		@SuppressWarnings("all")
		public class MyFile {
		  public final Strategy first = defineStrategyFirst();
		  
		  private Strategy defineStrategyFirst() {
		    Strategy strategy = new Strategy();
		    strategy.addRule(Factory.iterate("x").over(1, 2, 3).asDefaultRule());
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
		import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
		import org.dpolivaev.tsgen.ruleengine.Strategy;
		
		@SuppressWarnings("all")
		public class MyFile {
		  public final Strategy first = defineStrategyFirst();
		  
		  private Strategy defineStrategyFirst() {
		    Strategy strategy = new Strategy();
		    strategy.addRule(Factory.when("x1", "x2").iterate("y").over(1, 2, 3).asRule());
		    return strategy;
		  }
		}
		''')
	}	
	
	@Test def withOneRuleForValue() {
		'''
			strategy first
				let x be 1 {
					let y be 2
				}
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory.iterate("x").over(1).with(
			      Factory.iterate("y").over(2).asTriggeredRule()
			    ).asRule());
			    return strategy;
			  }
			}
		''')
	}	
	
	@Test def withTwoRuleForValue() {
		'''
			strategy first
				let x be 1 {
					let y be 2
					let z be 3
				}
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory.iterate("x").over(1).with(
			      Factory.iterate("y").over(2).asTriggeredRule(),
			      Factory.iterate("z").over(3).asTriggeredRule()
			    ).asRule());
			    return strategy;
			  }
			}
		''')
	}
		
	@Test def withOneRuleGroupForValue() {
		'''
			strategy first
				let x be 1 {
					for each x {
						let y be 2
					}
				}
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory.iterate("x").over(1).with(
			      Factory.when("x").iterate("y").over(2).asTriggeredRule()
			    ).asRule());
			    return strategy;
			  }
			}
		''')
	}	
	
	@Test def ordered() {
		'''
			strategy first
				let x be 2, 3 ordered 		
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory.iterate("x").over(2, 3).ordered().asRule());
			    return strategy;
			  }
			}
		''')
	}	
	
	@Test def shuffled() {
		'''
			strategy first
				let x be 2, 3 shuffled 		
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory.iterate("x").over(2, 3).shuffled().asRule());
			    return strategy;
			  }
			}
		''')
	}
	
	@Test def oneStrategyRun() {
		'''
			strategy First
			run strategy First
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			import org.dpolivaev.tsgen.scriptwriter.StrategyRunner;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    return strategy;
			  }
			  
			  public void run1() {
			    StrategyRunner strategyRunner = new StrategyRunner();
			    strategyRunner.run(first);
			  }
			  
			  public static void main(final String[] args) {
			    MyFile instance = new MyFile();
			    instance.run1();
			  }
			}
		''')
	}	
	
	@Test def twoStrategiesRun() {
		'''
			strategy first
			strategy second
			run strategy first with strategy second
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			import org.dpolivaev.tsgen.scriptwriter.StrategyRunner;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  public final Strategy second = defineStrategySecond();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    return strategy;
			  }
			  
			  private Strategy defineStrategySecond() {
			    Strategy strategy = new Strategy();
			    return strategy;
			  }
			  
			  public void run1() {
			    StrategyRunner strategyRunner = new StrategyRunner();
			    strategyRunner.run(first.with(second));
			  }
			  
			  public static void main(final String[] args) {
			    MyFile instance = new MyFile();
			    instance.run1();
			  }
			}
		''')
	}	
	
	@Test def extendStrategies() {
		'''
			strategy first
			strategy second extends first with first
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  public final Strategy second = defineStrategySecond();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    return strategy;
			  }
			  
			  private Strategy defineStrategySecond() {
			    Strategy strategy = new Strategy().with(first).with(first);
			    return strategy;
			  }
			}
		''')
	}	
	
	
	@Test def extendExternalStrategies() {
		'''
			import org.dpolivaev.tsgen.ruleengine.Strategy
			strategy first extends new Strategy()
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			
			@SuppressWarnings("all")
			public class MyFile {
			  private Strategy externalStrategy1() {
			    Strategy _strategy = new Strategy();
			    return _strategy;
			  }
			  
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy().with(externalStrategy1());
			    return strategy;
			  }
			}
		''')
	}	
	@Test def runWithXslt() {
		'''
			strategy First
			run strategy First output "testoutput/xml", apply "my.xslt" output "testoutput/java"
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			import org.dpolivaev.tsgen.scriptwriter.StrategyRunner;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    return strategy;
			  }
			  
			  public void run1() {
			    StrategyRunner strategyRunner = new StrategyRunner();
			    strategyRunner.getOutputConfiguration().setXmlDirectory("testoutput").setXmlExtension("xml").setXsltSource("my.xslt").setFileDirectory("testoutput").setFileExtension("java");
			    strategyRunner.run(first);
			  }
			  
			  public static void main(final String[] args) {
			    MyFile instance = new MyFile();
			    instance.run1();
			  }
			}
		''')
	}	

	@Test def runWithXml() {
		'''
			strategy First
			run strategy First output "testoutput/xml"
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			import org.dpolivaev.tsgen.scriptwriter.StrategyRunner;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    return strategy;
			  }
			  
			  public void run1() {
			    StrategyRunner strategyRunner = new StrategyRunner();
			    strategyRunner.getOutputConfiguration().setFileDirectory("testoutput").setFileExtension("xml");
			    strategyRunner.run(first);
			  }
			  
			  public static void main(final String[] args) {
			    MyFile instance = new MyFile();
			    instance.run1();
			  }
			}
		''')
	}	

	@Test def runWithReport() {
		'''
			strategy First
			run strategy First report "testoutput/xml", apply "my.xslt" output "testoutput/report"
		'''.assertCompilesTo('''
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			import org.dpolivaev.tsgen.scriptwriter.StrategyRunner;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    return strategy;
			  }
			  
			  public void run1() {
			    StrategyRunner strategyRunner = new StrategyRunner();
			    strategyRunner.getReportConfiguration().setXmlDirectory("testoutput").setXmlExtension("xml").setXsltSource("my.xslt").setFileDirectory("testoutput").setFileExtension("report");
			    strategyRunner.run(first);
			  }
			  
			  public static void main(final String[] args) {
			    MyFile instance = new MyFile();
			    instance.run1();
			  }
			}
		''')
	}	

	@Test def coverage() {
		'''
			strategy First
				cover req1 by 123, 456
		'''.assertCompilesTo('''
			import java.util.Arrays;
			import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			import org.dpolivaev.tsgen.ruleengine.ValueProvider;
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final Strategy first = defineStrategyFirst();
			  
			  private Strategy defineStrategyFirst() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(Factory.iterate("requirement.req1").over(new ValueProvider(){
			      @Override public Object value(PropertyContainer propertyContainer) { return Arrays.asList(123, 456); }
			    }).asRule());
			    return strategy;
			  }
			}
		''')
	}	
	
	@Test def coverageWithReference() {
		'''
			strategy First
				let default a be 123
				cover req1 by :a
		'''.assertCompilesTo('''
				import java.util.Arrays;
				import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
				import org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory;
				import org.dpolivaev.tsgen.ruleengine.Strategy;
				import org.dpolivaev.tsgen.ruleengine.ValueProvider;
				
				@SuppressWarnings("all")
				public class MyFile {
				  private Object valueProvider1(final PropertyContainer propertyContainer) {
				    return propertyContainer.get("a");
				  }
				  
				  public final Strategy first = defineStrategyFirst();
				  
				  private Strategy defineStrategyFirst() {
				    Strategy strategy = new Strategy();
				    strategy.addRule(Factory.iterate("a").over(123).asDefaultRule());
				    strategy.addRule(Factory.iterate("requirement.req1").over(new ValueProvider(){
				      @Override public Object value(PropertyContainer propertyContainer) { return Arrays.asList(valueProvider1(propertyContainer)); }
				    }).asRule());
				    return strategy;
				  }
				}
		''')
	}	
	
}