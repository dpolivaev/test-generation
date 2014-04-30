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
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory;

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
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory;

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

