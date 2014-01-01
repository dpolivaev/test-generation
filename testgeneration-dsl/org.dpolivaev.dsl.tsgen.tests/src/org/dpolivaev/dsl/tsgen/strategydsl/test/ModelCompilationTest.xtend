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
class ModelCompilationTest {

	@Inject extension CompilationTestHelper
	
	@Test def minimalModel() {
		'''
			model myModel {}
		'''.assertCompilesTo(
		'''
			MULTIPLE FILES WERE GENERATED
			
			File 1 : MyFile.java
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final static MyModel myModel = new MyModel();
			}
			
			File 2 : MyModel.java
			
			import java.util.Arrays;
			import java.util.List;
			import org.dpolivaev.tsgen.coverage.code.CodeCoverageTracker;
			import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
			import org.dpolivaev.tsgen.scriptwriter.PropertyAccessingModel;
			
			@SuppressWarnings("all")
			public class MyModel implements PropertyAccessingModel {
			  public final static List<String> labels = Arrays.asList(new String[]{});
			  
			  private PropertyContainer propertyContainer;
			  
			  private CodeCoverageTracker codeCoverageTracker = new CodeCoverageTracker();
			  
			  @Override
			  public String getName() {
			    return "myModel";
			  }
			  
			  @Override
			  public CodeCoverageTracker getCodeCoverageTracker() {
			    return codeCoverageTracker;
			  }
			  
			  @Override
			  public List<String> getRequiredItems() {
			    return labels;
			  }
			  
			  @Override
			  public void setPropertyContainer(final PropertyContainer propertyContainer) {
			    this.propertyContainer=propertyContainer;
			  }
			}
			
		''')
	}	
	@Test def modelWithLabels() {
		'''
			model myModel {
				def calculate(int i){
					["req1" "reason1"] 
					val x = ["req2"] 2
					x
				}
			}
		'''.assertCompilesTo(
		'''
			MULTIPLE FILES WERE GENERATED
			
			File 1 : MyFile.java
			
			@SuppressWarnings("all")
			public class MyFile {
			  public final static MyModel myModel = new MyModel();
			}
			
			File 2 : MyModel.java
			
			import java.util.Arrays;
			import java.util.List;
			import org.dpolivaev.tsgen.coverage.code.CodeCoverageTracker;
			import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
			import org.dpolivaev.tsgen.scriptwriter.PropertyAccessingModel;
			
			@SuppressWarnings("all")
			public class MyModel implements PropertyAccessingModel {
			  public final static List<String> labels = Arrays.asList(new String[]{"req2","req1",});
			  
			  private PropertyContainer propertyContainer;
			  
			  private CodeCoverageTracker codeCoverageTracker = new CodeCoverageTracker();
			  
			  @Override
			  public String getName() {
			    return "myModel";
			  }
			  
			  @Override
			  public CodeCoverageTracker getCodeCoverageTracker() {
			    return codeCoverageTracker;
			  }
			  
			  @Override
			  public List<String> getRequiredItems() {
			    return labels;
			  }
			  
			  @Override
			  public void setPropertyContainer(final PropertyContainer propertyContainer) {
			    this.propertyContainer=propertyContainer;
			  }
			  
			  public int calculate(final int i) {
			    int _xblockexpression = (int) 0;
			    {
			      codeCoverageTracker.reach("req1", String.valueOf("reason1"));
			      codeCoverageTracker.reach("req2", "");
			      int _labeledexpression = (int) 0;
			      _labeledexpression = 2;
			      final int x = _labeledexpression;
			      _xblockexpression = (x);
			    }
			    return _xblockexpression;
			  }
			}
			
		''')
	}	
	@Test def registeredModel() {
		'''
			model myModel {
				def calculate(int i){0}
			}
			strategy s
				let x be myModel.calculate(0)
				
			run strategy s with model goal myModel
		'''.assertCompilesTo(
		'''
			MULTIPLE FILES WERE GENERATED
			
			File 1 : MyFile.java
			
			import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
			import org.dpolivaev.tsgen.ruleengine.RuleBuilder;
			import org.dpolivaev.tsgen.ruleengine.Strategy;
			import org.dpolivaev.tsgen.ruleengine.ValueProvider;
			import org.dpolivaev.tsgen.scriptwriter.StrategyRunner;
			
			@SuppressWarnings("all")
			public class MyFile {
			  private static Object valueProvider1(final PropertyContainer propertyContainer) {
			    Object _calculate = MyFile.myModel.calculate(0);
			    return _calculate;
			  }
			  
			  public final static MyModel myModel = new MyModel();
			  
			  public final static Strategy s = defineStrategyS();
			  
			  private static Strategy defineStrategyS() {
			    Strategy strategy = new Strategy();
			    strategy.addRule(RuleBuilder.Factory.iterate("x").over(new ValueProvider(){
			      @Override public Object value(PropertyContainer propertyContainer) { return valueProvider1(propertyContainer); }
			    }).asRule());
			    return strategy;
			  }
			  
			  public static void run1() {
			    StrategyRunner strategyRunner = new StrategyRunner();
			    strategyRunner.addModel(myModel).addPropertyAccessor(myModel);
			    strategyRunner.run(s);
			  }
			  
			  public static void main(final String[] args) {
			    MyFile.run1();
			  }
			}
			
			File 2 : MyModel.java
			
			import java.util.Arrays;
			import java.util.List;
			import org.dpolivaev.tsgen.coverage.code.CodeCoverageTracker;
			import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
			import org.dpolivaev.tsgen.scriptwriter.PropertyAccessingModel;
			
			@SuppressWarnings("all")
			public class MyModel implements PropertyAccessingModel {
			  public final static List<String> labels = Arrays.asList(new String[]{});
			  
			  private PropertyContainer propertyContainer;
			  
			  private CodeCoverageTracker codeCoverageTracker = new CodeCoverageTracker();
			  
			  @Override
			  public String getName() {
			    return "myModel";
			  }
			  
			  @Override
			  public CodeCoverageTracker getCodeCoverageTracker() {
			    return codeCoverageTracker;
			  }
			  
			  @Override
			  public List<String> getRequiredItems() {
			    return labels;
			  }
			  
			  @Override
			  public void setPropertyContainer(final PropertyContainer propertyContainer) {
			    this.propertyContainer=propertyContainer;
			  }
			  
			  public int calculate(final int i) {
			    return 0;
			  }
			}
			
		''')
	}	
}