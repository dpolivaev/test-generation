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

