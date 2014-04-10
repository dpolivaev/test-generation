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
MULTIPLE FILES WERE GENERATED

File 1 : packagename/MyFile.java

package packagename;

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : packagename/_first_StrategyFactory.java

package packagename;

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x y").over(1, 2, 3).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}	
	@Test def withConditionForSingleValue() {
		'''
			strategy first
				let x be 'a', 'b'{
					let y be 'A' only if :x=='a',
							'B' ordered
				}
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import com.google.common.base.Objects;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private Boolean condition1(final PropertyContainer propertyContainer) {
    boolean _equals = Objects.equal(propertyContainer.get("x"), "a");
    return Boolean.valueOf(_equals);
  }

  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over("a", "b").with(
      RuleBuilder.Factory.iterate("y").over(new ValueProvider(){
        @Override public Object value(PropertyContainer propertyContainer) {
          if(!condition1(propertyContainer)) return SpecialValue.SKIP;
          Object __value = "A";
          return __value;
      }}, "B").ordered().asTriggeredRule()
    ).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}

	@Test def withValueProvider() {
		'''
			import org.dpolivaev.tsgen.strategies.StrategyHelper; 
			strategy first
				let testcase be StrategyHelper.idProvider
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.ruleengine.ValueProviderHelper;
import org.dpolivaev.tsgen.strategies.StrategyHelper;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private ValueProvider value1(final PropertyContainer propertyContainer) {
    return StrategyHelper.idProvider;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("testcase").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object __value = ValueProviderHelper.toValue(value1(propertyContainer), propertyContainer);
        return __value;
    }}).asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x y").over(1).asRule());
    __strategy.addRule(RuleBuilder.Factory.when("x y").iterate("z").over(2).asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over("line1\nline2\nline3\n").asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.when("x1", "x2").iterate("y").over(1, 2, 3).asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.ruleengine.ValueProviderHelper;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private Object value1(final PropertyContainer propertyContainer) {
    return propertyContainer.get("x");
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("y").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object __value = ValueProviderHelper.toValue(value1(propertyContainer), propertyContainer);
        return __value;
    }}).asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private Boolean condition1(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((1 < 2));
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        if (!condition1(propertyContainer)) return false;
        return true;
    }}).iterate("y").over(3).asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private Boolean condition1(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((1 < 2));
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        if (!condition1(propertyContainer)) return false;
        return true;
    }}).iterate("y").over(3).asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private Boolean condition1(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((1 < 2));
  }
  
  private Boolean condition2(final PropertyContainer propertyContainer) {
    return Boolean.valueOf((3 < 4));
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        if (!condition1(propertyContainer)) return false;
        if (!condition2(propertyContainer)) return false;
        return true;
    }}).iterate("y").over(5).asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(1, 2, 3).asDefaultRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.when("x1", "x2").iterate("y").over(1, 2, 3).asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).with(
      RuleBuilder.Factory.iterate("y").over(2).asTriggeredRule()
    ).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}	
	
	@Test def withAppliedStrategyForValue() {
		'''
			strategy other
				let y be 2
			strategy first
				let x be 1 {
					apply other
				}
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy other = other();

  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy other() {
    return new _other_StrategyFactory().other();
  }

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.StrategyConverter;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private RequirementBasedStrategy externalStrategy1() {
    return MyFile.other;
  }

  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).with(
      RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.0/@rule/@values/@actions.0/@ruleGroups.0/@strategy").over(SpecialValue.UNDEFINED).with(StrategyConverter.toStrategy(externalStrategy1())).asTriggeredRule()
    ).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy).addRequiredItemsFrom(StrategyConverter.toRequirementBasedStrategy(externalStrategy1()));
  }
}

File 3 : _other_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _other_StrategyFactory {
  RequirementBasedStrategy other() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("y").over(2).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}

	@Test def withAppliedStrategyAndRulesForValue() {
		'''
			strategy other
				let y be 2
			strategy first
				let x be 1 {
					let z1 be 3
					apply other
					let z2 be 4
				}
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy other = other();

  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy other() {
    return new _other_StrategyFactory().other();
  }

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.StrategyConverter;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private RequirementBasedStrategy externalStrategy1() {
    return MyFile.other;
  }

  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).with(
      RuleBuilder.Factory.iterate("z1").over(3).asTriggeredRule(),
      RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.0/@rule/@values/@actions.0/@ruleGroups.1/@strategy").over(SpecialValue.UNDEFINED).with(StrategyConverter.toStrategy(externalStrategy1())).asTriggeredRule(),
      RuleBuilder.Factory.iterate("z2").over(4).asTriggeredRule()
    ).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy).addRequiredItemsFrom(StrategyConverter.toRequirementBasedStrategy(externalStrategy1()));
  }
}

File 3 : _other_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _other_StrategyFactory {
  RequirementBasedStrategy other() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("y").over(2).asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).with(
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).with(
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = First();

  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _First_StrategyFactory {
  RequirementBasedStrategy First() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("a").over(1).with(
      RuleBuilder.Factory.iterate("b").over(2).asTriggeredRule()
    ).over(3).asRule());
    __strategy.addRule(RuleBuilder.Factory.iterate("d").over(1).over(3).asDefaultRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(2, 3).ordered().asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(2, 3).shuffled().asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object __value = new StringBuilder().append("a").append("b").toString();
        return __value;
    }}).asRule());
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.TrackingRuleEngine;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = First();
  
  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    CoverageTracker __coverageTracker = new CoverageTracker();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    __writerFactory.addCoverageTracker(__coverageTracker);
    RuleEngine __ruleEngine = new TrackingRuleEngine(__coverageTracker);
    __writerFactory.configureEngine(__ruleEngine);
    new RequirementBasedStrategy().with(First).run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _First_StrategyFactory {
  RequirementBasedStrategy First() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.TrackingRuleEngine;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public final static RequirementBasedStrategy second = second();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
  
  public static RequirementBasedStrategy second() {
    return new _second_StrategyFactory().second();
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    CoverageTracker __coverageTracker = new CoverageTracker();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    __writerFactory.addCoverageTracker(__coverageTracker);
    RuleEngine __ruleEngine = new TrackingRuleEngine(__coverageTracker);
    __writerFactory.configureEngine(__ruleEngine);
    new RequirementBasedStrategy().with(first).with(second).run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

File 3 : _second_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _second_StrategyFactory {
  RequirementBasedStrategy second() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}	
	
	@Test def appliesStrategies() {
		'''
			strategy first
			strategy second apply first
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy first = first();
  
  public final static RequirementBasedStrategy second = second();
  
  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }

  public static RequirementBasedStrategy second() {
    return new _second_StrategyFactory().second();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

File 3 : _second_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.StrategyConverter;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _second_StrategyFactory {
  private RequirementBasedStrategy externalStrategy1() {
    return MyFile.first;
  }
  
  RequirementBasedStrategy second() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.0/@strategy").over(SpecialValue.UNDEFINED).with(StrategyConverter.toStrategy(externalStrategy1())).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy).addRequiredItemsFrom(StrategyConverter.toRequirementBasedStrategy(externalStrategy1()));
  }
}

		''')
	}	
	
	
	@Test def extendExternalStrategies() {
		'''
			import org.dpolivaev.tsgen.ruleengine.Strategy
			strategy first apply new Strategy()
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  private static Object externalStrategy1();

  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.StrategyConverter;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private Strategy externalStrategy1() {
    Strategy _strategy = new Strategy();
    return _strategy;
  }
  
  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate(" /MyFile.tsgen#/0/@strategies.0/@ruleGroups.0/@strategy").over(SpecialValue.UNDEFINED).with(StrategyConverter.toStrategy(externalStrategy1())).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy).addRequiredItemsFrom(StrategyConverter.toRequirementBasedStrategy(externalStrategy1()));
  }
}

		''')
	}	
	@Test def runWithXslt() {
		'''
			strategy First
			run strategy First output "testoutput/xml", apply "my.xslt" output "testoutput/java"
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.TrackingRuleEngine;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = First();
  
  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    __outputConfiguration.setXmlDirectory("testoutput").setXmlExtension("xml").setXsltSource("my.xslt").setFileDirectory("testoutput").setFileExtension("java");
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    CoverageTracker __coverageTracker = new CoverageTracker();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    __writerFactory.addCoverageTracker(__coverageTracker);
    RuleEngine __ruleEngine = new TrackingRuleEngine(__coverageTracker);
    __writerFactory.configureEngine(__ruleEngine);
    new RequirementBasedStrategy().with(First).run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _First_StrategyFactory {
  RequirementBasedStrategy First() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}	

	@Test def runWithXml() {
		'''
			strategy First
			run strategy First output "testoutput/xml"
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.TrackingRuleEngine;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = First();
  
  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    __outputConfiguration.setFileDirectory("testoutput").setFileExtension("xml");
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    CoverageTracker __coverageTracker = new CoverageTracker();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    __writerFactory.addCoverageTracker(__coverageTracker);
    RuleEngine __ruleEngine = new TrackingRuleEngine(__coverageTracker);
    __writerFactory.configureEngine(__ruleEngine);
    new RequirementBasedStrategy().with(First).run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _First_StrategyFactory {
  RequirementBasedStrategy First() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}	

	@Test def runWithReport() {
		'''
			strategy First
			run strategy First report "testoutput/xml", apply "my.xslt" output "testoutput/report"
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.TrackingRuleEngine;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = First();
  
  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    __reportConfiguration.setXmlDirectory("testoutput").setXmlExtension("xml").setXsltSource("my.xslt").setFileDirectory("testoutput").setFileExtension("report");
    CoverageTracker __coverageTracker = new CoverageTracker();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    __writerFactory.addCoverageTracker(__coverageTracker);
    RuleEngine __ruleEngine = new TrackingRuleEngine(__coverageTracker);
    __writerFactory.configureEngine(__ruleEngine);
    new RequirementBasedStrategy().with(First).run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _First_StrategyFactory {
  RequirementBasedStrategy First() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.TrackingRuleEngine;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = First();
  
  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    CoverageTracker __coverageTracker = new CoverageTracker();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    __writerFactory.addCoverageTracker(__coverageTracker);
    First.registerRequiredItems(__writerFactory);
    RuleEngine __ruleEngine = new TrackingRuleEngine(__coverageTracker);
    __writerFactory.configureEngine(__ruleEngine);
    new RequirementBasedStrategy().with(First).run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _First_StrategyFactory {
  RequirementBasedStrategy First() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{
      new CoverageEntry("req1", "123"),};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("[req1]").over(123).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
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
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy First = First();

  public static RequirementBasedStrategy First() {
    return new _First_StrategyFactory().First();
  }
}

File 2 : _First_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.ruleengine.ValueProviderHelper;

@SuppressWarnings("all")
class _First_StrategyFactory {
  private Object value1(final PropertyContainer propertyContainer) {
    return propertyContainer.get("a");
  }
  
  RequirementBasedStrategy First() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{
      new CoverageEntry("req1", CoverageEntry.ANY),};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("a").over(123).asDefaultRule());
    __strategy.addRule(RuleBuilder.Factory.iterate("[req1]").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object __value = ValueProviderHelper.toValue(value1(propertyContainer), propertyContainer);
        return __value;
    }}).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}	
	
	@Test def withOneParameter() {
		'''
			strategy first(int p)
				let default x be p
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first(final int p) {
    return new _first_StrategyFactory(p).first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.ruleengine.ValueProviderHelper;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private final int p;

  public _first_StrategyFactory(final int p) {
    this.p = p
  }

  private int value1(final PropertyContainer propertyContainer) {
    return this.p;
  }

  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object __value = ValueProviderHelper.toValue(value1(propertyContainer), propertyContainer);
        return __value;
    }}).asDefaultRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}

	@Test def withParameterizedRuleName() {
		'''
			strategy first(int p)
				let default ("x#" p) be p
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public static RequirementBasedStrategy first(final int p) {
    return new _first_StrategyFactory(p).first();
  }
}

File 2 : _first_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.ruleengine.ValueProviderHelper;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private final int p;

  public _first_StrategyFactory(final int p) {
    this.p = p
  }

  private int name1() {
    return this.p;
  }

  private int value2(final PropertyContainer propertyContainer) {
    return this.p;
  }

  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate(new StringBuilder().append("x#").append(name1()).toString()).over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        Object __value = ValueProviderHelper.toValue(value2(propertyContainer), propertyContainer);
        return __value;
    }}).asDefaultRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}

	@Test def withConditionallyAppliedStrategy() {
		'''
			strategy other
				let y be 2
			strategy first
				let x be 1
				if (:x == 1) apply other
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;

@SuppressWarnings("all")
public class MyFile {
  public final static RequirementBasedStrategy other = other();

  public final static RequirementBasedStrategy first = first();

  public static RequirementBasedStrategy other() {
    return new _other_StrategyFactory().other();
  }

  public static RequirementBasedStrategy first() {
    return new _first_StrategyFactory().first();
  }
}

File 2 : _first_StrategyFactory.java

import com.google.common.base.Objects;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.StrategyConverter;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _first_StrategyFactory {
  private Boolean condition1(final PropertyContainer propertyContainer) {
    boolean _equals = Objects.equal(propertyContainer.get("x"), Integer.valueOf(1));
    return Boolean.valueOf(_equals);
  }

  private RequirementBasedStrategy externalStrategy2() {
    return MyFile.other;
  }

  RequirementBasedStrategy first() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(1).asRule());
    __strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        if (!condition1(propertyContainer)) return false;
        return true;
    }}).iterate(" /MyFile.tsgen#/0/@strategies.1/@ruleGroups.1/@strategy").over(SpecialValue.UNDEFINED).with(StrategyConverter.toStrategy(externalStrategy2())).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy).addRequiredItemsFrom(StrategyConverter.toRequirementBasedStrategy(externalStrategy2()));
  }
}

File 3 : _other_StrategyFactory.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.Strategy;

@SuppressWarnings("all")
class _other_StrategyFactory {
  RequirementBasedStrategy other() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("y").over(2).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
}

		''')
	}

}