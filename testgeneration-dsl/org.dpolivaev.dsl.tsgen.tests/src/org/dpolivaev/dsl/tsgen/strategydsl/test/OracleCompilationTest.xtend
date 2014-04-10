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
class OracleCompilationTest {

	@Inject extension CompilationTestHelper
	
	@Test def minimalOracle() {
		'''
			oracle myOracle {}
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

@SuppressWarnings("all")
public class MyFile {
  public final static MyOracle myOracle = new MyOracle();
}

File 2 : MyOracle.java

import java.util.Arrays;
import java.util.List;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyOracle implements PropertyHandler {
  public final static List<CoverageEntry> labels = Arrays.asList(new CoverageEntry[]{});
  
  private PropertyContainer propertyContainer;
  
  private CoverageTracker coverageTracker = null;
  
  public void setCoverageTracker(final CoverageTracker coverageTracker) {
    this.coverageTracker = coverageTracker;
  }
  
  public void registerRequiredItems(final WriterFactory writerFactory) {
    writerFactory.registerRequiredItems(labels);
  }
  
  @Override
  public void generationStarted(final PropertyContainer propertyContainer) {
    this.propertyContainer=propertyContainer;
  }
  
  @Override
  public void handlePropertyCombination(final PropertyContainer propertyContainer) {
    
  }
  
  @Override
  public void generationFinished() {
    this.propertyContainer=null;
  }
}

		''')
	}	
	@Test def oracleWithLabels() {
		'''
			oracle myOracle {
				def calculate(int i){
					["req1" "reason1"] 
					val x = ["req2"] 2
					x
				}
			}
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

@SuppressWarnings("all")
public class MyFile {
  public final static MyOracle myOracle = new MyOracle();
}

File 2 : MyOracle.java

import java.util.Arrays;
import java.util.List;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyOracle implements PropertyHandler {
  public final static List<CoverageEntry> labels = Arrays.asList(new CoverageEntry[]{
      new CoverageEntry("req1", "reason1"),
      new CoverageEntry("req2", CoverageEntry.ANY),});
  
  private PropertyContainer propertyContainer;
  
  private CoverageTracker coverageTracker = null;
  
  public void setCoverageTracker(final CoverageTracker coverageTracker) {
    this.coverageTracker = coverageTracker;
  }
  
  public void registerRequiredItems(final WriterFactory writerFactory) {
    writerFactory.registerRequiredItems(labels);
  }
  
  @Override
  public void generationStarted(final PropertyContainer propertyContainer) {
    this.propertyContainer=propertyContainer;
  }
  
  @Override
  public void handlePropertyCombination(final PropertyContainer propertyContainer) {
    
  }
  
  @Override
  public void generationFinished() {
    this.propertyContainer=null;
  }
  
  public int calculate(final int i) {
    int _xblockexpression = (int) 0;
    {
      if(coverageTracker != null) coverageTracker.reach("req1", String.valueOf("reason1"));
      if(coverageTracker != null) coverageTracker.reach("req2", "");
      int _labeledexpression = (int) 0;
      _labeledexpression = 2;
      final int x = _labeledexpression;
      _xblockexpression = x;
    }
    return _xblockexpression;
  }
}

		''')
	}	
	@Test def registeredOracle() {
		'''
			oracle myOracle {
				def calculate(int i){0}
			}
			strategy s
				let x be traced myOracle.calculate(0)
				
			run strategy s with oracle goal myOracle
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.CoverageTrackerEnabler;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.TrackingRuleEngine;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.ruleengine.ValueProviderHelper;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  private static Object value1(final PropertyContainer propertyContainer) {
    Object _calculate = MyFile.myOracle.calculate(0);
    return _calculate;
  }
  
  public final static MyOracle myOracle = new MyOracle();
  
  public final static RequirementBasedStrategy s = defineStrategyS();
  
  private static RequirementBasedStrategy defineStrategyS() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
      @Override public Object value(PropertyContainer propertyContainer) {
        ((CoverageTrackerEnabler)propertyContainer).startTrace(); try{
        Object __value = ValueProviderHelper.toValue(value1(propertyContainer), propertyContainer);
        return __value;
        } finally{ ((CoverageTrackerEnabler)propertyContainer).stopTrace();}
    }}).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    CoverageTracker __coverageTracker = new CoverageTracker();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    __writerFactory.addCoverageTracker(__coverageTracker);
    RuleEngine __ruleEngine = new TrackingRuleEngine(__coverageTracker);
    __ruleEngine.addHandler(myOracle);
    myOracle.setCoverageTracker(__coverageTracker);
    myOracle.registerRequiredItems(__writerFactory);
    __writerFactory.configureEngine(__ruleEngine);
    new RequirementBasedStrategy().with(s).run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}

File 2 : MyOracle.java

import java.util.Arrays;
import java.util.List;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyOracle implements PropertyHandler {
  public final static List<CoverageEntry> labels = Arrays.asList(new CoverageEntry[]{});
  
  private PropertyContainer propertyContainer;
  
  private CoverageTracker coverageTracker = null;
  
  public void setCoverageTracker(final CoverageTracker coverageTracker) {
    this.coverageTracker = coverageTracker;
  }
  
  public void registerRequiredItems(final WriterFactory writerFactory) {
    writerFactory.registerRequiredItems(labels);
  }
  
  @Override
  public void generationStarted(final PropertyContainer propertyContainer) {
    this.propertyContainer=propertyContainer;
  }
  
  @Override
  public void handlePropertyCombination(final PropertyContainer propertyContainer) {
    
  }
  
  @Override
  public void generationFinished() {
    this.propertyContainer=null;
  }
  
  public int calculate(final int i) {
    return 0;
  }
}

		''')
	}	

	@Test def registeredOracleInCondition() {
		'''
			oracle myOracle {
				def calculate(){true}
			}
			strategy s
				if traced myOracle.calculate() let x be 1
				
			run strategy s with oracle goal myOracle
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.coverage.CoverageTrackerEnabler;
import org.dpolivaev.tsgen.coverage.RequirementBasedStrategy;
import org.dpolivaev.tsgen.coverage.TrackingRuleEngine;
import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.scriptwriter.OutputConfiguration;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyFile {
  private static Boolean condition1(final PropertyContainer propertyContainer) {
    Boolean _calculate = MyFile.myOracle.calculate();
    return _calculate;
  }
  
  public final static MyOracle myOracle = new MyOracle();
  
  public final static RequirementBasedStrategy s = defineStrategyS();
  
  private static RequirementBasedStrategy defineStrategyS() {
    CoverageEntry[] __requiredItems = new CoverageEntry[]{};
    Strategy __strategy = new Strategy();
    __strategy.addRule(RuleBuilder.Factory._if(new Condition(){
      @Override public boolean isSatisfied(PropertyContainer propertyContainer) {
        ((CoverageTrackerEnabler)propertyContainer).startTrace(); try{
        if (!condition1(propertyContainer)) return false;
        } finally{ ((CoverageTrackerEnabler)propertyContainer).stopTrace();}
        return true;
    }}).iterate("x").over(1).asRule());
    return new RequirementBasedStrategy(__requiredItems).with(__strategy);
  }
  
  public static void run1() {
    
    OutputConfiguration __outputConfiguration = new OutputConfiguration();
    OutputConfiguration __reportConfiguration = new OutputConfiguration();
    CoverageTracker __coverageTracker = new CoverageTracker();
    WriterFactory __writerFactory = new WriterFactory(__outputConfiguration, __reportConfiguration);
    __writerFactory.addCoverageTracker(__coverageTracker);
    RuleEngine __ruleEngine = new TrackingRuleEngine(__coverageTracker);
    __ruleEngine.addHandler(myOracle);
    myOracle.setCoverageTracker(__coverageTracker);
    myOracle.registerRequiredItems(__writerFactory);
    __writerFactory.configureEngine(__ruleEngine);
    new RequirementBasedStrategy().with(s).run(__ruleEngine);
  }
  
  public static void main(final String[] args) {
    MyFile.run1();
  }
}

File 2 : MyOracle.java

import java.util.Arrays;
import java.util.List;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyOracle implements PropertyHandler {
  public final static List<CoverageEntry> labels = Arrays.asList(new CoverageEntry[]{});
  
  private PropertyContainer propertyContainer;
  
  private CoverageTracker coverageTracker = null;
  
  public void setCoverageTracker(final CoverageTracker coverageTracker) {
    this.coverageTracker = coverageTracker;
  }
  
  public void registerRequiredItems(final WriterFactory writerFactory) {
    writerFactory.registerRequiredItems(labels);
  }
  
  @Override
  public void generationStarted(final PropertyContainer propertyContainer) {
    this.propertyContainer=propertyContainer;
  }
  
  @Override
  public void handlePropertyCombination(final PropertyContainer propertyContainer) {
    
  }
  
  @Override
  public void generationFinished() {
    this.propertyContainer=null;
  }
  
  public boolean calculate() {
    return true;
  }
}

''')
	}

	@Test def referencingOracles() {
		'''
			oracle myOracle1 {
				def calculate(){1}
			}

			oracle myOracle2 {
				def calculate(){myOracle1.calculate()}
			}
		'''.assertCompilesTo('''
MULTIPLE FILES WERE GENERATED

File 1 : MyFile.java

@SuppressWarnings("all")
public class MyFile {
  public final static MyOracle1 myOracle1 = new MyOracle1();

  public final static MyOracle2 myOracle2 = new MyOracle2();
}

File 2 : MyOracle1.java

import java.util.Arrays;
import java.util.List;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyOracle1 implements PropertyHandler {
  public final static List<CoverageEntry> labels = Arrays.asList(new CoverageEntry[]{});

  private PropertyContainer propertyContainer;

  private CoverageTracker coverageTracker = null;

  public void setCoverageTracker(final CoverageTracker coverageTracker) {
    this.coverageTracker = coverageTracker;
  }

  public void registerRequiredItems(final WriterFactory writerFactory) {
    writerFactory.registerRequiredItems(labels);
  }

  @Override
  public void generationStarted(final PropertyContainer propertyContainer) {
    this.propertyContainer=propertyContainer;
  }

  @Override
  public void handlePropertyCombination(final PropertyContainer propertyContainer) {

  }

  @Override
  public void generationFinished() {
    this.propertyContainer=null;
  }

  public int calculate() {
    return 1;
  }
}

File 3 : MyOracle2.java

import java.util.Arrays;
import java.util.List;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageTracker;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.PropertyHandler;
import org.dpolivaev.tsgen.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyOracle2 implements PropertyHandler {
  public final static List<CoverageEntry> labels = Arrays.asList(new CoverageEntry[]{});

  private PropertyContainer propertyContainer;

  private CoverageTracker coverageTracker = null;

  public void setCoverageTracker(final CoverageTracker coverageTracker) {
    this.coverageTracker = coverageTracker;
  }

  public void registerRequiredItems(final WriterFactory writerFactory) {
    writerFactory.registerRequiredItems(labels);
  }

  @Override
  public void generationStarted(final PropertyContainer propertyContainer) {
    this.propertyContainer=propertyContainer;
  }

  @Override
  public void handlePropertyCombination(final PropertyContainer propertyContainer) {

  }

  @Override
  public void generationFinished() {
    this.propertyContainer=null;
  }

  public int calculate() {
    return MyFile.myOracle1.calculate();
  }
}

''')
	}

}