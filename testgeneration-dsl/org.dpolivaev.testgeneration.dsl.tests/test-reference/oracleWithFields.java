MULTIPLE FILES WERE GENERATED

File 1 : /myProject/src-gen/MyFile.java

@SuppressWarnings("all")
public class MyFile {
  public final static MyOracle myOracle = new MyOracle();
}

File 2 : /myProject/src-gen/MyOracle.java

import java.util.Arrays;
import java.util.List;
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.ruleengine.PropertyHandler;
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory;

@SuppressWarnings("all")
public class MyOracle implements PropertyHandler {
  public MyOracle() {
    this.x = _init_x();
    this.y = _init_y();
    
  }
  
  private int _init_x() {
    return 0;
  }
  
  public final int x;
  
  private int _init_y() {
    return 2;
  }
  
  public int y;
  
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

