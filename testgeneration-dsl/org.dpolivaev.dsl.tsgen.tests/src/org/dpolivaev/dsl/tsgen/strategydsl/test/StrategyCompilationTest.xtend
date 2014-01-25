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

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	
	@Test def withOneTopRule() {
		'''
			strategy first
				let "x y" be 1, 2, 3
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("x y").over(1, 2, 3).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	
	@Test def withExplicitTriggeredRule() {
		'''
			strategy first
				let "x y" be 1
				for each "x y" let z be 2
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("x y").over(1).asRule());
    strategy.addRule(RuleBuilder.Factory.when("x y").iterate("z").over(2).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	
	@Test def withMultilineString() {
		'''
			strategy first
				let x be "line1
			line2
			line3
			"
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("x").over("line1\nline2\nline3\n").asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	
	@Test def withTriggeredRules() {
		'''
			strategy first
				for each x1, x2 let y be 1, 2, 3
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.when("x1", "x2").iterate("y").over(1, 2, 3).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	
	@Test def withCalculation() {
		'''
			strategy first
				let y be :x
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  private static Object valueProvider1(final PropertyContainer propertyContainer) {
    return propertyContainer.get("x");
  }
  
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("y").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) { return valueProvider1(propertyContainer); }
    }).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	
	@Test def withCondition() {
		'''
			strategy first
				if 1 < 2 let y be 3
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  private static Boolean condition1(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((1 < 2));
  }
  
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) { return condition1(propertyContainer); }
    }).iterate("y").over(3).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	@Test def withConditionalGroup() {
		'''
			strategy first
				if 1 < 2 {
					let y be 3
				}
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  private static Boolean condition1(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((1 < 2));
  }
  
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) { return condition1(propertyContainer); }
    }).iterate("y").over(3).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	@Test def withConditionalGroupAndCondition() {
		'''
			strategy first
				if 1 < 2 {
					if 3 < 4 let y be 5
				}
			'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  private static Boolean condition1(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((1 < 2));
  }
  
  private static Boolean condition2(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((3 < 4));
  }
  
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) { return condition1(propertyContainer) && condition2(propertyContainer); }
    }).iterate("y").over(5).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	@Test def withOneDefaultRule() {
		'''
			strategy first
				let default x be 1, 2, 3
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("x").over(1, 2, 3).asDefaultRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
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
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.when("x1", "x2").iterate("y").over(1, 2, 3).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
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
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).with(
      RuleBuilder.Factory.iterate("y").over(2).asTriggeredRule()
    ).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
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
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).with(
      RuleBuilder.Factory.iterate("y").over(2).asTriggeredRule(),
      RuleBuilder.Factory.iterate("z").over(3).asTriggeredRule()
    ).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
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
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).with(
      RuleBuilder.Factory.when("x").iterate("y").over(2).asTriggeredRule()
    ).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}
	
	@Test def withSharedValues() {
		'''
			strategy First
			let a be listA with 1 {let b be 2}, 3 {}
			let default d be from listA
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("a").over(1).with(
      RuleBuilder.Factory.iterate("b").over(2).asTriggeredRule()
    ).over(3).asRule());
    strategy.addRule(RuleBuilder.Factory.iterate("d").over(1).over(3).asDefaultRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}
	
	@Test def ordered() {
		'''
			strategy first
				let x be 2, 3 ordered 		
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("x").over(2, 3).ordered().asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	
	@Test def shuffled() {
		'''
			strategy first
				let x be 2, 3 shuffled 		
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("x").over(2, 3).shuffled().asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}
	
	@Test def concatenatedValues(){
		'''
			strategy first
				let x be ("a" "b")
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) { return new StringBuilder().append("a").append("b").toString(); }
    }).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}
	
	@Test def oneStrategyRun() {
		'''
			strategy First
			run strategy First
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    RuleEngine __ruleEngine = new RuleEngine();
    __writerFactory.configureEngine(__ruleEngine);
    First.run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
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
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  public final static RequirementBasedStrategy second = defineStrategySecond();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
  
  private static RequirementBasedStrategy defineStrategySecond() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    RuleEngine __ruleEngine = new RuleEngine();
    __writerFactory.configureEngine(__ruleEngine);
    first.with(second).run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}
		''')
	}	
	
	@Test def extendStrategies() {
		'''
			strategy first
			strategy second extends first with first
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  public final static RequirementBasedStrategy second = defineStrategySecond();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
  
  private static RequirementBasedStrategy defineStrategySecond() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(first).with(first).with(__strategy);
  }
}
		''')
	}	
	
	
	@Test def extendExternalStrategies() {
		'''
			import org.dpolivaev.tsgen.ruleengine.Strategy
			strategy first extends new Strategy()
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  private static Strategy externalStrategy1() {
    Strategy _strategy = new Strategy();
    return _strategy;
  }
  
  public final static RequirementBasedStrategy first = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(externalStrategy1()).with(__strategy);
  }
}
		''')
	}	
	@Test def runWithXslt() {
		'''
			strategy First
			run strategy First output "testoutput/xml", apply "my.xslt" output "testoutput/java"
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
     __outputConfiguration.setXmlDirectory("testoutput").setXmlExtension("xml").setXsltSource("my.xslt").setFileDirectory("testoutput").setFileExtension("java");
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    RuleEngine __ruleEngine = new RuleEngine();
    __writerFactory.configureEngine(__ruleEngine);
    First.run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}
		''')
	}	

	@Test def runWithXml() {
		'''
			strategy First
			run strategy First output "testoutput/xml"
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
     __outputConfiguration.setFileDirectory("testoutput").setFileExtension("xml");
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    RuleEngine __ruleEngine = new RuleEngine();
    __writerFactory.configureEngine(__ruleEngine);
    First.run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}
		''')
	}	

	@Test def runWithReport() {
		'''
			strategy First
			run strategy First report "testoutput/xml", apply "my.xslt" output "testoutput/report"
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
     __reportConfiguration.setXmlDirectory("testoutput").setXmlExtension("xml").setXsltSource("my.xslt").setFileDirectory("testoutput").setFileExtension("report");
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    RuleEngine __ruleEngine = new RuleEngine();
    __writerFactory.configureEngine(__ruleEngine);
    First.run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}
		''')
	}	

	@Test def coverage() {
		'''
			strategy First
				let [req1] be 123
			
			run strategy goal First 	
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{
      new CoverageEntry("req1", "123"),};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("[req1]").over(123).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    First.addRequiredItems(__writerFactory);
    RuleEngine __ruleEngine = new RuleEngine();
    __writerFactory.configureEngine(__ruleEngine);
    First.run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}
		''')
	}	
	
	@Test def coverageWithReference() {
		'''
			strategy First
				let default a be 123
				let [req1] be :a
		'''.assertCompilesTo('''
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.scriptwriter.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  private static Object valueProvider1(final PropertyContainer propertyContainer) {
    return propertyContainer.get("a");
  }
  
  public final static RequirementBasedStrategy First = defineStrategyFirst();
  
  private static RequirementBasedStrategy defineStrategyFirst() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{
      new CoverageEntry("req1", CoverageEntry.ANY),};
    Strategy __strategy = new Strategy();
    strategy.addRule(RuleBuilder.Factory.iterate("a").over(123).asDefaultRule());
    strategy.addRule(RuleBuilder.Factory.iterate("[req1]").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) { return valueProvider1(propertyContainer); }
    }).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}
		''')
	}	
	
}